package dev.josedegracia.jamboardReplacement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter();
    }
}