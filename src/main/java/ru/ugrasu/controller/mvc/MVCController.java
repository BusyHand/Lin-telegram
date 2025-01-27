package ru.ugrasu.controller.mvc;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ugrasu.mapper.BignessCaseMapper;
import ru.ugrasu.mapper.TermMapper;
import ru.ugrasu.mapper.ToolMapper;
import ru.ugrasu.service.BignessCaseService;
import ru.ugrasu.service.TermService;
import ru.ugrasu.service.ToolService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Controller
@CrossOrigin(origins = "http://localhost:63342")
@RequiredArgsConstructor
@RequestMapping("/")
public class MVCController {

    private final BignessCaseService bignessCaseService;
    private final TermService termService;
    private final ToolService toolService;
    private final TermMapper termMapper;
    private final ToolMapper toolMapper;
    private final BignessCaseMapper bignessCaseMapper;

    @GetMapping("/search")
    public String search(@RequestParam String query, @RequestParam String type, Model model) {
        switch (type) {
            case "terms":
                model.addAttribute("results", termMapper.toDtoList(termService.findSuggestionsForTerms(query))); // Replace with actual results fetching logic
                break;
            case "bignessCases":
                model.addAttribute("results", bignessCaseMapper.toDtoList(bignessCaseService.findSuggestionsForBignessCases(query))); // Fetch results for case
                break;
            case "tools":
                model.addAttribute("results", toolMapper.toDtoList(toolService.findSuggestionsForTools(query))); // Fetch results for tools
                break;
        }
        model.addAttribute("query", query);
        return "searchResults";
    }

    @GetMapping("/searcher")
    public String search() {
        return "search";
    }

    @GetMapping
    public String searchFirst() {
        return "search";
    }

    @GetMapping("/start")
    public String start(HttpServletRequest request) {
        System.out.println("Start");
        return metric(request);
    }

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
