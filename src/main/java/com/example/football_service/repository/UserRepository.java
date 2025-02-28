package com.example.football_service.repository;

import com.example.football_service.domain.entities.AppUser;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);

    boolean existsByEmail(@NotNull String email);
}
