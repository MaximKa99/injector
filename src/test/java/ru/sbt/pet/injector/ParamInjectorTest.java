package ru.sbt.pet.injector;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.sbt.pet.injector.entity.A;
import ru.sbt.pet.injector.injector.Injector;
import ru.sbt.pet.injector.injector.Scope;

public class ParamInjectorTest {
    @Test
    public void test_params_addA_getA_success() {
        Assertions.assertDoesNotThrow(() -> {
            Injector.add(A.class, Scope.SINGLETON, new Object[]{1, 2});
        });
    }
}
