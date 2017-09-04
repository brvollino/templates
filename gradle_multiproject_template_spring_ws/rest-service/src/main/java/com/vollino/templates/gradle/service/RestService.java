package com.vollino.templates.gradle.service;

import com.vollino.templates.gradle.service.domain.GetOutput;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestService {
	
	@RequestMapping(path = "/post-endpoint", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public void postEndpoint(@RequestParam("param") String param) {
		//do nothing
	}

	@RequestMapping(path = "/{param}/get-endpoint", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GetOutput> getEndpoint(@PathVariable String param) {
		
		return new ArrayList<>();
	}
	
	@RequestMapping(path = "/", method = RequestMethod.DELETE)
	public void delete() {
		// do nothing
	}
}
