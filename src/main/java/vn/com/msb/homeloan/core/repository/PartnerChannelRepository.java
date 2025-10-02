package vn.com.msb.homeloan.core.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.PartnerChannelEntity;

@Repository
public interface PartnerChannelRepository extends JpaRepository<PartnerChannelEntity, String> {

  Optional<PartnerChannelEntity> findByLoanId(String loanId);
}
