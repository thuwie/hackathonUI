package org.hackaton.coffeeservice.bot;

import com.petersamokhin.bots.sdk.clients.Group;
import com.petersamokhin.bots.sdk.objects.Message;
import org.hackaton.coffeeservice.controller.PredictResponse;
import org.hackaton.coffeeservice.service.CoffeeService;
import org.springframework.stereotype.Service;

@Service
public class VKBot {

    private CoffeeService coffeeService;
    private Group group;

    public VKBot(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;

        // comment it since Cannot launch it on VK HOST - error with "access_token was given to another ip adderss"

        /*
        this.group = new Group(17692504, "my_access_token");

        group.onSimpleTextMessage(message -> {
            String responseMessage = "";
            if ("help".equalsIgnoreCase(message.getText())) {
                responseMessage = "Hi! I am CoffeeBot, send \"?\" to check coffee pot status and \"notify\" to notify when " +
                        "coffeepot is ready!";
                this.sendMessage(message.authorId(), responseMessage);
            }
            if ("?".equalsIgnoreCase(message.getText())) {
                PredictResponse pr = this.coffeeService.getHistoryFromTimestamp(0, System.currentTimeMillis());
                responseMessage = "Hi! The current status of coffeepot is: " +
                        pr.getMessage() + "!";
                this.sendMessage(message.authorId(), responseMessage);
            }
            if ("notify".equalsIgnoreCase(message.getText())) {
                this.notifyUserOnFullCoffeePot(message.authorId(), "Great, I will notify you when the coffee pot is ready!");
            }
        });
        */
    }

    public void notifyUserOnFullCoffeePot(Integer recipientId, String message) {
        this.coffeeService.notifyUser(0, responseCallbackMessage -> {
            this.sendMessage(recipientId, responseCallbackMessage);
            return null;
        });
        this.sendMessage(recipientId, message);
    }

    private void sendMessage(Integer recipientId, String message) {
        if (this.group != null) {
            new Message()
                    .from(group)
                    .to(recipientId)
                    .text(message)
                    .send();
        }
    }
}