package com.example.football_service.repository;

import com.example.football_service.domain.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findByCompetitorId(String id);

    @Query(value = "SELECT * FROM team WHERE LOWER(name) LIKE LOWER(CONCAT('%', :teamName, '%'))", nativeQuery = true)
    List<Team> findByNameIgnoreCaseContaining(String teamName);
}
