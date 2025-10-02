package vn.com.msb.homeloan.core.model.mapper;

public interface BaseMapper<D, E> {


  D toDto(E e);

  E toEntity(D d);
}
