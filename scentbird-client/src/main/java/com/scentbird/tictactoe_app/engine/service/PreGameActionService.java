package com.scentbird.tictactoe_app.engine.service;

import com.scentbird.tictactoe_app.engine.service.impl.DiscoveryClientServiceImpl;
import com.scentbird.tictactoe_app.engine.service.impl.TicTacToeServiceImpl;
import com.scentbird.tictactoe_app.engine.storage.UserInfoStore;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PreGameActionService {

  private final DiscoveryClientServiceImpl discoveryService;
  private final TicTacToeServiceImpl ticTacToeServiceImpl;
  private final UserInfoStore userInfoStore;

  public PreGameActionService(UserInfoStore userInfoStore,
      DiscoveryClientServiceImpl discoveryService,
      TicTacToeServiceImpl ticTacToeServiceImpl) {
    this.discoveryService = discoveryService;
    this.userInfoStore = userInfoStore;
    this.ticTacToeServiceImpl = ticTacToeServiceImpl;
  }

  public void register(String userName) {
    if (userInfoStore.getUserName() != null) {
      throw new RuntimeException("You are already registered!");
    }
    Boolean registered = discoveryService.registerUser(userName);
    if (!registered) {
      throw new RuntimeException("Please try again with another name");
    }
    userInfoStore.setUserName(userName);
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
    ticTacToeServiceImpl.sendFoeGameRequest(foeUserName);
  }

  public void acceptGameRequest(String foeUserName) {
    if (userInfoStore.getUserName() == null) {
      throw new RuntimeException("Please login first");
    }
    ticTacToeServiceImpl.acceptGameRequest(foeUserName);
  }
}
