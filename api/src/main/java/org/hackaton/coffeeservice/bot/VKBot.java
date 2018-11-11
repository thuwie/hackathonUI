package org.hackaton.coffeeservice.bot;

import com.petersamokhin.bots.sdk.clients.Group;
import com.petersamokhin.bots.sdk.objects.Message;
import org.hackaton.coffeeservice.controller.PredictResponse;
import org.hackaton.coffeeservice.service.CoffeeService;
import org.springframework.stereotype.Service;

// disable service since w/o access_token it doesn't work :)
//@Service
public class VKBot {

    private CoffeeService coffeeService;

    public VKBot(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
        Group group = new Group(17692504, "my_access_token");

        group.onSimpleTextMessage(message -> {
            String responseMessage = "";
            if ("help".equalsIgnoreCase(message.getText())) {
                responseMessage = "Hi! I am CoffeeBot, send \"?\" to check coffee pot status and \"notify\" to notify when " +
                        "coffeepot is ready!";
            }
            if ("?".equalsIgnoreCase(message.getText())) {
                PredictResponse pr = this.coffeeService.getHistoryFromTimestamp(0, System.currentTimeMillis());
                responseMessage = "Hi! The current status of coffeepot is: " +
                        pr.getMessage() + "!";
            }
            if ("notify".equalsIgnoreCase(message.getText())) {
                this.coffeeService.notifyUser(0, responseCallbackMessage -> {
                    new Message()
                            .from(group)
                            .to(message.authorId())
                            .text(responseCallbackMessage)
                            .send();
                    return null;
                });
                responseMessage = "Great, I will notify you when the coffee pot is ready!";
            }
            if (!responseMessage.isEmpty()) {
                // Send message
                new Message()
                        .from(group)
                        .to(message.authorId())
                        .text(responseMessage)
                        .send();
            }
        });
    }
}