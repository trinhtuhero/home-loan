package vn.com.msb.homeloan.core.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.ProfileEntity;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, String> {

  Optional<ProfileEntity> findOneByPhone(String phoneNumber);

  Optional<ProfileEntity> findByUuid(String uuid);
}
