/**
 * 
 */
package com.zoul.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
/**
 * @author kunal.lawand
 *
 */
@Configuration
public class HystixFallBackConfigartion implements FallbackProvider{

	@Override
	public String getRoute() {
		return "university-service";
	}

	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
		return new ClientHttpResponse() {
			
			@Override
			public HttpHeaders getHeaders() {
				  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  headers.setAccessControlAllowCredentials(true);
                  headers.setAccessControlAllowOrigin("*");
                  return headers;
			}
			
			@Override
			public InputStream getBody() throws IOException {
			    return new ByteArrayInputStream("{\"factorA\":\"Sorry, Service is Down!\",\"factorB\":\"?\",\"id\":null}".getBytes());
			}
			
			@Override
			public String getStatusText() throws IOException {
				return HttpStatus.OK.name();
			}
			
			@Override
			public HttpStatus getStatusCode() throws IOException {
				return HttpStatus.OK;
			}
			
			@Override
			public int getRawStatusCode() throws IOException {
				return HttpStatus.OK.value();
			}
			
			@Override
			public void close() {
			}
		};
	}

	
	
}
