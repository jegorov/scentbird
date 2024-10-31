package com.scentbird.scentbirdservicediscovery.engine.service;

import com.scentbird.scentbirdservicediscovery.engine.ResponseWrapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
public class ClientRequestImplService {

  private final ResponseWrapper responseWrapper;
  private final ClientService clientService;

  public ClientRequestImplService(ResponseWrapper responseWrapper, ClientService clientService) {
    this.responseWrapper = responseWrapper;
    this.clientService = clientService;
  }

  public boolean sendRequest(String clientName, String foeName) {
    String url = clientService.getClientUrl(foeName);
    ParameterizedTypeReference<HttpStatus> typeRef = new ParameterizedTypeReference<>() {
    };
    if (responseWrapper.getResponse(url + "/actions/invitation/user-name/" + clientName, typeRef)
        .is2xxSuccessful()) {
      return true;
    }
    return false;
  }

  public boolean getPlayerStatus(String clientName) {
    String url = clientService.getClientUrl(clientName);
    ParameterizedTypeReference<HttpStatus> typeRef = new ParameterizedTypeReference<>() {
    };
    HttpStatus status = responseWrapper.getResponse(url + "/actions/status", typeRef);
    if (status == HttpStatus.OK) {
      return true;
    }
    if (status == HttpStatus.NOT_ACCEPTABLE) {
      System.out.println("Player is already playing");
    }
    return false;
  }

  public boolean sendGameNotification(String clientName, String gameId, String notification) {
    String url = clientService.getClientUrl(clientName);
    ParameterizedTypeReference<HttpStatus> typeRef = new ParameterizedTypeReference<>() {
    };
    HttpStatus status = responseWrapper.getResponse(
        url + String.format("/actions/game/%s/notification/", gameId) + notification,
        typeRef);
    return status == HttpStatus.OK;
  }

  public void finishGame(String clientName, String gameId) {
    String url = clientService.getClientUrl(clientName);
    ParameterizedTypeReference<HttpStatus> typeRef = new ParameterizedTypeReference<>() {
    };
    try {
      responseWrapper.postResponse(
          url + String.format("/actions/game/%s/finish", gameId),
          typeRef);
    } catch (ResourceAccessException e) {
      // do nothing
    }
  }

}
