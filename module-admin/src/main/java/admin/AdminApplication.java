package admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@SpringBootApplication(scanBasePackages = {"admin"})
@ComponentScan(
        basePackages = {"web"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {
                Controller.class,
                Configuration.class,
                EnableWebSecurity.class})})
@EntityScan("web")
@EnableJpaRepositories("web")
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
