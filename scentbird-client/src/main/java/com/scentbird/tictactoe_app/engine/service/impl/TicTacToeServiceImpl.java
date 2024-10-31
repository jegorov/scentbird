package com.scentbird.tictactoe_app.engine.service.impl;

import com.scentbird.tictactoe_app.engine.ResponseWrapper;
import com.scentbird.tictactoe_app.engine.storage.UserInfoStore;
import com.scentbird.tictactoe_app.engine.web.dto.GameMoveDto;
import com.scentbird.tictactoe_app.engine.web.dto.PlayerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TicTacToeServiceImpl {

  @Value("${scentbird.server-discovery.address}")
  private String serviceDiscoveryUrl;


  private final ResponseWrapper responseWrapper;
  private final DiscoveryClientServiceImpl discoveryService;


  public TicTacToeServiceImpl(ResponseWrapper responseWrapper,
      UserInfoStore userInfoStore, DiscoveryClientServiceImpl discoveryService) {
    this.responseWrapper = responseWrapper;
    this.discoveryService = discoveryService;
  }

  public boolean sendFoeGameRequest(String foeUserName) {
    String url = getUrl("/game/request?foeName=" + foeUserName);

    ParameterizedTypeReference<HttpStatus> typeRef = new ParameterizedTypeReference<>() {
    };
    HttpStatus httpStatus = responseWrapper.postResponse(url, typeRef, new PlayerDto(foeUserName));
    return httpStatus.value() == 202;
  }

  public void acceptGameRequest(String foeUserName) {
    String url = getUrl("/game/accept");

    ParameterizedTypeReference<HttpStatus> typeRef = new ParameterizedTypeReference<>() {
    };
    responseWrapper.postResponse(url, typeRef, new PlayerDto(foeUserName));
  }

  public String getCurrentBoard(String gameId) {
    String url = getUrl("/game/" + gameId);

    ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<>() {
    };
    return responseWrapper.getResponse(url, typeRef);
  }

  public void doMove(String gameId, Integer x, Integer y) {
    String url = getUrl("/game/" + gameId + "/move");

    ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<>() {
    };
    responseWrapper.postResponse(url, typeRef, new GameMoveDto(x, y));
  }

  private String getUrl(String path) {
    return UriComponentsBuilder.fromUriString(serviceDiscoveryUrl + path).toUriString();
  }
}
