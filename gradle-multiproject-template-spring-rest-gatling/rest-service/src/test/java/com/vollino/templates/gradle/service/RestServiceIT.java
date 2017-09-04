package com.vollino.templates.gradle.service;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vollino.templates.gradle.service.domain.GetOutput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriTemplateHandler;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class RestServiceIT {
	
	@Autowired
	private RestService restService;
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	private String encodeParam(String param) {
		try {
			return URLEncoder.encode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	private URI processUrlTemplate(String urlTemplate, String... params) {
		UriTemplateHandler uriTemplatehandler = restTemplate.getRestTemplate().getUriTemplateHandler();
		List<String> encodedParams = Stream.of(params).map(this::encodeParam).collect(Collectors.toList());
		
		return uriTemplatehandler.expand(urlTemplate, encodedParams);
	}
	
	@Test
	public void shouldStartService() {
		assertNotNull(restService);
	}
	
	@Test
	public void shouldAcceptPostRequest() {
		RequestEntity<String> request = RequestEntity
				.post(processUrlTemplate("/post-endpoint"))
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.body("param=value");
			
		ResponseEntity<String> response = restTemplate.exchange(request, String.class);
				
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
	}
	
	@Test
	public void shouldAcceptGetRequest() {
		RequestEntity<Void> request = RequestEntity
				.get(processUrlTemplate("/{param}/get-endpoint", "value"))
				.accept(MediaType.APPLICATION_JSON)
				.build();
				
		ResponseEntity<List<GetOutput>> response = restTemplate.exchange(request,
				new ParameterizedTypeReference<List<GetOutput>>() {});
				
		assertThat(response.toString(), response.getStatusCode(), equalTo(HttpStatus.OK));
		assertNotNull(response.getBody());
	}
	
	@Test
	public void shouldAcceptDeleteRequest() {		
		RequestEntity<Void> request = RequestEntity.delete(processUrlTemplate("/")).build();
				
		ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);
				
		assertThat(response.toString(), response.getStatusCode(), equalTo(HttpStatus.OK));
	}
}
