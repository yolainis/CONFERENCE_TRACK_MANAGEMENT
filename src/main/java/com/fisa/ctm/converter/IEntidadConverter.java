package com.fisa.ctm.converter;

import java.util.List;

public interface IEntidadConverter<T, D> {

	public List<D> mapListEntityToListDto(List<T> listEntidad);

	public List<T> mapListDtoToListEntity(List<D> listEntidadDto);

	public D mapEntityToDto(T entidad);

	public T mapDtoToEntity(D entidadDto);
}
