package com.assignment.spring.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import com.abhishek.spring.entities.PartialUserProjection;
import com.abhishek.spring.entities.User;

@RepositoryRestResource(excerptProjection=PartialUserProjection.class)
public interface UserRepository extends CrudRepository<User, String>{
	
	User  findByEmail(String email);
	
}
