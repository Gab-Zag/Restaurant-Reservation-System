package com.gab.rrs.dtos.register;

public record RegisterUserDTO(String name, String email,
                              String password, String role) {
}
