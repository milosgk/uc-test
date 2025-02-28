package com.example.football_service.repository;

import com.example.football_service.domain.entities.AppUser;
import com.example.football_service.domain.entities.UserFavoriteTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFavoriteTeamRepository extends JpaRepository<UserFavoriteTeam, Long> {
}
