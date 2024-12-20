package group.societe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Tous les endpoints sous /api
                .allowedOrigins("http://localhost:63342/") // Autoriser toutes les origines (ou spécifiez une origine comme "http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Méthodes HTTP autorisées
                .allowedHeaders("*") // Tous les en-têtes autorisés
                .allowCredentials(true); // Autoriser les cookies (si nécessaire)
    }
}