/**
 * 
 */
package com.student.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kunal.lawand
 *
 */
@RestController
public class StudentController {

	@RequestMapping(value = "/student", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<String> student() {
		return new ResponseEntity<String>("Hello I am Running..!!!", HttpStatus.OK);
	}

	
	@RequestMapping(value = "/student/1", method = RequestMethod.GET, headers = "Accept=application/json")
	public String student1() {
		return new String("Hello userd");
	}
	
}
