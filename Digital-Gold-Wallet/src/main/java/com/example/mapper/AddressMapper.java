package com.example.mapper;

import com.example.dto.AddressDTO;
import com.example.entity.Address;

public class AddressMapper {
	public static AddressDTO convertEntityToObject(Address add) {
		if (add == null)
			return null;
		AddressDTO addDTO = new AddressDTO();
		addDTO.setAddressId(add.getAddressId());
		addDTO.setStreet(add.getStreet());
		addDTO.setCity(add.getCity());
		addDTO.setState(add.getState());
		addDTO.setPostalCode(add.getPostalCode());
		addDTO.setCountry(add.getCountry());
		return addDTO;
	}

	public static Address convertObjectToEntity(AddressDTO addDTO) {
		if (addDTO == null)
			return null;
		Address add = new Address();
		add.setAddressId(addDTO.getAddressId());
		add.setStreet(addDTO.getStreet());
		add.setCity(addDTO.getCity());
		add.setState(addDTO.getState());
		add.setPostalCode(addDTO.getPostalCode());
		add.setCountry(addDTO.getCountry());
		return add;
	}

}
