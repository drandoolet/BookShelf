package bookshelf.boot.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("bookshelf.jpa.repos")
public class CustomAnnotationConfiguration {
}
