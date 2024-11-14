package com.scentbird.tictactoe_app.engine.service.rest.request_wrapper;

import com.scentbird.tictactoe_app.engine.RestResponseWrapper;
import com.scentbird.tictactoe_app.engine.web.dto.PlayerDto;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class DiscoveryClientRequestService {

  @Value("${scentbird.server-discovery.address}")
  private String serviceDiscoveryUrl;

  private final RestResponseWrapper restResponseWrapper;
  private final ServletWebServerApplicationContext webServerAppContext;


  public DiscoveryClientRequestService(RestResponseWrapper restResponseWrapper,
      ServletWebServerApplicationContext webServerAppContext) {
    this.restResponseWrapper = restResponseWrapper;
    this.webServerAppContext = webServerAppContext;
  }

  public List<String> getAvailablePlayers() {
    String url = getUrl("/available-players");

    ParameterizedTypeReference<List<String>> typeRef = new ParameterizedTypeReference<>() {
    };
    return restResponseWrapper.get(url, typeRef);
  }

  public Boolean registerUser(String userName) {
    String url;
    String ip;
    String port;
    try {
      ip = Inet4Address.getLocalHost().getHostAddress();
      port = String.valueOf(webServerAppContext.getWebServer().getPort());
      url = getUrl("/register");
    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    }
    ParameterizedTypeReference<Boolean> typeRef = new ParameterizedTypeReference<>() {
    };
    return restResponseWrapper.post(url, typeRef, new PlayerDto(userName, ip, port));
  }

  public void unregisterUser(String userName) {
    String url = getUrl(String.format("/unregister?userName=%s", userName));
    ParameterizedTypeReference<HttpStatus> typeRef = new ParameterizedTypeReference<>() {
    };
    restResponseWrapper.get(url, typeRef);
  }

  private String getUrl(String path) {
    return UriComponentsBuilder.fromUriString(serviceDiscoveryUrl + path).toUriString();
  }
}
