package org.shalim.heybeach.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.shalim.heybeach.domain.requests.RegisterRequest;
import org.shalim.heybeach.services.UserService;
import org.shalim.heybeach.services.validators.RegisterRequestValidator;
import org.shalim.heybeach.util.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RegisterController.class, secure = false)
public class RegisterControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@SpyBean
	private RegisterRequestValidator registerRequestValidator;

	private String REGISTER_ENDPOINT = "/heybeach/welcome/register";
	private String VALID_REGISTER_REQUEST = "{\"email\": \"mickey.mouse@gmail.com\", \"password\": \"mickey\", \"repeatedPassword\": \"mickey\"}";
	private String REGISTER_REQUEST_MISSING_EMAIL = "{\"password\": \"mickey\", \"repeatedPassword\": \"mickey\"}";

	@Test
	public void shouldPerformAUserRegistrationOnValidRequest() throws Exception {
		Mockito.when(userService.save(Mockito.any(RegisterRequest.class))).thenReturn(ReturnCode.SUCCESS);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(REGISTER_ENDPOINT)
				.accept(MediaType.APPLICATION_JSON).content(VALID_REGISTER_REQUEST)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void registrationShouldFailOnMissingParams() throws Exception {
		Mockito.when(userService.save(Mockito.any(RegisterRequest.class))).thenReturn(ReturnCode.SUCCESS);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(REGISTER_ENDPOINT)
				.accept(MediaType.APPLICATION_JSON).content(REGISTER_REQUEST_MISSING_EMAIL)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		assertEquals("Failed with the following errors:\n3: Invalid Request: missing email\n",
				response.getContentAsString());
	}
}
