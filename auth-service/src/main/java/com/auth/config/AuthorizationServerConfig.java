/**
 * 
 */
package com.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author kunal.lawand
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private CustomUserDeatails customUserDeatails;
	
	
	@Autowired
	PasswordEncoder passwordEncoder;

//	@Bean
//	TokenStore tokenStore() {
//		TokenStore tokenStore = new InMemoryTokenStore();
//		return tokenStore;
//	}

//	@Bean
//	@Primary
//	public DefaultTokenServices tokenServices() {
//		DefaultTokenServices tokenServices = new DefaultTokenServices();
//		tokenServices.setSupportRefreshToken(true);
//		tokenServices.setTokenStore(tokenStore());
//		tokenServices.setAccessTokenValiditySeconds(660);
//		tokenServices.setRefreshTokenValiditySeconds(36000);
//		return tokenServices;
//	}
	
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("as466gf");
        return converter;
	}

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
	

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		super.configure(endpoints);
		endpoints.authenticationManager(authManager).userDetailsService(customUserDeatails).tokenStore(tokenStore()).accessTokenConverter(accessTokenConverter());
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		super.configure(security);
		security.allowFormAuthenticationForClients();
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("webapp").authorizedGrantTypes("password", "refresh_token", "client_credentials").scopes("read", "write").resourceIds("restservice").secret(passwordEncoder.encode("websecret")).accessTokenValiditySeconds(660);
	}

	/**
	 * Configure the {@link AuthenticationManagerBuilder} with initial
	 * configuration to setup users.
	 * 
	 * @author anilallewar
	 *
	 */
	@Configuration
	@Order(Ordered.LOWEST_PRECEDENCE - 20)
	protected static class AuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {

		
		//@Autowired
		//private DataSource dataSource;
		
		@Autowired
		private CustomUserDeatails customUserDeatails;
		
		 @Bean
		    public static PasswordEncoder passwordEncoder() {
		        return new BCryptPasswordEncoder();
		    }
		
		/**
		 * Setup 2 users with different roles
		 */
		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			// @formatter:off
			//auth.inMemoryAuthentication().withUser("user1").password("password1").roles("USER").and().withUser("admin").password("password2").roles("ADMIN");
			// @formatter:on
			auth.userDetailsService(customUserDeatails).passwordEncoder(passwordEncoder());
			
		}
		
		 
	}
}
