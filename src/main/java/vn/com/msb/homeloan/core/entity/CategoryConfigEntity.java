package vn.com.msb.homeloan.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "category_config", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CategoryConfigEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "type")
    String type;

    @Column(name = "key")
    String key;

    @Column(name = "sub_key_1")
    String sub_key_1;

    @Column(name = "sub_key_2")
    String sub_key_2;

    @Column(name = "value")
    String value;

    @Column(name = "description")
    String description;

    @Column(name = "status")
    int status;

}
