package ru.sbt.pet.injector.exception;

public class NoTagException extends Exception {
    public NoTagException() {
        super("У сущности нет тега!");
    }

    public NoTagException(String text) {
        super(text);
    }
}
