package com.scentbird.tictactoe_app.engine.handler.console_command.impl;

import com.scentbird.tictactoe_app.engine.handler.console_command.CommandHandler;
import com.scentbird.tictactoe_app.engine.service.RegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RegisterCommandHandler implements CommandHandler {

  private static final Logger LOG = LoggerFactory.getLogger(RegisterCommandHandler.class);
  private final RegisterService registerService;

  public RegisterCommandHandler(RegisterService registerService) {
    this.registerService = registerService;
  }

  @Override
  public String getCommand() {
    return "register";
  }

  @Override
  public void handle(String input) {
    String userName = input.split("%")[1];
    registerService.register(userName);
    LOG.warn("Successfully logged in");
  }
}
