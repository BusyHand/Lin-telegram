package ru.ugrasu.controller.telegram;

import com.github.kshashov.telegram.api.bind.annotation.BotController;
import com.github.kshashov.telegram.api.bind.annotation.request.MessageRequest;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.WebAppInfo;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import ru.ugrasu.config.TelegramMvcControllerProcessor;

@BotController
@RequiredArgsConstructor
public class StartBotController extends TelegramMvcControllerProcessor {

    @MessageRequest("/start")
    public SendMessage start(User user) {
        return new SendMessage(user.id(), "test")
                .replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Cайт").webAppInfo(
                                new WebAppInfo("https://2ma0twrel0qw.share.zrok.io")))
                        .resizeKeyboard(true));
    }
}