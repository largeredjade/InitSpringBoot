package com.likelion.lionlib.exception;

public class ReserveExistsException extends RuntimeException{
    public ReserveExistsException() {
        super("Reserve already exists");
    }
}
