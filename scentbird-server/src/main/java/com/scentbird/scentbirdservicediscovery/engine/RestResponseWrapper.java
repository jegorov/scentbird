package com.scentbird.scentbirdservicediscovery.engine;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestResponseWrapper {

  private final RestTemplate restTemplate;

  public RestResponseWrapper(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public <T> T get(String url, ParameterizedTypeReference<T> typeReference) {
    ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, null, typeReference);

    if (response.getStatusCode() == HttpStatus.OK) {
      return response.getBody();
    } else {
      throw new RuntimeException("Failed to fetch. Status code: " + response.getStatusCode());
    }
  }

  public <T> T post(String url, ParameterizedTypeReference<T> typeReference) {
    return post(url, typeReference, null);
  }

  public <T> T post(String url, ParameterizedTypeReference<T> typeReference, HttpHeaders headers) {
    HttpEntity entity = null;
    if (headers != null) {
      entity = new HttpEntity<>(headers);
    }

    ResponseEntity<T> response = restTemplate.exchange(
        url,
        HttpMethod.POST,
        entity,
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