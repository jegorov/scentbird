package com.scentbird.scentbirdservicediscovery.web.dto;

public class PlayerDto {

  private String name;
  private String port;
  private String url;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getFullAddress() {
    return "http://" + url + ":" + port;
  }
}
