package com.scentbird.tictactoe_app.engine.web.controller;

import com.scentbird.tictactoe_app.engine.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actions")
public class ActionController {

  private final GameService gameService;


  public ActionController(GameService gameService) {
    this.gameService = gameService;
  }

  @GetMapping("/invitation/user-name/{userName}")
  public HttpStatus sendGameRequest(@PathVariable String userName) {
    if (!gameService.isGameRunning()) {
      System.out.printf("User %s calls to play game!%n", userName);
      return HttpStatus.ACCEPTED;
    }
    return HttpStatus.NOT_ACCEPTABLE;
  }

  @GetMapping("/status")
  public HttpStatus getStatus() {
    if (!gameService.isGameRunning()) {
      return HttpStatus.OK;
    }
    return HttpStatus.NOT_ACCEPTABLE;
  }

  @GetMapping("/game/{gameId}/notification/{notification}")
  public HttpStatus notitication(@PathVariable String gameId, @PathVariable String notification) {
    gameService.setGameRunning(true);
    gameService.setGameId(gameId);
    System.out.println(notification);
    return HttpStatus.OK;
  }

  @PostMapping("/game/{gameId}/finish")
  public void finishGame(@PathVariable String gameId) {
    if (gameService.getGameId() != null && gameService.getGameId().equals(gameId)) {
      System.out.println("Game has been finished");
      System.exit(0);
    }
  }

}
