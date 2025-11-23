package com.dyma.tennis.service;

public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException(String lastName) {
        super(String.format("Player with last name %s could not be found.", lastName));
    }


}
