package com.apps.developerblog.app.ws.ui.controller;


import com.apps.developerblog.app.ws.exceptions.UserServiceException;
import com.apps.developerblog.app.ws.security.shared.dto.AddressDTO;
import com.apps.developerblog.app.ws.security.shared.dto.UserDto;
import com.apps.developerblog.app.ws.service.AddressService;
import com.apps.developerblog.app.ws.service.UserService;
import com.apps.developerblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.apps.developerblog.app.ws.ui.model.response.*;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@RestController
@RequestMapping("users") //http://localhost/8080/users
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	AddressService addressesService;
	
	@Autowired
	AddressService addressService;
	
	@GetMapping(path="/{id}", produces= { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public UserRest getUser(@PathVariable String id) {
		
		UserDto userDto = userService.getUserByUserId(id);
		
		ModelMapper modelMapper = new ModelMapper();
		UserRest returnValue = modelMapper.map(userDto, UserRest.class);
		
		return returnValue;
	}
	
	@PostMapping(
			consumes= { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			produces= { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
		
		if(userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
//		UserDto userDto = new UserDto();
//		BeanUtils.copyProperties(userDetails, userDto);
		
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto =  modelMapper.map(userDetails, UserDto.class);
		
		UserDto createdUser = userService.createUser(userDto);
		UserRest returnValue = modelMapper.map(createdUser, UserRest.class);
		
		return returnValue;
		
	}
	
	@PutMapping(path="/{id}",
			consumes= { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			produces= { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {

		UserRest returnValue = new UserRest();
		
		if(userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto updatedUser = userService.updateUser(id, userDto);
		BeanUtils.copyProperties(updatedUser, returnValue);
		
		return returnValue;
	}
	
	@DeleteMapping(path="/{id}", produces= { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public OperationsStatusModel deleteUser(@PathVariable String id) {
		
		OperationsStatusModel returnValue = new OperationsStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		userService.deleteUser(id);
		
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		
		return returnValue;
	}
	
	@GetMapping(produces= { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<UserRest> getUsers(@RequestParam(value="page", defaultValue="0") int page, @RequestParam(value="limit", defaultValue="25") int limit){
		
		List<UserRest> returnValue = new ArrayList<>();
		
		if(page>0) page = page-1;
		
		List<UserDto> users = userService.getUsers(page, limit);
		
		for (UserDto userDto : users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
		}
		
		return returnValue;
		
	}


	
//	http://localhost:8080/mobile-app-ws/users/jlglljgljljlg/addresses
	@GetMapping(path="/{id}/addresses", produces= { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public CollectionModel<AddressesRest> getUserAddresses(@PathVariable String id) {

		List<AddressesRest> returnValue = new ArrayList<>();

		List<AddressDTO> addressesDTO = addressesService.getAddresses(id);

		if(addressesDTO != null && !addressesDTO.isEmpty()) {

			Type listType = new TypeToken<List<AddressesRest>>() {}.getType();
			returnValue = new ModelMapper().map(addressesDTO, listType);

			for(AddressesRest addressRest : returnValue) {

				Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
						  .getUserAddress(id, addressRest.getAddressId()))
						  .withSelfRel();
				addressRest.add(selfLink);
			}
		}

		Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(id).withRel("user");

		Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
				  .getUserAddresses(id))
				  .withSelfRel();

		return CollectionModel.of(returnValue, userLink,selfLink);
	}


	@GetMapping(path="/{userId}/addresses/{addressId}", produces= { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public EntityModel<AddressesRest> getUserAddress(@PathVariable String userId, @PathVariable String addressId) {

		AddressDTO addressesDTO = addressService.getAddress(addressId);

		AddressesRest returnValue = new ModelMapper().map(addressesDTO, AddressesRest.class);

		Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(userId).withRel("user");

		Link userAddressesLink = WebMvcLinkBuilder.linkTo((UserController.class), WebMvcLinkBuilder.methodOn(UserController.class)
												  .getUserAddresses(userId))
												  .withRel("addresses");


		Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
										 .getUserAddress(userId, addressId))
										 .withSelfRel();

		return EntityModel.of(returnValue, Arrays.asList(userLink,userAddressesLink,selfLink));
	}
	

}
