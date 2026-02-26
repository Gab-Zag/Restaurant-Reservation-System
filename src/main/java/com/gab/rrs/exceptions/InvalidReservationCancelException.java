package com.gab.rrs.exceptions;

public class InvalidReservationCancelException extends RuntimeException {
    public InvalidReservationCancelException(String message) {
        super(message);
    }
}
