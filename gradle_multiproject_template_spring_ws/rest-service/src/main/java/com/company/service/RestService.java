package com.company.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.company.service.domain.GetOutput;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
