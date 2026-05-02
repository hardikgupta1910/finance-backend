package com.finance.backend.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
	
	@Configuration
	public class OpenApiConfig {

		@Bean
		public OpenAPI customOpenAPI() {
			return new OpenAPI()
					.info(new io.swagger.v3.oas.models.info.Info()
							.title("Finance Backend API")
							.description(
									"🔐 Authentication Guide:\n\n" +
											"1. Use POST /auth/signin with:\n" +
											"   email: admin@finance.com\n" +
											"   password: admin123\n\n" +
											"2. Copy the JWT token from response.\n\n" +
											"3. Click 'Authorize' and paste:\n" +
											"   Bearer <your_token>\n\n" +
											"4. Now you have full access."
							)
					)
					.addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
					.components(new Components()
							.addSecuritySchemes("bearerAuth",
									new SecurityScheme()
											.name("Authorization")
											.type(SecurityScheme.Type.HTTP)
											.scheme("bearer")
											.bearerFormat("JWT")
							)
					);
		}
	}



