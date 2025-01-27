package ru.ugrasu.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.ugrasu.dto.TermDto;
import ru.ugrasu.entity.Term;
import ru.ugrasu.repository.TermRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class TermService {

    private final TermRepository termRepository;

    public List<Term> findSuggestionsForTerms(String query) {
        List<Term> terms = termRepository.findByNameContainingIgnoreCase(query);
        log.info("Term suggestions for: {} -> {}", query, terms);
        return terms;
    }

    public Page<Term> getList(Pageable pageable) {
        return termRepository.findAll(pageable);
    }

    public Term getOne(Long id) {
        return termRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
    }

    public List<Term> getMany(List<Long> ids) {
        return termRepository.findAllById(ids);
    }

    public Term create(Term term) {
        return termRepository.save(term);
    }

    public Term patch(Long id, JsonNode termUpdates) {
        Term term = termRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        return termRepository.save(term);
    }

    public void delete(Long id) {
        Term term = termRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        termRepository.delete(term);
    }

    public void deleteMany(List<Long> ids) {
        termRepository.deleteAllById(ids);
    }
}
