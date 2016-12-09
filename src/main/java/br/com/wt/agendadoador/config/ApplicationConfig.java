package br.com.wt.agendadoador.config;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan
@EnableWebMvc

public class ApplicationConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("PUT", "GET", "DELETE", "OPTIONS", "PATCH", "POST");
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> httpMessageConverters) {
		httpMessageConverters.add(new MappingJackson2HttpMessageConverter());
	}
}