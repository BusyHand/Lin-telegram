package ru.ugrasu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.ugrasu.entity.BignessCase;
import ru.ugrasu.repository.BignessCaseRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class BignessCaseService {

    private final BignessCaseRepository bignessCaseRepository;

    public List<BignessCase> findSuggestionsForBignessCases(String query) {
        List<BignessCase> bignessCases = bignessCaseRepository.findByNameContainingIgnoreCase(query);
        log.info("Bigness Case suggestions for: {} -> {}", query, bignessCases);
        return bignessCases;
    }

    public Page<BignessCase> getList(Pageable pageable) {
        return bignessCaseRepository.findAll(pageable);
    }

    public BignessCase getOne(Long id) {
        return bignessCaseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
    }

    public List<BignessCase> getMany(List<Long> ids) {
        return bignessCaseRepository.findAllById(ids);
    }

    public BignessCase create(BignessCase bignessCase) {
        return bignessCaseRepository.save(bignessCase);
    }

    public void delete(Long id) {
        BignessCase bignessCase = bignessCaseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        bignessCaseRepository.delete(bignessCase);
    }

    public void deleteMany(List<Long> ids) {
        bignessCaseRepository.deleteAllById(ids);
    }
}
