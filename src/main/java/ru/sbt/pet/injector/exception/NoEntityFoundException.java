package ru.sbt.pet.injector.exception;

public class NoEntityFoundException extends Exception {
    public NoEntityFoundException() {
        super("Сущность не найдена!");
    }

    public NoEntityFoundException(String message) {
        super(message);
    }
}
