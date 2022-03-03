package com.alifetvaci.ReadingIsGood.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	private final String url = "http://localhost:8080/";

	@Test
	void registerCustomerTest() throws Exception {
		String content = "{\r\n"
				+ "	\"username\":\"username12\",\r\n"
				+ "	\"email\":\"email12@hotmail.com\",\r\n"
				+ "	\"password\":\"password12\"\r\n"
				+ "}";
		mvc.perform(MockMvcRequestBuilders.post(url + "/api/auth/register").contentType(MediaType.APPLICATION_JSON)
				.content(content))
				.andExpect(status().isOk());
	}
	
	@Test
	void registerCustomerTestInvalidEmail() throws Exception {
		String content = "{\r\n"
				+ "	\"username\":\"username12\",\r\n"
				+ "	\"email\":\"email12\",\r\n"
				+ "	\"password\":\"password12\"\r\n"
				+ "}";
		mvc.perform(MockMvcRequestBuilders.post(url + "/api/auth/register").contentType(MediaType.APPLICATION_JSON)
				.content(content))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void loginCustomer() throws Exception {
		String content = "{\r\n"
				+ "	\"email\":\"email9\",\r\n"
				+ "	\"password\":\"username1\"\r\n"
				+ "}";
		mvc.perform(MockMvcRequestBuilders.post(url + "/api/auth/login").contentType(MediaType.APPLICATION_JSON)
				.content(content))
				.andExpect(status().isOk());
	}
	
	@Test
	void loginCustomerNullPassword() throws Exception {
		String content = "{\r\n"
				+ "	\"email\":\"email9\",\r\n"
				+ "	\"password\":\"\"\r\n"
				+ "}";
		mvc.perform(MockMvcRequestBuilders.post(url + "/api/auth/login").contentType(MediaType.APPLICATION_JSON)
				.content(content))
				.andExpect(status().isBadRequest());
	}

}
