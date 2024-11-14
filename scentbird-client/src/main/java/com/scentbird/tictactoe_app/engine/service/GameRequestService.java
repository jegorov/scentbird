package com.scentbird.tictactoe_app.engine.service;

import com.scentbird.tictactoe_app.engine.service.rest.wrapper.DiscoveryClientService;
import com.scentbird.tictactoe_app.engine.service.rest.wrapper.TicTacToeService;
import com.scentbird.tictactoe_app.engine.storage.UserInfoStore;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GameRequestService {

  private final DiscoveryClientService discoveryService;
  private final TicTacToeService ticTacToeService;
  private final UserInfoStore userInfoStore;

  public GameRequestService(UserInfoStore userInfoStore,
      DiscoveryClientService discoveryService,
      TicTacToeService ticTacToeService) {
    this.discoveryService = discoveryService;
    this.userInfoStore = userInfoStore;
    this.ticTacToeService = ticTacToeService;
  }

  public Set<String> findPlayers() {
    if (userInfoStore.getUserName() != null) {
      return discoveryService.getAvailablePlayers().stream()
          .filter(el -> !el.equals(userInfoStore.getUserName()))
          .collect(Collectors.toUnmodifiableSet());
    }
    throw new RuntimeException("Please login first");
  }

  public void unregisterUser() {
    discoveryService.unregisterUser(userInfoStore.getUserName());
  }

  public void sendGameRequest(String foeUserName) {
    if (userInfoStore.getUserName() == null) {
      throw new RuntimeException("Please login first");
    }
    ticTacToeService.sendGameRequest(foeUserName);
  }

  public void acceptGameRequest(String foeUserName) {
    if (userInfoStore.getUserName() == null) {
      throw new RuntimeException("Please login first");
    }
    ticTacToeService.acceptGameRequest(foeUserName);
  }
}
