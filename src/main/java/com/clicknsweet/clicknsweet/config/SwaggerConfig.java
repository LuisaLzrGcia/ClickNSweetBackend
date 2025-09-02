package com.clicknsweet.clicknsweet.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "ClickNSweet API",
                version = "1.0.0",
                description = "API para el e-commerce ClickNSweet: gestión de productos, usuarios, carrito, pedidos y reseñas.",
                contact = @Contact(
                        name = "Soporte ClickNSweet",
                        url = "http://98.82.183.146:8080/contact-us/index.html",
                        email = "clicknsweet@gmail.com"
                )
        ),
        servers = {
                @Server(
                        description = "LOCAL SERVER",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PRODUCTION SERVER",
                        url = "http://98.82.183.146:8080"
                )
        }
)

public class SwaggerConfig {
}
