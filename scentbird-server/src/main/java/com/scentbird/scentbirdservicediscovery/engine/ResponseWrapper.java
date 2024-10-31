package com.scentbird.scentbirdservicediscovery.engine;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ResponseWrapper {

  private final RestTemplate restTemplate;

  public ResponseWrapper(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public <T> T getResponse(String url, ParameterizedTypeReference<T> typeReference) {
    ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, null, typeReference);

    if (response.getStatusCode() == HttpStatus.OK) {
      return response.getBody();
    } else {
      throw new RuntimeException("Failed to fetch. Status code: " + response.getStatusCode());
    }
  }

  public <T> T postResponse(String url, ParameterizedTypeReference<T> typeReference) {
    ResponseEntity<T> response = restTemplate.exchange(
        url,
        HttpMethod.POST,
        null,
        typeReference
    );

    if (response.getStatusCode() == HttpStatus.OK) {
      return response.getBody();
    } else {
      throw new RuntimeException(
          "Failed to fetch. Status code: " + response.getStatusCode());
    }
  }
}
