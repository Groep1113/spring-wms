package base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import seeder.DatabaseSeeder;

@SpringBootApplication
@EnableJpaRepositories("repository")
@ComponentScan(basePackages = {"base", "entity", "controller", "repository", "graphql", "seeder"})
@EntityScan("entity")
public class Application {
    public static void main(String[] args) {
        if (args.length > 0) {
            for (String arg: args) {
                if ("seed".equals(arg)) {
                    DatabaseSeeder.enableSeeding();
                }
            }
        }
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

}
