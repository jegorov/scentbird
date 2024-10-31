package com.scentbird.tictactoe_app.engine.processor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

//This class is required to start an app on random port. Just for the local dev
//Of course there are other simpliear waysto run app with diff ports, but it was just easier to run. One-click start :)
@Profile("dev")
public class PortSelectionEnvironmentPostProcessor implements EnvironmentPostProcessor {

  private static final Logger logger = LoggerFactory.getLogger(
      PortSelectionEnvironmentPostProcessor.class);
  private static final String PORT_PROPERTY = "server.port";
  private static final int[] PORTS = {8080, 8081, 8082, 8083};

  @Override
  public void postProcessEnvironment(ConfigurableEnvironment environment,
      SpringApplication application) {
    int selectedPort = -1;

    for (int port : PORTS) {
      if (isPortAvailable(port)) {
        selectedPort = port;
        break;
      }
    }

    if (selectedPort == -1) {
      logger.error("No available ports found in the range {}.", (Object) PORTS);
      throw new IllegalStateException("No available ports found in the specified range.");
    }

    Map<String, Object> props = new HashMap<>();
    props.put(PORT_PROPERTY, selectedPort);
    MutablePropertySources sources = environment.getPropertySources();
    sources.addFirst(new MapPropertySource("portSelection", props));

    logger.info("Application will start on port {}", selectedPort);
  }

  private boolean isPortAvailable(int port) {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      serverSocket.setReuseAddress(true);
      return true;
    } catch (IOException e) {
      return false;
    }
  }
}