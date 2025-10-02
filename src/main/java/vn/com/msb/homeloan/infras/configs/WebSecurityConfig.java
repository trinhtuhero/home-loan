package vn.com.msb.homeloan.infras.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import vn.com.msb.homeloan.api.filter.APIKeyAuthenticationFilter;
import vn.com.msb.homeloan.api.filter.CMSAuthenticationFilter;
import vn.com.msb.homeloan.api.filter.JwtAuthenticationFilter;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

  @Order(1)
  @Configuration
  public static class LDPSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.cors()
          .and()
          .csrf().disable()
          .sessionManagement().sessionCreationPolicy(
              SessionCreationPolicy.STATELESS)
          .and()
          .antMatcher("/api/v1/ldp/**")
          .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
          .authorizeRequests()
          .antMatchers(
              "/api/v1/ldp/loan-applications/**",
              "/api/v1/ldp/contact-person/**",
              "/api/v1/ldp/loan-payer/**",
              "/api/v1/ldp/married-person/**",
              "/api/v1/ldp/other-incomes/**",
              "/api/v1/ldp/salary-incomes/**",
              "/api/v1/ldp/business-incomes/**",
              "/api/v1/ldp/collateral/**",
              "/api/v1/ldp/profile/**",
              "/api/v1/ldp/loan-upload/**",
              "/api/v1/ldp/id-card/**",
              "/api/v1/ldp/comment/**",
              "/api/v1/ldp/partner-channel/**",
              "/api/v1/ldp/feedback/**",
              "/api/v1/ldp/overdraft/**"

          ).authenticated()
          .anyRequest().permitAll()
          .and()
          .exceptionHandling().authenticationEntryPoint(securityException401EntryPoint());
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
      return new JwtAuthenticationFilter();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
      web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
          "/configuration/**", "/swagger-ui.html", "/webjars/**");
    }
  }

  @Order(2)
  @Configuration
  public static class CMSSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private EnvironmentProperties envProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.cors()
          .and()
          .csrf().disable()
          .sessionManagement().sessionCreationPolicy(
              SessionCreationPolicy.STATELESS)
          .and()
          .antMatcher("/api/v1/cms/**")
          .addFilterBefore(cmsAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
          .authorizeRequests()
          .antMatchers(
              "/api/v1/cms/cms-user/**",
              "/api/v1/cms/loan-application-comment/**",
              "/api/v1/cms/loan-application/**",
              "/api/v1/cms/loan-upload/**",
              "/api/v1/cms/upload-file-comment/**",
              "/api/v1/cms/upload-file-status/**",
              "/api/v1/cms/asset_evaluate/**",
              "/api/v1/cms/business-incomes/**",
              "/api/v1/cms/collateral/**",
              "/api/v1/cms/collateral-owner/**",
              "/api/v1/cms/contact-person/**",
              "/api/v1/cms/loan-payer/**",
              "/api/v1/cms/married-person/**",
              "/api/v1/cms/other-evaluate/**",
              "/api/v1/cms/other-incomes/**",
              "/api/v1/cms/salary-incomes/**",
              "/api/v1/cms/tab-action/**",
              "/api/v1/cms/cic/**",
              "/api/v1/cms/credit-appraisal/**",
              "/api/v1/cms/css/**",
              "/api/v1/cms/credit-card/**",
              "/api/v1/cms/op-risk/**",
              "/api/v1/cms/organization/**",
              "/api/v1/cms/id-card/**",
              "/api/v1/cms/creditworthiness-item/**",
              "/api/v1/cms/exception-item/**",
              "/api/v1/cms/credit-institution/**",
              "/api/v1/cms/field-survey/**",
              "/api/v1/cms/loan-application-item/**",
              "/api/v2/cms/loan-application-item/**",
              "/api/v1/cms/report/**",
              "/api/v1/cms/aml/**",
              "/api/v1/cms/m-value/**",
              "/api/v1/cms/overdraft/**"
          ).authenticated()
          .anyRequest().permitAll()
          .and()
          .exceptionHandling().authenticationEntryPoint(securityException401EntryPoint());
    }

    @Bean
    public CMSAuthenticationFilter cmsAuthenticationFilter() {
      return new CMSAuthenticationFilter();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
      web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
          "/configuration/**", "/swagger-ui.html", "/webjars/**");
    }
  }

  @Order(3)
  @Configuration
  public class ApiKeySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("mockSecretRetriever")
    SecretRetriever mockSecretRetriever;

    @Bean
    AuthenticationProvider getAPIAuthenticationProvider() {
      return new ApiKeyAuthenticationProvider(mockSecretRetriever);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
      auth.authenticationProvider(getAPIAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.cors()
          .and()
          .csrf().disable()
          .sessionManagement().sessionCreationPolicy(
              SessionCreationPolicy.STATELESS)
          .and()
          .antMatcher("/api/v1/integration/**")
          .addFilterBefore(new APIKeyAuthenticationFilter(authenticationManager()),
              BasicAuthenticationFilter.class)
          .authorizeRequests()
          .antMatchers(
              "/api/v1/integration/loan-application/**"
          ).authenticated()
          .anyRequest().permitAll()
          .and()
          .exceptionHandling().authenticationEntryPoint(securityException401EntryPoint());
    }
  }

  @Bean
  public static Http401UnauthorizedEntryPoint securityException401EntryPoint() {
    return new Http401UnauthorizedEntryPoint();
  }
}
