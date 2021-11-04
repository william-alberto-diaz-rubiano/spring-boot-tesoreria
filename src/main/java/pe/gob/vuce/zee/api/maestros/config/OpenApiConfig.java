package pe.gob.vuce.zee.api.maestros.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("VUCE-ZEE-API-MAESTROS")
                        .description("Sistema Integrado de Gestión de Operaciones de las Zonas Económicas Especiales - ZEE")
                        //.termsOfService("terms")
                        .contact(new Contact().email("info@vuce.gob.pe"))
                        .license(new License().name("Apche 2.0"))
                        .version("1.0.0")
                );
    }
}
