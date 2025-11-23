package com.dyma.tennis.service;

public class PlayerAreadyExiste extends RuntimeException {

    public PlayerAreadyExiste (String lastName) {
        super(String.format("Player %s Aready exist", lastName));
    }

}
