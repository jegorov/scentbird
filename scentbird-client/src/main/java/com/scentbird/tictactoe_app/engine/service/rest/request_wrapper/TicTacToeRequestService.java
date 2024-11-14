package com.scentbird.tictactoe_app.engine.service.rest.request_wrapper;

import com.scentbird.tictactoe_app.engine.RestResponseWrapper;
import com.scentbird.tictactoe_app.engine.web.dto.GameMoveDto;
import com.scentbird.tictactoe_app.engine.web.dto.PlayerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TicTacToeRequestService {

  @Value("${scentbird.server-discovery.address}")
  private String serviceDiscoveryUrl;


  private final RestResponseWrapper restResponseWrapper;


  public TicTacToeRequestService(RestResponseWrapper restResponseWrapper) {
    this.restResponseWrapper = restResponseWrapper;
  }

  public void sendGameRequest(String foeUserName) {
    String url = getUrl("/game/request?foeName=" + foeUserName);

    ParameterizedTypeReference<HttpStatus> typeRef = new ParameterizedTypeReference<>() {
    };
    restResponseWrapper.post(url, typeRef, new PlayerDto(foeUserName));
  }

  public void acceptGameRequest(String foeUserName) {
    String url = getUrl("/game/accept");

    ParameterizedTypeReference<HttpStatus> typeRef = new ParameterizedTypeReference<>() {
    };
    restResponseWrapper.post(url, typeRef, new PlayerDto(foeUserName));
  }

  public String getCurrentBoard(String gameId) {
    String url = getUrl("/game/" + gameId);

    ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<>() {
    };
    return restResponseWrapper.get(url, typeRef);
  }

  public void doMove(String gameId, Integer x, Integer y) {
    String url = getUrl("/game/" + gameId + "/move");

    ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<>() {
    };
    restResponseWrapper.post(url, typeRef, new GameMoveDto(x, y));
  }

  private String getUrl(String path) {
    return UriComponentsBuilder.fromUriString(serviceDiscoveryUrl + path).toUriString();
  }
}
