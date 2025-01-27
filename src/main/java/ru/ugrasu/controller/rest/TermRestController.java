package ru.ugrasu.controller.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ugrasu.dto.TermDto;
import ru.ugrasu.entity.Term;
import ru.ugrasu.mapper.TermMapper;
import ru.ugrasu.service.TermService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/terms")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:63342")
public class TermRestController {

    private final TermService termService;
    private final TermMapper termMapper;
    private final ObjectMapper objectMapper;

    @GetMapping("/suggestions")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getSuggestions(@RequestParam String query) {
        List<Term> terms = termService.findSuggestionsForTerms(query);
        return terms.stream().map(Term::getName).toList();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<TermDto> getList(Pageable pageable) {
        Page<Term> terms = termService.getList(pageable);
        return terms.map(termMapper::toDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TermDto getOne(@PathVariable Long id) {
        Term term = termService.getOne(id);
        return termMapper.toDto(term);
    }

    @GetMapping("/by-ids")
    @ResponseStatus(HttpStatus.OK)
    public List<TermDto> getMany(@RequestParam List<Long> ids) {
        List<Term> terms = termService.getMany(ids);
        return terms.stream().map(termMapper::toDto).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TermDto create(@RequestBody TermDto dto) {
        Term term = termMapper.toEntity(dto);
        Term resultTerm = termService.create(term);
        return termMapper.toDto(resultTerm);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TermDto patch(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
        Term term = termService.getOne(id);
        termService.patch(id, patchNode);

        TermDto termDto = termMapper.toDto(term);
        objectMapper.readerForUpdating(termDto).readValue(patchNode);
        termMapper.updateWithNull(termDto, term);
        Term updatedTerm = termService.create(term);

        return termMapper.toDto(updatedTerm);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        termService.delete(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMany(@RequestParam List<Long> ids) {
        termService.deleteMany(ids);
    }
}
