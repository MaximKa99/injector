package ru.sbt.pet.injector.entity;

import ru.sbt.pet.injector.injector.Scope;

public class InjectionEntity {
    Class<?> clazz;
    Scope scope;
    String tag;
    Object object;

    public InjectionEntity(Class<?> clazz, Scope scope, Object object, String tag) {
        this.clazz = clazz;
        this.scope = scope;
        this.object = object;
        this.tag = tag;
    }

    public InjectionEntity(Class<?> clazz, Scope scope, Object object) {
        this.clazz = clazz;
        this.scope = scope;
        this.object = object;
        this.tag = "";
    }

    public InjectionEntity() {
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
