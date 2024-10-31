package com.scentbird.tictactoe_app.engine.storage;

import org.springframework.stereotype.Component;


//Since this was not a part of the main logic, i didn't work much on storing current user in a storage or maybe session.
// So thats why this is just a component with some simple logic
@Component
public class UserInfoStore {


  private String userName = null;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

}
