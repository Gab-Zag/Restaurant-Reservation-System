package com.gab.rrs.dtos.register;

import com.gab.rrs.entities.users.UserType;

public record RegisterUserDTO(String name, String email,
                              String password, UserType role) {
}
