package com.BenjaminPark.dto;

public class UpdateUserDTO {
    final String username;
    final String password;

    /**
     * Creates user update object.
     * @param username Name of this user.
     * @param password Password of this user.
     */
    public UpdateUserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns username of this user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns password of this user.
     */
    public String getPassword() {
        return password;
    }
}
