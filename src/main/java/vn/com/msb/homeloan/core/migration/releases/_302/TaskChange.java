package vn.com.msb.homeloan.core.migration.releases._302;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import liquibase.change.custom.CustomTaskChange;
import liquibase.change.custom.CustomTaskRollback;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.RollbackImpossibleException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.modelmapper.ModelMapper;
import vn.com.msb.homeloan.core.model.LoanApplicationItem;
import vn.com.msb.homeloan.core.model.LoanApplicationReview;

@Slf4j
public class TaskChange implements CustomTaskChange, CustomTaskRollback {

  @Override
  public void execute(Database database) throws CustomChangeException {
    log.info("Migration in progress");
    JdbcConnection con;
    try {
      con = (JdbcConnection) database.getConnection();
      // select all data in loan_pre_approval
      String query1 = "select loan_id, meta_data from loan_pre_approval";
      Statement stmt1 = con.prepareStatement(query1);
      ResultSet rs1 = stmt1.executeQuery(query1);
      // start loop
      while (rs1.next()) {
        try {
          ModelMapper modelMapper = new ModelMapper();

          ObjectMapper objectMapper = new ObjectMapper()
              .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
              .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
              .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

          String loanId = rs1.getString("loan_id");
          String metaDataBefore = rs1.getString("meta_data");

          // parse to object LoanApplicationReview
          OldLoanApplicationReview oldLoanApplicationReview = objectMapper.readValue(metaDataBefore,
              OldLoanApplicationReview.class);

          // set loanApplicationItems
          LoanApplicationReview loanApplicationReview = modelMapper.map(oldLoanApplicationReview,
              LoanApplicationReview.class);

          List<LoanApplicationItem> loanApplicationItems = new ArrayList<>();

          LoanApplicationItem loanApplicationItem = new LoanApplicationItem();

          // query loan application item id
          String query2 = "select count(uuid), uuid from loan_application_item where loan_application_id = ?";
          PreparedStatement preparedStatement2 = con.prepareStatement(query2);
          preparedStatement2.setString(1, loanId);
          ResultSet rs2 = preparedStatement2.executeQuery();
          rs2.next();
          int rows = rs2.getInt(1);
          String loanApplicationItemId = null;
          if (rows == 0) {
            throw new RuntimeException(
                String.format("No record found %s:%s", "loan_application_item:loan_application_id",
                    loanId));
          } else if (rows == 1) {
            loanApplicationItemId = rs2.getString(2);
          } else if (rows > 1) {
            throw new RuntimeException(String.format("Have more than one record %s:%s",
                "loan_application_item:loan_application_id", loanId));
          }

          loanApplicationItem.setUuid(loanApplicationItemId);
          loanApplicationItem.setLoanPurpose(
              oldLoanApplicationReview.getLoanApplication().getLoanPurpose());
          loanApplicationItem.setLoanAssetValue(
              oldLoanApplicationReview.getLoanApplication().getLoanAssetValue());
          loanApplicationItem.setLoanAmount(
              oldLoanApplicationReview.getLoanApplication().getLoanAmount());
          loanApplicationItem.setLoanTime(
              oldLoanApplicationReview.getLoanApplication().getLoanTime());

          loanApplicationItems.add(loanApplicationItem);

          loanApplicationReview.setLoanApplicationItems(loanApplicationItems);

          // parse to json and save back to loan_pre_approval
          String metaDataAfter = objectMapper.writeValueAsString(loanApplicationReview);

          String updateQuery = "update loan_pre_approval set meta_data = ? where loan_id = ?";

          PreparedStatement preparedStatement3 = con.prepareStatement(updateQuery);
          preparedStatement3.setString(1, metaDataAfter);
          preparedStatement3.setString(2, loanId);

          int row = preparedStatement3.executeUpdate();
          log.info("executeUpdate:row {}", row);
        } catch (Exception ex) {
          // write log
          String insertLogQuery = "insert into log_migration(message, stack_trace) values (?, ?)";
          PreparedStatement preparedStatement4 = con.prepareStatement(insertLogQuery);
          preparedStatement4.setString(1, ex.getMessage());
          preparedStatement4.setString(2, ExceptionUtils.getStackTrace(ex));
          preparedStatement4.executeUpdate();
          ex.printStackTrace();
        }
        // end loop
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      log.error("Unable to migrate data");
      throw new RuntimeException();
    }
    log.info("Data Migration Completed Successfully");
  }

  @Override
  public String getConfirmationMessage() {
    return null;
  }

  @Override
  public void setUp() throws SetupException {

  }

  @Override
  public void setFileOpener(ResourceAccessor resourceAccessor) {

  }

  @Override
  public ValidationErrors validate(Database database) {
    return null;
  }

  @Override
  public void rollback(Database database)
      throws CustomChangeException, RollbackImpossibleException {

  }
}
