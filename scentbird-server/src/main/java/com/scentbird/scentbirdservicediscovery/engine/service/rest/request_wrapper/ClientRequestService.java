package com.scentbird.scentbirdservicediscovery.engine.service.rest.request_wrapper;

import com.scentbird.scentbirdservicediscovery.engine.RestResponseWrapper;
import com.scentbird.scentbirdservicediscovery.engine.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
public class ClientRequestService {

  private static final Logger LOG = LoggerFactory.getLogger(ClientRequestService.class);
  private final RestResponseWrapper restResponseWrapper;
  private final ClientService clientService;

  public ClientRequestService(RestResponseWrapper restResponseWrapper,
      ClientService clientService) {
    this.restResponseWrapper = restResponseWrapper;
    this.clientService = clientService;
  }

  public boolean sendRequest(String userName, String foeName) {
    String url = clientService.getClientUrl(foeName);
    ParameterizedTypeReference<HttpStatus> typeRef = new ParameterizedTypeReference<>() {
    };
    HttpHeaders headers = new HttpHeaders();
    headers.set("userName", userName);
    return restResponseWrapper.post(url + "/users/invite" + userName, typeRef, headers)
        .is2xxSuccessful();
  }

  public boolean getPlayerStatus(String clientName) {
    String url = clientService.getClientUrl(clientName);
    ParameterizedTypeReference<HttpStatus> typeRef = new ParameterizedTypeReference<>() {
    };
    HttpStatus status = restResponseWrapper.get(url + "/users/status", typeRef);
    if (status == HttpStatus.OK) {
      return true;
    }
    if (status == HttpStatus.NOT_ACCEPTABLE) {
      LOG.error("Player is already playing");
    }
    return false;
  }

  public boolean sendGameNotification(String clientName, String gameId, String notification) {
    String url = clientService.getClientUrl(clientName);
    ParameterizedTypeReference<HttpStatus> typeRef = new ParameterizedTypeReference<>() {
    };
    HttpStatus status = restResponseWrapper.get(
        url + String.format("/game/%s/notification/", gameId) + notification,
        typeRef);
    return status == HttpStatus.OK;
  }

  public void finishGame(String clientName, String gameId) {
    String url = clientService.getClientUrl(clientName);
    ParameterizedTypeReference<HttpStatus> typeRef = new ParameterizedTypeReference<>() {
    };
    try {

      restResponseWrapper.post(
          url + String.format("/game/%s/finish", gameId),
          typeRef);
    } catch (ResourceAccessException e) {
      // do nothing
    }
  }

}
