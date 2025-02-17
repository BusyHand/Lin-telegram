package ru.ugrasu.config;

import com.github.kshashov.telegram.api.TelegramMvcController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public abstract class TelegramMvcControllerProcessor implements TelegramMvcController {

    @Value("${telegram.bot.key}")
    private String token;

    @Override
    public String getToken() {
        return token;
    }
}