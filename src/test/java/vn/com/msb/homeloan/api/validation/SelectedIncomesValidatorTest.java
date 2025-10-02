package vn.com.msb.homeloan.api.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SelectedIncomesValidatorTest {

  @BeforeEach
  void setUp() {
  }

  @Test
  void test_isValid() {
    SelectedIncomesValidator selectedIncomesValidator = new SelectedIncomesValidator();

    String[] selectedIncomes1 = null;
    String[] selectedIncomes2 = {};

    String[] selectedIncomes3 = {"SALARY_INCOME", "PERSONAL_BUSINESS_INCOME"};
    String[] selectedIncomes4 = {"ASSUMING_TOTAL_ASSETS_INCOME", "ASSUMING_OTHERS_INCOME"};

    String[] selectedIncomes5 = {"SALARY_INCOME", "PERSONAL_BUSINESS_INCOME",
        "ASSUMING_TOTAL_ASSETS_INCOME"};
    String[] selectedIncomes6 = {"ASSUMING_TOTAL_ASSETS_INCOME", "ASSUMING_OTHERS_INCOME",
        "SALARY_INCOME"};

    String[] selectedIncomes7 = {"SALARY_INCOME", "ASSUMING_TOTAL_ASSETS_INCOME"};

    String[] selectedIncomes8 = {"ASSUMING_OTHERS_INCOME", "OTHERS_INCOME"};

    String[] selectedIncomes9 = {"OTHERS_INCOME", "ASSUMING_OTHERS_INCOME"};

    String[] selectedIncomes10 = {"SALARY_INCOME", "ASSUMING_TOTAL_ASSETS_INCOME", "OTHERS_INCOME",
        "ASSUMING_OTHERS_INCOME"};

    assertTrue(selectedIncomesValidator.isValid(null, null));

    assertTrue(selectedIncomesValidator.isValid(selectedIncomes2, null));

    assertTrue(selectedIncomesValidator.isValid(selectedIncomes3, null));

    assertTrue(selectedIncomesValidator.isValid(selectedIncomes4, null));

    assertFalse(selectedIncomesValidator.isValid(selectedIncomes5, null));
    assertFalse(selectedIncomesValidator.isValid(selectedIncomes6, null));
    assertFalse(selectedIncomesValidator.isValid(selectedIncomes7, null));
    assertFalse(selectedIncomesValidator.isValid(selectedIncomes8, null));
    assertFalse(selectedIncomesValidator.isValid(selectedIncomes9, null));
    assertFalse(selectedIncomesValidator.isValid(selectedIncomes10, null));
  }
}
