package com.info.country.config.swagger;

import com.info.country.config.secuirty.CurrentUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.MultiValueMap;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.function.Predicate;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket countryApi() {
        return new Docket(SWAGGER_2)
                .ignoredParameterTypes(CurrentUser.class, Predicate.class, MultiValueMap.class)
                .select()
                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.info.country.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(Arrays.asList(apiKey()));
    }

    private Contact contact() {
        Contact contact = new Contact(
                "Country and currency information System",
                "http://info.country.com/",
                "info@country.com"
        );
        return contact;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Country and currency information System Api")
                .description("Country and currency information System Api")
                .version("1.0")
                .license("Country and currency information System Service")
                .licenseUrl("http://info.country.com/")
                .contact(contact())
                .build();
    }

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(
                null,
                null,
                null, // realm Needed for authenticate button to work
                null, // appName Needed for authenticate button to work
                " ",// apiKeyValue
                ApiKeyVehicle.HEADER,
                "Authorization", //apiKeyName
                null);
    }

    private ApiKey apiKey() {
        return new ApiKey("jwtToken", "Authorization", "header");
    }
}
