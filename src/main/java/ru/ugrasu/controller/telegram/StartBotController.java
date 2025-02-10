package ru.ugrasu.controller.telegram;

import com.github.kshashov.telegram.api.bind.annotation.BotController;
import com.github.kshashov.telegram.api.bind.annotation.request.MessageRequest;
import com.pengrad.telegrambot.model.User;
import lombok.RequiredArgsConstructor;
import ru.ugrasu.config.TelegramMvcControllerProcessor;

@BotController
@RequiredArgsConstructor
public class StartBotController extends TelegramMvcControllerProcessor {

    @MessageRequest("/start")
    public String start(User user) {
        return "Hello World!";
    }
}