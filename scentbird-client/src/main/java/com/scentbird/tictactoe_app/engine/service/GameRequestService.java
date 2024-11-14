package com.scentbird.tictactoe_app.engine.service;

import com.scentbird.tictactoe_app.engine.service.rest.request_wrapper.DiscoveryClientRequestService;
import com.scentbird.tictactoe_app.engine.service.rest.request_wrapper.TicTacToeRequestService;
import com.scentbird.tictactoe_app.engine.storage.UserInfoStore;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GameRequestService {

  private final DiscoveryClientRequestService discoveryService;
  private final TicTacToeRequestService ticTacToeRequestService;
  private final UserInfoStore userInfoStore;

  public GameRequestService(UserInfoStore userInfoStore,
      DiscoveryClientRequestService discoveryService,
      TicTacToeRequestService ticTacToeRequestService) {
    this.discoveryService = discoveryService;
    this.userInfoStore = userInfoStore;
    this.ticTacToeRequestService = ticTacToeRequestService;
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
    ticTacToeRequestService.sendGameRequest(foeUserName);
  }

  public void acceptGameRequest(String foeUserName) {
    if (userInfoStore.getUserName() == null) {
      throw new RuntimeException("Please login first");
    }
    ticTacToeRequestService.acceptGameRequest(foeUserName);
  }
}
