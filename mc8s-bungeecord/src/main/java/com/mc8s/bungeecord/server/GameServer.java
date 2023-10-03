package com.mc8s.bungeecord.server;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @author Aventix created at: 21.11.2022
 */
public class GameServer {
  private String type;
  private ServerState state;
  private InetSocketAddress address;

  public GameServer() {}

  public GameServer(String type, InetSocketAddress address) {
    this.type = type;
    this.address = address;
    this.state = ServerState.STARTING;
  }

  public GameServer(String type, ServerState state, InetSocketAddress address) {
    this.type = type;
    this.state = state;
    this.address = address;
  }

  public GameServer setAddress(InetSocketAddress address) {
    this.address = address;
    return this;
  }

  public GameServer setState(ServerState state) {
    this.state = state;
    return this;
  }

  public GameServer setType(String type) {
    this.type = type;
    return this;
  }

  public String getType() {
    return type;
  }

  public InetSocketAddress getAddress() {
    return address;
  }

  public ServerState getState() {
    return state;
  }

  public enum ServerState {
    STARTING,
    LOBBY,
    INGAME,
    ENDING
  }
}
