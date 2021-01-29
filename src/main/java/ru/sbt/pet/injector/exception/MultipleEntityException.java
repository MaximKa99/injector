package ru.sbt.pet.injector.exception;

public class MultipleEntityException extends Exception {
    public MultipleEntityException() {
        super("Существует несколько подходящих сущностей!");
    }

    public MultipleEntityException(String text) {
        super(text);
    }
}
