package com.likelion.lionlib.exception;

public class ReservationNotFoundException extends RuntimeException{
    public ReservationNotFoundException(Long Id) {
        super("Reservation Not Found");
    }
}
