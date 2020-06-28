package com.abhishek.spring.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abhishek.spring.entities.User;
import com.abhishek.spring.services.EmailService;
import com.abhishek.spring.services.OtpService;
import com.abhishek.spring.utility.EmailTemplate;

@Controller
public class OtpController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public OtpService otpService;

	@Autowired
	public EmailService emailService;

	@GetMapping("/generateOtp")
	public String generateOtp(User user) {

		int otp = otpService.generateOTP(user.getEmail());

		logger.info("OTP : " + otp);

		// Generate The Template to send OTP
		EmailTemplate template = new EmailTemplate("SendOtp.html");

		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("user", user.getUsername());
		replacements.put("otp", String.valueOf(otp));

		String message = template.getTemplate(replacements);

		emailService.sendOtpMessage(user.getEmail(), "OTP Confirmation", message);

		return "otppage";
	}

	@RequestMapping(value = "/validateOtp", method = RequestMethod.GET)
	public @ResponseBody String validateOtp(@PathVariable String email, @PathVariable int otpnum) {

		final String SUCCESS = "Entered Otp is valid";

		final String FAIL = "Entered Otp is NOT valid. Please Retry!";

		logger.info(" Otp  : " + otpnum);

		// Validate the Otp
		if (otpnum >= 0) {
			int serverOtp = otpService.getOtp(email);
			logger.info(" Otp  server: " + serverOtp);
			if (serverOtp > 0) {
				if (otpnum == serverOtp) {
					otpService.clearOTP(email);
					return SUCCESS;
				} else {
					return FAIL;
				}
			} else {
				return FAIL;
			}
		} else {
			return FAIL;
		}
	}

}
