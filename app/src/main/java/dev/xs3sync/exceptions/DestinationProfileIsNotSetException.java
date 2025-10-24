package dev.xs3sync.exceptions;

public class DestinationProfileIsNotSetException extends RuntimeException {
    public DestinationProfileIsNotSetException() {
        super("Destination profile is not set");
    }
}
