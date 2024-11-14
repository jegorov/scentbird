package com.scentbird.tictactoe_app.engine.web.controller;

import com.scentbird.tictactoe_app.engine.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
  private final GameService gameService;

  public UserController(GameService gameService) {
    this.gameService = gameService;
  }

  @PostMapping("/invite")
  public HttpStatus sendGameRequest(@RequestParam("userName") String userName) {
    if (!gameService.isGameRunning()) {
      LOG.warn("User {} calls to play game!", userName);
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

}
