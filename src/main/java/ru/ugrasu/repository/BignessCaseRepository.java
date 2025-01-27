package ru.ugrasu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ugrasu.entity.BignessCase;
import ru.ugrasu.entity.Term;

import java.util.List;

public interface BignessCaseRepository extends JpaRepository<BignessCase, Long>, JpaSpecificationExecutor<BignessCase> {

    @Query("SELECT t FROM BignessCase t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<BignessCase> findByNameContainingIgnoreCase(@Param("query") String query);
}