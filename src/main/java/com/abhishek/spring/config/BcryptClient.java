package com.abhishek.spring.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class BcryptClient {

	public static Logger logger = LoggerFactory.getLogger(BcryptClient.class);
	
	@SuppressWarnings("unused")
	private static void main(String[] args) throws IOException{
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		logger.info("Enter the word to BCrypt : ");
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		
		String word = buffer.readLine();
		
		String bcryptWord = encoder.encode(word);
		
		logger.info("Encrypt Word : " + bcryptWord);
	}
}
