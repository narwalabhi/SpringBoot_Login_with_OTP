package com.abhishek.spring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abhishek.spring.entities.LoginRequestBody;
import com.abhishek.spring.entities.OtpRequestModel;
import com.abhishek.spring.entities.User;
import com.abhishek.spring.services.OtpService;
import com.assignment.spring.repos.UserRepository;

@RepositoryRestController
@RequestMapping
public class HomeController {


	@Autowired
	public OtpService otpService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OtpController otpController;
	
	@PostMapping("/register")
	public ResponseEntity<PersistentEntityResource> register(@RequestBody User user, PersistentEntityResourceAssembler assembler) {
//		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
//		logger.info(encodedPassword);
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
		return ResponseEntity.ok(assembler.toModel(user));
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/getOtp")
	public ResponseEntity getOtp(@RequestBody OtpRequestModel requestBody) {
		User user = userRepository.findByEmail(requestBody.getEmail());
		if (user == null) {
			throw new ResourceNotFoundException();
		} else {
			otpController.generateOtp(user);
		}
		return ResponseEntity.ok("OTP Sent to " + user.getEmail());
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody LoginRequestBody requestBody) {
		User user = userRepository.findByEmail(requestBody.getEmail());
		String message;
		if (user == null) {
			throw new ResourceNotFoundException();
		} else {
			message = otpController.validateOtp(requestBody.getEmail(), requestBody.getOtp());
		}
		return ResponseEntity.ok(message);
	}

}
