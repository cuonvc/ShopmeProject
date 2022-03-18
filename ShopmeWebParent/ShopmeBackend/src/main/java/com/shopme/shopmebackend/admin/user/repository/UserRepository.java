package com.shopme.shopmebackend.admin.user.repository;

import com.shopme.shopmecommon.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
