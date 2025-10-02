package vn.com.msb.homeloan.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.AmlEntity;
import vn.com.msb.homeloan.core.entity.CategoryConfigEntity;

import java.util.List;
import java.util.Set;

@Repository
public interface CategoryConfigRepository extends JpaRepository<CategoryConfigEntity, String> {
    List<CategoryConfigEntity> findByTypeAndStatus(String type,int status);
}
