package vn.com.msb.homeloan.infras.configs;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
      "classpath:/META-INF/resources/", "classpath:/resources/",
      "classpath:/static/", "classpath:/public/"};

  /*
   * Create MessageSource bean
   */
  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:i18n/messages");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    //  Set routes that allow cross domain routing
    registry.addMapping("/**")
        //  Set the domain name that allows cross domain requests
        //.allowedOrigins("*")
        // Cross domain configuration error , take .allowedOrigins Replace with .allowedOriginPatterns that will do .
        .allowedOriginPatterns("*")
        //  Whether to allow certificates （cookies）
        .allowCredentials(true)
        //  Set allowed methods
        .allowedMethods("*")
        //  Cross domain allow time
        .maxAge(3600);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    if (!registry.hasMappingForPattern("/**")) {
      registry.addResourceHandler("/**").addResourceLocations(
          CLASSPATH_RESOURCE_LOCATIONS);
    }
  }

}