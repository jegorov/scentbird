package com.scentbird.tictactoe_app.engine.service;

import com.scentbird.tictactoe_app.engine.service.rest.request_wrapper.DiscoveryClientRequestService;
import com.scentbird.tictactoe_app.engine.storage.UserInfoStore;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {


  private final DiscoveryClientRequestService discoveryService;
  private final UserInfoStore userInfoStore;

  public RegisterService(UserInfoStore userInfoStore,
      DiscoveryClientRequestService discoveryService) {
    this.discoveryService = discoveryService;
    this.userInfoStore = userInfoStore;
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
}
