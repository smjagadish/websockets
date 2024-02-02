package org.websocketConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.websocketHandlers.Handler;

@EnableWebSocket
@Configuration
public class Configurator implements WebSocketConfigurer {
    private final Handler hlr;

    Configurator(Handler hlr) {
        this.hlr = hlr;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(hlr, "/wsdata")
                .setAllowedOrigins("*");
    }
}
