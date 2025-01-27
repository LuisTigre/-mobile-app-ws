package com.apps.developerblog.app.ws.service.impl;


import com.apps.developerblog.app.ws.io.entity.AddressEntity;
import com.apps.developerblog.app.ws.io.entity.UserEntity;
import com.apps.developerblog.app.ws.io.repositories.AddressRepository;
import com.apps.developerblog.app.ws.io.repositories.UserRepository;
import com.apps.developerblog.app.ws.shared.dto.AddressDTO;
import com.apps.developerblog.app.ws.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressRepository addressRepository;

	@Override
	public List<AddressDTO> getAddresses(String userId) {

		List<AddressDTO> returnValue = new ArrayList<>();
		ModelMapper modelMapper = new ModelMapper();
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity == null) return returnValue;
		
		Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
		
		for(AddressEntity addressEntity : addresses) {
			
			returnValue.add( modelMapper.map(addressEntity, AddressDTO.class));
		}
		
		
		return returnValue;
	}

	@Override
	public AddressDTO getAddress(String addressId) {
		AddressDTO returnValue = new AddressDTO();
		
		ModelMapper modelMapper = new ModelMapper();
		
		AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
		
		if(addressEntity != null) {
		
			returnValue =  new ModelMapper().map(addressEntity, AddressDTO.class);
		
		}
		
		return returnValue;
	}

}
