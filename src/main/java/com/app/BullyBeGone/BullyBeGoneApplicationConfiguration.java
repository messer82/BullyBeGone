package com.app.BullyBeGone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableScheduling
public class BullyBeGoneApplicationConfiguration {

    public static final Logger logger = LoggerFactory.getLogger(BullyBeGoneApplication.class);

    private static final int SCHEDULER_THREADS = 5;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Origin","Access-Control-Allow-Origin", "Content-Type", "Accept",
                "Authorization", "Origin, Accept", "X-Requested-With","Access-Control-Request-Method",
                "Access-Control-Allow-Headers","Access-Control-Request-Headers","Cache-Control"));
        configuration.setExposedHeaders(Arrays.asList("Origin","Content-Type", "Accept","Authorization","Access-Control-Allow-Origin",
                "Access-Control-Allow-Headers","Access-Control-Allow-Credentials"));
        configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "POST", "PATCH", "PUT"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public Docket api() {
        logger.debug("ajunge aici");
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.test.BullyBeGoneApplication.BullyBeGoneApplication"))
                .build();
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        logger.debug("ajunge aici 1");
        ThreadPoolTaskScheduler threadPoolTaskScheduler
                = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(SCHEDULER_THREADS);
        return threadPoolTaskScheduler;
    }

    @Bean
    public DataSource postgresqlDataSource() {
        logger.debug("ajunge aici 2");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUsername("postgres");
        dataSource.setPassword("password");

        return dataSource;
    }
}
