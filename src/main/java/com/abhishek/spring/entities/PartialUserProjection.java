package com.abhishek.spring.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="partial", types= {User.class})
public interface PartialUserProjection {
	String getUsername();
	String getEmail();
	String getMobile();
}
