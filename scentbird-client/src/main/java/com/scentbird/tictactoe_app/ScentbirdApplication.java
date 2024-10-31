package com.scentbird.tictactoe_app;

import com.scentbird.tictactoe_app.engine.service.ConsoleInputCommandsService;
import com.scentbird.tictactoe_app.engine.service.impl.DiscoveryClientServiceImpl;
import com.scentbird.tictactoe_app.engine.storage.UserInfoStore;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScentbirdApplication implements CommandLineRunner {

  private final DiscoveryClientServiceImpl discoveryService;
  private final UserInfoStore userInfoStore;
  private final ConsoleInputCommandsService consoleInputCommandsService;

  public ScentbirdApplication(UserInfoStore userInfoStore,
      DiscoveryClientServiceImpl discoveryService,
      ConsoleInputCommandsService consoleInputCommandsService) {
    this.discoveryService = discoveryService;
    this.userInfoStore = userInfoStore;
    this.consoleInputCommandsService = consoleInputCommandsService;
  }

  public static void main(String[] args) {
    SpringApplication.run(ScentbirdApplication.class, args);
  }

  @PreDestroy
  public void onShutdown() {
    System.out.println("onShutdown...");
    if (userInfoStore.getUserName() != null) {
      //not the perfect way, but at least something
      discoveryService.unregisterUser(userInfoStore.getUserName());
    }
  }

  @Override
  public void run(String... args) throws Exception {
    consoleInputCommandsService.run(args);
  }
}
