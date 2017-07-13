package org.xsnake.cloud.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableConfigServer
@Configuration
@EnableEurekaClient
public class Application {
	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(true).profiles("native").run(args);
	}
}
