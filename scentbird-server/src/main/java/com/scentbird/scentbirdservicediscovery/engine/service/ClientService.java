package com.scentbird.scentbirdservicediscovery.engine.service;

import com.scentbird.scentbirdservicediscovery.engine.dto.PlayerInfo;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

  private final ConcurrentMap<String, PlayerInfo> clientsList = new ConcurrentHashMap<>();

  public ConcurrentMap<String, PlayerInfo> getClientsList() {
    return clientsList;
  }

  public boolean registerClient(PlayerInfo playerInfo) {
    if (clientsList.get(playerInfo.getUniqName()) != null) {
      return false;
    }
    clientsList.put(playerInfo.getUniqName(), playerInfo);
    return true;
  }

  public void unregisterClient(String userName) {
    clientsList.remove(userName);
  }

  public void changeClientAvailability(String playerName, boolean available) {
    PlayerInfo playerInfo = clientsList.get(playerName);
    playerInfo.setAvailable(available);
    clientsList.put(playerName, playerInfo);
  }

  public Set<String> getAvailablePlayers() {
    return clientsList.entrySet().stream().filter(el -> el.getValue().isAvailable())
        .map(Entry::getKey).collect(Collectors.toSet());
  }

  public String getClientUrl(String clientName) {
    return clientsList.get(clientName).getAddress();
  }
}
