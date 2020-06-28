package com.abhishek.spring.utility;

import java.util.Map;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class EmailTemplate {

	@SuppressWarnings("unused")
	private String templateId;
	 
	private String template;
 
 
	public EmailTemplate(String templateId) {
		this.templateId = templateId;
		try {
			this.template = loadTemplate(templateId);
		} catch (Exception e) {
			this.template = "Empty";
		}
	}
 
	private String loadTemplate(String templateId) throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		java.io.File file = new File(classLoader.getResource(templateId).getFile());
		String content = "Empty";
		try {
			content = new String(Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			throw new Exception("Could not read template with ID = " + templateId);
		}
		return content;
	}
 
	public String getTemplate(Map<String, String> replacements) {
		String cTemplate = this.template;
 
		//Replace the String 
		cTemplate = "otp : " + replacements.get("otp");
		return cTemplate;
	}
	
}
