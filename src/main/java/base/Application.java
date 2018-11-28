package base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"base", "interfaces"})
public class Application {

    public static void main(String[] args) {
        System.out.println("Hello world!");
        SpringApplication.run(Application.class, args);
    }
}