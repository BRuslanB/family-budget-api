package kz.bars.family.budget.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Family Budget API")
                        .description("Family Budget Information")
                        .version("1.0.3")
                        .contact(new Contact()
                                .name("BRuslan")
                                .email("kz.bars.prod@gmail.com")
                        )
                );
    }
}
