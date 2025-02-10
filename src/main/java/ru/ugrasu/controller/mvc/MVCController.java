package ru.ugrasu.controller.mvc;

import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * Нужен для подключения потом авторизации
 */
@Deprecated
public class MVCController {

    @GetMapping("/check")
    public String as(HttpServletRequest request) {
        System.out.println("Check");
        return metric(request);
    }

    @NotNull
    private String metric(HttpServletRequest request) {
        System.out.println("Request Method: " + request.getMethod());
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Headers:");

        request.getHeaderNames().asIterator().forEachRemaining(headerName -> {
            System.out.println(headerName + ": " + request.getHeader(headerName));
        });

        System.out.println("Parameters: ");
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((key, value) -> System.out.println(key + ": " + Arrays.toString(value)));

        String fragment = request.getParameter("fragment");
        System.out.println("Fragment: " + fragment);

        System.out.println("Body:");
        StringBuilder body = new StringBuilder();

        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(body);
        return "index";
    }

}
