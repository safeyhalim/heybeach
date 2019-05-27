package org.shalim.heybeach.services.validators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.shalim.heybeach.config.HeybeachConfig;
import org.shalim.heybeach.domain.requests.IRequest;
import org.shalim.heybeach.domain.requests.PhotoUploadRequest;
import org.shalim.heybeach.util.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = HeybeachConfig.class)
@SpringBootTest(classes = PhotoRequestValidator.class)
@RunWith(SpringRunner.class)
public class PhotoRequestValidatorTest {

	@Autowired
	PhotoRequestValidator photoRequestValidator;
	static final String PHOTO_FILE_NAME = "donald.png";

	@Test
	public void shouldAcceptAValidPhotoUploadRequest() throws FileNotFoundException, IOException {
		List<ReturnCode> errors = new ArrayList<ReturnCode>();
		IRequest photoUploadRequest = photoRequestValidator.validateRequest(Arrays.asList(getValidPhoto(true)), errors);
		assertNotEquals(photoUploadRequest, null);
		assertThat(photoUploadRequest instanceof PhotoUploadRequest);
	}

	private MultipartFile getValidPhoto(boolean isValidType) throws FileNotFoundException, IOException {
		Resource resource = new ClassPathResource(PHOTO_FILE_NAME);
		String contentType = isValidType ? "image/png" : "text/plain";
		String path = resource.getURL().getPath();
		File file = new File(path);
		FileInputStream input = new FileInputStream(file);
		MultipartFile multipartFile = new MockMultipartFile(PHOTO_FILE_NAME, file.getName(), contentType,
				IOUtils.toByteArray(input));
		return multipartFile;
	}
}
