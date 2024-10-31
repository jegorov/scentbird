package com.scentbird.scentbirdservicediscovery.engine.dto;

public class PlayerInfo {

  private String uniqName;
  private String address;
  private boolean isAvailable;
  private Character type;


  public PlayerInfo(String uniqName, String address) {
    this.uniqName = uniqName;
    this.address = address;
    this.isAvailable = true;
  }

  //for tests
  public PlayerInfo(String uniqName, String address, Character type) {
    this.uniqName = uniqName;
    this.address = address;
    this.type = type;
    this.isAvailable = true;
  }

  public String getUniqName() {
    return uniqName;
  }

  public void setUniqName(String uniqName) {
    this.uniqName = uniqName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Character getType() {
    return type;
  }

  public void setType(Character type) {
    this.type = type;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public void setAvailable(boolean available) {
    isAvailable = available;
  }
}
