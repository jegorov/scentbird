package com.scentbird.tictactoe_app.engine.handler.console_command;

public interface CommandHandler {

  String getCommand();

  void handle(String input);
}