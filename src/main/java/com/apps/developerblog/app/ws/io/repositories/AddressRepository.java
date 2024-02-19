package com.apps.developerblog.app.ws.io.repositories;


import com.apps.developerblog.app.ws.io.entity.AddressEntity;
import com.apps.developerblog.app.ws.io.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
	
	List<AddressEntity> findAllByUserDetails(UserEntity userEntity);
	
	AddressEntity findByAddressId(String addressId);

}
