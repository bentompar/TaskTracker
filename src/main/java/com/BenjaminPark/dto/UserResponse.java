package com.BenjaminPark.dto;

public class UserResponse {
    final String userId;
    final String username;

    /**
     * Creates new user response object.
     * @param userId Id of this user.
     * @param username Name of this user.
     */
    public UserResponse(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    /**
     * Returns userId of this user.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Returns username of this user.
     */
    public String getUsername() {
        return username;
    }
}
