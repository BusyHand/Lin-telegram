package ru.ugrasu.controller.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ugrasu.dto.ToolDto;
import ru.ugrasu.entity.Tool;
import ru.ugrasu.mapper.ToolMapper;
import ru.ugrasu.service.ToolService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/tools")
@RequiredArgsConstructor
public class ToolRestController {

    private final ToolService toolService;
    private final ToolMapper toolMapper;
    private final ObjectMapper objectMapper;

    @GetMapping("/suggestions")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getSuggestions(@RequestParam String query) {
        List<Tool> tools = toolService.findSuggestionsForTools(query);
        return toolMapper.toDtoList(tools).stream()
                .map(ToolDto::getName)
                .toList();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ToolDto> getList(Pageable pageable) {
        Page<Tool> tools = toolService.getList(pageable);
        return tools.map(toolMapper::toDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ToolDto getOne(@PathVariable Long id) {
        Tool tool = toolService.getOne(id);
        return toolMapper.toDto(tool);
    }

    @GetMapping("/by-ids")
    @ResponseStatus(HttpStatus.OK)
    public List<ToolDto> getMany(@RequestParam List<Long> ids) {
        List<Tool> tools = toolService.getMany(ids);
        return tools.stream()
                .map(toolMapper::toDto)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ToolDto create(@RequestBody ToolDto dto) {
        Tool tool = toolMapper.toEntity(dto);
        Tool resultTool = toolService.create(tool);
        return toolMapper.toDto(resultTool);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ToolDto patch(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
        Tool tool = toolService.getOne(id);
        ToolDto toolDto = toolMapper.toDto(tool);
        objectMapper.readerForUpdating(toolDto).readValue(patchNode);
        toolMapper.updateWithNull(toolDto, tool);

        Tool updatedTool = toolService.create(tool);
        return toolMapper.toDto(updatedTool);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ToolDto delete(@PathVariable Long id) {
        Tool tool = toolService.getOne(id);
        toolService.delete(id);
        return toolMapper.toDto(tool);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMany(@RequestParam List<Long> ids) {
        toolService.deleteMany(ids);
    }
}
