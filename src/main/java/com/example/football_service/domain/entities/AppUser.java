package com.example.football_service.domain.entities;

import com.example.football_service.domain.common.request.CreateUser;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;


@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    @NotNull
    String firstName;

    @NotNull
    String lastName;

    @NotNull
    String password;

    @NotNull
    String email;

    public static AppUser from(CreateUser createUser, String password) {
        AppUser user = new AppUser();
        user.setFirstName(createUser.firstName());
        user.setLastName(createUser.lastName());
        user.setPassword(password);
        user.setEmail(createUser.email());
        return user;
    }

}