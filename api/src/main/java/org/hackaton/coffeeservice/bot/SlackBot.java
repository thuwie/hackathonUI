package org.hackaton.coffeeservice.bot;

import me.ramswaroop.jbot.core.common.Controller;
import me.ramswaroop.jbot.core.common.EventType;
import me.ramswaroop.jbot.core.common.JBot;
import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.models.Event;
import org.hackaton.coffeeservice.controller.PredictResponse;
import org.hackaton.coffeeservice.service.CoffeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.socket.WebSocketSession;

/**
 * A simple Slack Bot. You can create multiple bots by just
 * extending {@link Bot} class like this one. Though it is
 * recommended to create only bot per jbot instance.
 *
 * @author ramswaroop
 * @version 1.0.0, 05/06/2016
 */
@JBot
@Profile("slack")
public class SlackBot extends Bot {

    private static final Logger logger = LoggerFactory.getLogger(SlackBot.class);

    private CoffeeService coffeeService;

    public SlackBot(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    /**
     * Slack token from application.properties file. You can get your slack token
     * next <a href="https://my.slack.com/services/new/bot">creating a new bot</a>.
     */
    @Value("${slackBotToken}")
    private String slackToken;

    @Override
    public String getSlackToken() {
        return slackToken;
    }

    @Override
    public Bot getSlackBot() {
        return this;
    }

    @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE})
    public void onReceiveDM(WebSocketSession session, Event event) {
        reply(session, event, "Hi, I am " + slackService.getCurrentUser().getName());
    }

    @Controller(pattern = "(\\?)", events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE})
    public void onReceiveDMByPattern(WebSocketSession session, Event event) {
        PredictResponse pr = this.coffeeService.getHistoryFromTimestamp(0, System.currentTimeMillis());
        String message = "Hi! The current status of coffeepot is: " +
                pr.getMessage() + "!";
        reply(session, event, message);
    }

    @Controller(pattern = "(notify)", events = {EventType.DIRECT_MESSAGE})
    public void onReciveDMNotify(WebSocketSession session, Event event) {
        this.coffeeService.notifyUser(0, message -> {
            reply(session, event, message);
            return null;
        });
        String message = "Great, I will notify you when the coffee pot is ready!";
        reply(session, event, message);
    }
}
