package com.myproject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.HateoasSortHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.PagedResourcesAssemblerArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.myproject.api.domain.CustomerResource;
import com.myproject.api.hypermedia.CustomerResourceAssembler;
import com.myproject.controller.CustomerController;
@Configuration
public class WebServiceConfig {

  @Bean
	public CustomerResourceAssembler customerResourceAssembler() {
		return new CustomerResourceAssembler(CustomerController.class, CustomerResource.class);
  }

  @Bean
  public HateoasPageableHandlerMethodArgumentResolver pageableResolver() {
    return new HateoasPageableHandlerMethodArgumentResolver(sortResolver());
  }

  @Bean
  public HateoasSortHandlerMethodArgumentResolver sortResolver() {
    return new HateoasSortHandlerMethodArgumentResolver();
  }

  @Bean
  public PagedResourcesAssembler<?> pagedResourcesAssembler() {
    return new PagedResourcesAssembler<Object>(pageableResolver(), null);
  }

  @Bean
  public PagedResourcesAssemblerArgumentResolver pagedResourcesAssemblerArgumentResolver() {
    return new PagedResourcesAssemblerArgumentResolver(pageableResolver(), null);
  }

	// enable access for external Swagger UI site
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/v2/api-docs").allowedOrigins("*");
				// registry.addMapping("/api").allowedOrigins("*");
			}
		};
	}



}
