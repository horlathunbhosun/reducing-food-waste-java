package tech.olatunbosun.wastemanagement.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Olatunbosun Olulode",
                        email = "tech@olatunbosun.com",
                        url = "olaolulode.xyz"
                ),
                description = "OpenApi documentation for Spring Security",
                title = "OpenApi specification - Rest API for Waste Management System",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "olaolulode.xyz"
                ),
                termsOfService = "Terms of service"
        ),

        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:9090"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://spring-api-data-waste-data-api.azuremicroservices.io/"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
