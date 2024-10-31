package com.scentbird.tictactoe_app.configuration;

import java.net.BindException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration {

  @Bean
  public WebServerFactoryCustomizer<TomcatServletWebServerFactory> webServerFactoryCustomizer() {
    return factory -> {
      List<Integer> ports = Arrays.asList(8080, 8081, 8082, 8083);
      Random random = new Random();
      int randomPort = 0;

      // Retry logic for port binding
      for (int i = 0; i < ports.size(); i++) {
        try {
          randomPort = ports.get(random.nextInt(ports.size())); // Pick a random port
          factory.setPort(randomPort);
          System.out.println("Trying to start the application on port: " + randomPort);
          break;
        } catch (Exception ex) {
          if (ex.getCause() instanceof BindException) {
            System.out.println(
                "Port " + randomPort + " is already in use. Trying a different port...");
          } else {
            throw ex;
          }
        }
      }
    };
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

}
