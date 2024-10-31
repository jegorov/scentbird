package com.scentbird.tictactoe_app.engine.service;

import java.util.Scanner;
import org.springframework.stereotype.Service;

@Service
public class ConsoleInputCommandsService {

  private final PreGameActionService preGameActionService;
  private final GameService game;

  public ConsoleInputCommandsService(PreGameActionService preGameActionService,
      GameService game) {
    this.preGameActionService = preGameActionService;
    this.game = game;
  }


  // harcoded logic for console  logic
  public void run(String... args) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.println("Enter a command (type 'help' to get commands): ");
      String input = scanner.nextLine().toLowerCase();

      if (input.contains("%")) {
        continue;
      }
      input = input.replaceAll("\\s+", "%");

      try {
        if (input.equals("help")) {
          System.out.println("""
              Available commands:
              1. register userName (reg)
              2. logout
              3. players (p)
              4. request foeName (req)
              5. accept foeName (ac)
              6. move x:y (m)
              7. board (b)
              P.S: '%' symbol is restricted to use.
              """);
        } else if (input.startsWith("register") || input.startsWith("reg")) {
          preGameActionService.register(input.split("%")[1]);
          System.out.println("Successfully logged in");
        } else if (input.equals("logout")) {
          preGameActionService.unregisterUser();
          System.out.println("Successfully logged out");
        } else if (input.equals("players") || input.equals("p")) {
          System.out.println("Player list:");
          System.out.println(preGameActionService.findPlayers());
        } else if (input.startsWith("request") || input.startsWith("req")) {
          if (!game.isGameRunning()) {
            String foeName = input.split("%")[1];
            preGameActionService.sendGameRequest(foeName);
          } else {
            System.out.println("Game is already started");
          }
        } else if (input.startsWith("accept") || input.startsWith("ac")) {
          if (!game.isGameRunning()) {
            String foeName = input.split("%")[1];
            preGameActionService.acceptGameRequest(foeName);
          } else {
            System.out.println("Game is already started");
          }
        } else if (input.equals("board") || input.equals("b")) {
          System.out.println(game.getBoard());
        } else if (input.startsWith("move") || input.startsWith("m")) {
          int x = Integer.parseInt(input.split("%")[1].split(":")[0]);
          int y = Integer.parseInt(input.split("%")[1].split(":")[1]);
          game.doMove(x, y);
          System.out.println(game.getBoard());

        } else {
          System.out.println("Command not found!");
        }

      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
