package vn.com.msb.homeloan.core.model;

import javax.persistence.Column;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Nationality {

  @Id
  @Column(name = "code", unique = true)
  String code;

  @Column(name = "name")
  String name;
}
