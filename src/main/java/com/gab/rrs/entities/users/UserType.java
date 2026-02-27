package com.gab.rrs.entities.users;

public enum UserType {
    CLIENT("client"),
    ADM("admin");

    private String role;

    UserType(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
