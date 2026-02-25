package com.gab.rrs.controllers;

import com.gab.rrs.dtos.login.LoginUserDTO;
import com.gab.rrs.dtos.register.RegisterUserDTO;
import com.gab.rrs.services.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UsersService usersService;

    public UserController(UsersService usersService){
        this.usersService = usersService;
    }

    @PostMapping("/usuarios/registrar")
    public ResponseEntity<Void> register(@RequestBody RegisterUserDTO dto){
        usersService.register(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/usuarios/login")
    public ResponseEntity<Void> login(@RequestBody LoginUserDTO dto){
        usersService.login(dto);
        return ResponseEntity.ok().build();
    }
}
