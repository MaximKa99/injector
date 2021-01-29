package ru.sbt.pet.injector.entity;

public class A {
    public int a;

    public A() {
        this.a = 9;
    }

    public A(Integer a1, Integer a2) {
        System.out.println(a1 + a2);
    }
}
