package org.shalim.heybeach.controllers;

import org.junit.runner.RunWith;
import org.shalim.heybeach.services.UserService;
import org.shalim.heybeach.services.validators.PhotoRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RegisterController.class, secure = false)
public class PhotosControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@SpyBean
	private PhotoRequestValidator photoRequestValidator;

	private String PHOTO_ENDPOINT_BASE = "/heybeach/home";
	private String VALID_REGISTER_REQUEST = "{\"email\": \"mickey.mouse@gmail.com\", \"password\": \"mickey\", \"repeatedPassword\": \"mickey\"}";
	private String REGISTER_REQUEST_MISSING_EMAIL = "{\"password\": \"mickey\", \"repeatedPassword\": \"mickey\"}";

//	@Test
//	public void shouldUploadPhotoOnValidRequest() throws Exception {
//		Mockito.when(userService.save(Mockito.any(RegisterRequest.class))).thenReturn(ReturnCode.SUCCESS);
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(PHOTO_ENDPOINT_BASE + "/upload")
//				.content(VALID_REGISTER_REQUEST).contentType(MediaType.MULTIPART_FORM_DATA).content(content)
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//		MockHttpServletResponse response = result.getResponse();
//		assertEquals(HttpStatus.OK.value(), response.getStatus());
//	}

//	private String getPhoto() {
//
//	}
}
