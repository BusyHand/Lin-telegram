package ru.ugrasu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.ugrasu.entity.Tool;
import ru.ugrasu.repository.ToolRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ToolService {

    private final ToolRepository toolRepository;

    public List<Tool> findSuggestionsForTools(String query) {
        List<Tool> tools = toolRepository.findByNameContainingIgnoreCase(query);
        log.info("Tool suggestions for: {} -> {}", query, tools);
        return tools;
    }

    public Page<Tool> getList(Pageable pageable) {
        return toolRepository.findAll(pageable);
    }

    public Tool getOne(Long id) {
        return toolRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
    }

    public List<Tool> getMany(List<Long> ids) {
        return toolRepository.findAllById(ids);
    }

    public Tool create(Tool tool) {
        return toolRepository.save(tool);
    }

    public Tool patch(Long id, Tool toolUpdates) {
        Tool tool = toolRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        return toolRepository.save(tool);
    }

    public void delete(Long id) {
        Tool tool = toolRepository.findById(id).orElse(null);
        if (tool != null) {
            toolRepository.delete(tool);
        }
    }

    public void deleteMany(List<Long> ids) {
        toolRepository.deleteAllById(ids);
    }
}
