package edu.pnu.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RootConfig implements WebMvcConfigurer{

	@Bean
	public ModelMapper getMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
					.setFieldMatchingEnabled(true)
					.setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
					.setMatchingStrategy(MatchingStrategies.LOOSE);

		return modelMapper;
	}
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE","REQUESTS");
	}
}
