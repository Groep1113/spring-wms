package base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("repository")
@ComponentScan(basePackages = {"base", "entity", "controller", "repository", "graphql"})
@EntityScan("entity")
public class Application {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SpringApplication.run(Application.class, args);
    }
}