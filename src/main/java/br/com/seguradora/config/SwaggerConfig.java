package br.com.seguradora.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(metaInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.seguradora"))
                .paths(regex("/api/v1.*"))
                .build();
    }

    private ApiInfo metaInfo() {
        return new ApiInfo(
                "Seguradora API REST",
                "API REST para seguradora.",
                "1.0",
                "Terms of Service",
                new Contact("Lucas Knabben", "https://www.linkedin.com/in/lucas-knabben/",
                        "l.knabben@hotmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licesen.html", new ArrayList<VendorExtension>()
        );
    }
}
