package ru.sbt.pet.injector.injector;

public interface Injector {

    void add(Class<?> type, Scope scope) throws Exception;

    void add(Class<?> type, Scope scope, String tag) throws Exception;

    Object get(Class<?> type)  throws Exception;

    Object get(Class<?> type, String tag)  throws Exception;


}
