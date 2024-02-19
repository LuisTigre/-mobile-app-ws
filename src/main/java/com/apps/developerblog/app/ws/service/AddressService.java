package com.apps.developerblog.app.ws.service;






import com.apps.developerblog.app.ws.security.shared.dto.AddressDTO;

import java.util.List;

public interface AddressService {

	
	List<AddressDTO> getAddresses(String userId);
	
	AddressDTO getAddress(String addressId);
}
