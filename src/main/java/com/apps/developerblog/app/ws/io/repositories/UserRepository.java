package com.apps.developerblog.app.ws.io.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apps.developerblog.app.ws.io.entity.UserEntity;
import com.apps.developerblog.app.ws.shared.dto.UserDto;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);
	UserEntity findUserByUserId(String userId);
}
