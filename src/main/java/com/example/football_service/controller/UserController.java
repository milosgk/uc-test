package com.example.football_service.controller;

import com.example.football_service.domain.common.request.CreateUser;
import com.example.football_service.services.UserService;
import com.example.football_service.util.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping(Path.API + Path.USER)
public class UserController {

    private final UserService userService;

    @PostMapping(Path.CREATE)
    public ResponseEntity<Long> create(@RequestBody CreateUser request) {
        return ResponseEntity.ok(userService.create(request));
    }
}
