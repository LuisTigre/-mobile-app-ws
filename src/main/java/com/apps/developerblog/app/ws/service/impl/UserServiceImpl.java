package com.apps.developerblog.app.ws.service.impl;


import com.apps.developerblog.app.ws.exceptions.UserServiceException;
import com.apps.developerblog.app.ws.io.entity.UserEntity;
import com.apps.developerblog.app.ws.io.repositories.UserRepository;
import com.apps.developerblog.app.ws.security.shared.Utils;
import com.apps.developerblog.app.ws.security.shared.dto.AddressDTO;
import com.apps.developerblog.app.ws.security.shared.dto.UserDto;
import com.apps.developerblog.app.ws.service.UserService;
import com.apps.developerblog.app.ws.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	Utils utils;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDto createUser(UserDto user) {

		if (userRepository.findByEmail(user.getEmail()) != null)
			throw new RuntimeException("Record already exists");

		for (int i= 0; i < user.getAddresses().size(); i++){
			AddressDTO address = user.getAddresses().get(i);
			address.setUserDetails(user);
			address.setAddressId(utils.generateAddresId(30));
			user.getAddresses().set(i, address);
		}

		ModelMapper modelMapper = new ModelMapper();

		UserEntity userEntity = modelMapper.map(user, UserEntity.class);

		String publicUserId = utils.generatedUserId(30);
		userEntity.setUserId(publicUserId);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setEmailVerificationToken(utils.generateEmailVerificationToken(publicUserId));

		UserEntity storedUserDetails = userRepository.save(userEntity);

		UserDto returnValue = modelMapper.map(storedUserDetails, UserDto.class);


		return returnValue;
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);

		if(userEntity == null ) throw new UsernameNotFoundException(email);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);

		if(userEntity == null) throw new UsernameNotFoundException(email);

		return new User(userEntity.getEmail(),
						userEntity.getEncryptedPassword(),
						userEntity.getEmailVerificationStatus(),
						true, true, true,
						new ArrayList<>()
		);

//		return new User(userEntity.getEmail(),
//						userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUserByUserId(String userId) {

		UserEntity userEntity = userRepository.findByUserId(userId);

		if(userEntity == null ) throw new UsernameNotFoundException(userId);

		UserDto returnValue = new UserDto();

		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override
	public UserDto updateUser(String userId, UserDto user) {

		UserEntity userEntity = userRepository.findByUserId(userId);

		if(userEntity == null ) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		UserDto returnValue = new UserDto();

		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());

		UserEntity updatedUserDetails = userRepository.save(userEntity);

		BeanUtils.copyProperties(updatedUserDetails, returnValue);

		return returnValue;
	}

	@Transactional
	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if(userEntity == null ) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		userRepository.delete(userEntity);
	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		List<UserDto> returnValue = new ArrayList<>();

		if(page>0) page = page -1;

		Pageable pageableRequest = PageRequest.of(page, limit);
		Page<UserEntity> userPage = userRepository.findAll(pageableRequest);

		List<UserEntity> users = userPage.getContent();
		
		for( UserEntity userEntity : users){
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		}

		return returnValue;
	}

	@Override
	public boolean verifyEmailToken(String token) {
		boolean returnValue = false;

		//Find user by token
		UserEntity userEntity = userRepository.findUserByEmailVerificationToken(token);

			if(userEntity != null){
				boolean hastokenExpired = Utils.hasTokenExpired(token);
				if(!hastokenExpired){
					userEntity.setEmailVerificationToken(null);
					userEntity.setEmailVerificationStatus(Boolean.TRUE);
					userRepository.save(userEntity);
					returnValue = true;
				}
			}

		return false;
	}


}
