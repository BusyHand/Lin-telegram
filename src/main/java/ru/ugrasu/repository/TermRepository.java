package ru.ugrasu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ugrasu.entity.Term;
import ru.ugrasu.entity.Tool;

import java.util.List;

public interface TermRepository extends JpaRepository<Term, Long>, JpaSpecificationExecutor<Term> {
    @Query("SELECT t FROM Term t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Term> findByNameContainingIgnoreCase(@Param("query") String query);

}