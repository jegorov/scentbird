package com.scentbird.scentbirdservicediscovery.web.controller;

import com.scentbird.scentbirdservicediscovery.engine.dto.PlayerInfo;
import com.scentbird.scentbirdservicediscovery.engine.service.ClientService;
import com.scentbird.scentbirdservicediscovery.web.dto.PlayerDto;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientServiceController {

  private final ClientService clientService;

  public ClientServiceController(ClientService clientService) {
    this.clientService = clientService;
  }

  @GetMapping("available-players")
  public Set<String> getAvailablePlayers() {
    return clientService.getAvailablePlayers();
  }

  @PostMapping("register")
  public Boolean register(@RequestBody PlayerDto player) {
    return clientService.registerClient(
        new PlayerInfo(player.getName(), player.getFullAddress()));
  }

  @GetMapping("unregister")
  public HttpStatus unregister(@RequestParam("userName") String userName) {
    clientService.unregisterClient(userName);
    return HttpStatus.OK;
  }
}
