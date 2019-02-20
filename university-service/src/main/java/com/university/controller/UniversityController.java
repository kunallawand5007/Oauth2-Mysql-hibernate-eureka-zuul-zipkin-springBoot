/**
 * 
 */
package com.university.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.university.pojo.University;

/**
 * @author kunal.lawand
 *
 */
@RestController
public class UniversityController {

	private List<University> universities = new ArrayList<University>();

	@Autowired
	private DiscoveryClient discoveryClient;
	
	
	@PostConstruct
	public void init() {

		University university = new University();
		university.setCity("Pune");
		university.setName("University of pune");
		university.setNumber(1);
		university.setRank(10);

		universities.add(university);

	}

	@RequestMapping(value = "/", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<List<University>> universites() {
		return new ResponseEntity<List<University>>(universities, HttpStatus.OK);
	}

	@RequestMapping(value = "/uni/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<List<University>> universites(@PathVariable("id") String id) {
		List<University> collect = universities.stream().filter(s -> s.equals(id)).collect(Collectors.toList());
		return new ResponseEntity<List<University>>(collect, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/uni/students", method = RequestMethod.GET, headers = "Accept=application/json")
	@HystrixCommand(fallbackMethod="fallback")
	public String students() {
		List<String> names = new ArrayList<String>();
		
		
			//List<ServiceInstance> instances = discoveryClient.getInstances("student-service");
			//System.out.println(instances.size());
			
			//ServiceInstance serviceInstance = instances.get(0);
//			int port = serviceInstance.getPort();
//			URI uri = serviceInstance.getUri();
//			System.out.println("PORT:"+port);
//			System.out.println("URI:"+uri);
			
			System.out.println("#E###########################################################");
			
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set("Authorization", "Bearer "+"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzdHNlcnZpY2UiXSwidXNlcl9uYW1lIjoia3VuYWxsYXZhbmRAZ21haWwuY29tIiwic2NvcGUiOlsicmVhZCJdLCJleHAiOjE1NTA1ODM5NTksImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiIyOGE1MzZhMC1lNGYyLTRkODktOWM0Ny1mNjA3YTU1OTQxODMiLCJjbGllbnRfaWQiOiJ3ZWJhcHAifQ.KPtsHFU2Bx2jtTj5sojjibpn-Sd-1_vtiB0Kgc8eM50");
				RestTemplate restTemplate=new RestTemplate();
				HttpEntity<String> entity = new HttpEntity<String>(headers);
				
				ResponseEntity<String> forEntity = restTemplate.exchange("http://NIT-WKS-0127.nitorinfotech.net:8083"+"/student/1", HttpMethod.GET, entity, String.class);
				System.out.println("Response :"+forEntity.getBody());
			} catch (RestClientException e) {
				e.printStackTrace();
			}

		return "Hello";
	}	
	
	
	public String fallback() {
		return "CIRCUIT BREAKER ENABLED!!!No Response From Student Service at this moment. Service will be back shortly - " + new Date();
	}

	
}
