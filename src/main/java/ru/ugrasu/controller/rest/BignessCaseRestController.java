package ru.ugrasu.controller.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ugrasu.dto.BignessCaseDto;
import ru.ugrasu.entity.BignessCase;
import ru.ugrasu.mapper.BignessCaseMapper;
import ru.ugrasu.service.BignessCaseService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/bignessCases")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:63342")
public class BignessCaseRestController {

    private final BignessCaseService bignessCaseService;
    private final BignessCaseMapper bignessCaseMapper;
    private final ObjectMapper objectMapper;

    @GetMapping("/suggestions")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getSuggestions(@RequestParam String query) {
        List<BignessCase> bignessCases = bignessCaseService.findSuggestionsForBignessCases(query);
        return bignessCases.stream().map(BignessCase::getName).toList(); // Assuming BignessCase has a getTitle method
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<BignessCaseDto> getList(Pageable pageable) {
        Page<BignessCase> bignessCases = bignessCaseService.getList(pageable);
        return bignessCases.map(bignessCaseMapper::toDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BignessCaseDto getOne(@PathVariable Long id) {
        BignessCase bignessCase = bignessCaseService.getOne(id);
        return bignessCaseMapper.toDto(bignessCase);
    }

    @GetMapping("/by-ids")
    @ResponseStatus(HttpStatus.OK)
    public List<BignessCaseDto> getMany(@RequestParam List<Long> ids) {
        List<BignessCase> bignessCases = bignessCaseService.getMany(ids);
        return bignessCases.stream().map(bignessCaseMapper::toDto).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BignessCaseDto create(@RequestBody BignessCaseDto dto) {
        BignessCase bignessCase = bignessCaseMapper.toEntity(dto);
        BignessCase resultBignessCase = bignessCaseService.create(bignessCase);
        return bignessCaseMapper.toDto(resultBignessCase);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BignessCaseDto patch(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
        BignessCase bignessCase = bignessCaseService.getOne(id);
        BignessCaseDto bignessCaseDto = bignessCaseMapper.toDto(bignessCase);

        objectMapper.readerForUpdating(bignessCaseDto).readValue(patchNode);
        bignessCaseMapper.updateWithNull(bignessCaseDto, bignessCase);
        BignessCase updatedBignessCase = bignessCaseService.create(bignessCase);

        return bignessCaseMapper.toDto(updatedBignessCase);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bignessCaseService.delete(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMany(@RequestParam List<Long> ids) {
        bignessCaseService.deleteMany(ids);
    }
}
