package org.Dispatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.Data.webData;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.websocketHandlers.Handler;

import java.io.IOException;

@Configuration
public class taskSender {
    private final Handler hlr;

    taskSender(Handler hlr) {
        this.hlr = hlr;
    }

    @Scheduled(fixedRate = 1000)
    public void sendData() throws IOException {
        for (WebSocketSession session : hlr.getSession()) {
            webData wd = new webData();
            wd.setId(1);
            wd.setName("test");
            session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsBytes(wd)));
        }
    }
}
