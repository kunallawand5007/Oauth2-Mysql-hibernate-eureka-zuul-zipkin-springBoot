/**
 * 
 */
package com.student.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author kunal.lawand
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		//http.csrf().disable().authorizeRequests().antMatchers("/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/v2/**").permitAll().and().authorizeRequests().antMatchers("/student").hasAnyAuthority("ROLE_ADMIN")
		//.antMatchers("/student/**").hasAnyAuthority("ROLE_USER");
		http.csrf().disable().authorizeRequests().antMatchers("/student").hasAnyAuthority("ROLE_ADMIN").antMatchers("/student/**").hasAnyAuthority("ROLE_USER");
	}
	@Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
         resources.resourceId("restservice");
    }
}
