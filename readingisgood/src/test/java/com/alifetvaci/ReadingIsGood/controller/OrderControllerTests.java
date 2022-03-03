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
public class OrderControllerTests {

	//If you are run testing you should change bearer token :(
	private final String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbWFpbDE1QGhvdG1haWwuY29tIiwiaWF0IjoxNjQ2MzE4NzMzLCJleHAiOjE2NDY0MDUxMzN9.kaQ4CYyi6ZSfoPpBcyPdbP3J6tS98npCIVppO2AxDXL9yZgw0510htppQAgaWDXmF66DSjT2RKUR2OSwQ2k-wA";
	@Autowired
	private MockMvc mvc;
	
	private final String url = "http://localhost:8080/";

	@Test
	void createOrderBookCountNegativeTest() throws Exception {
		String content = "{\r\n"
				+ "   \"orderBook\":[\r\n"
				+ "      {\r\n"
				+ "         \"book\":\"621f4e4214710747d7a7a1b3\",\r\n"
				+ "         \"number\" : -2\r\n"
				+ "      },\r\n"
				+ "      {\r\n"
				+ "         \"book\":\"621f4e5114710747d7a7a1b4\",\r\n"
				+ "         \"number\" : 2\r\n"
				+ "      }\r\n"
				+ "   ]\r\n"
				+ "}";
		mvc.perform(MockMvcRequestBuilders.post(url + "api/order").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token).content(content))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void createOrderBookNullTest() throws Exception {
		String content = "{\r\n"
				+ "   \"orderBook\":[\r\n"
				+ "      {\r\n"
				+ "         \"book\":\"\",\r\n"
				+ "         \"number\" : 2\r\n"
				+ "      },\r\n"
				+ "      {\r\n"
				+ "         \"book\":\"621f4e5114710747d7a7a1b4\",\r\n"
				+ "         \"number\" : 2\r\n"
				+ "      }\r\n"
				+ "   ]\r\n"
				+ "}";
		mvc.perform(MockMvcRequestBuilders.post(url + "api/order").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token).content(content))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void createOrderTest() throws Exception {
		String content = "{\r\n"
				+ "   \"orderBook\":[\r\n"
				+ "      {\r\n"
				+ "         \"book\":\"621f4e4214710747d7a7a1b3\",\r\n"
				+ "         \"number\" : 2\r\n"
				+ "      },\r\n"
				+ "      {\r\n"
				+ "         \"book\":\"621f4e5114710747d7a7a1b4\",\r\n"
				+ "         \"number\" : 2\r\n"
				+ "      }\r\n"
				+ "   ]\r\n"
				+ "}";
		mvc.perform(MockMvcRequestBuilders.post(url + "api/order").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token).content(content))
				.andExpect(status().isOk());
	}
	
	@Test
	void getOrders() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(url + "api/order").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token))
				.andExpect(status().isOk());
	}

}
