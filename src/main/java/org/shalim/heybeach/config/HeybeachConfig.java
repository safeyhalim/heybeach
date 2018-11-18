package org.shalim.heybeach.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeybeachConfig {
	@Value("${image.types}")
	List<String> imageTypes;

	@Bean
	List<String> getImageTypes() {
		return imageTypes;
	}
}
