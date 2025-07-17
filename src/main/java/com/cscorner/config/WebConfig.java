package com.cscorner.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TokenAuthInterceptor tokenAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Intercepteur désactivé pour rendre toutes les APIs accessibles
        // Le système de tokens reste disponible pour ceux qui veulent l'utiliser
        // Pour activer la protection, décommentez les lignes ci-dessous :

        /*
        registry.addInterceptor(tokenAuthInterceptor)
                .addPathPatterns("/api/**") // Appliquer à tous les endpoints API
                .excludePathPatterns(
                    "/api/auth/login",
                    "/api/auth/register",
                    "/api/auth/validate",
                    "/ws/**",
                    "/error"
                );
        */
    }
}
