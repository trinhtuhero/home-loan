package vn.com.msb.homeloan.core.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "request_pricing")
public class RequestPricingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String status;

  private String contractCode;

  private String loanId;
}
