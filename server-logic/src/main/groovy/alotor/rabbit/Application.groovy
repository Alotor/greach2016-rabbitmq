package alotor.rabbit

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ImportResource
import org.springframework.integration.config.EnableIntegration

@SpringBootApplication
@ImportResource(["integration.xml"])
@EnableIntegration
public class Application {

    static void main(args) {
        SpringApplication.run Application, args
    }

}
