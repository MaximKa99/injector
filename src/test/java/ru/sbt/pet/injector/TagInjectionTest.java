package ru.sbt.pet.injector;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.sbt.pet.injector.entity.A;
import ru.sbt.pet.injector.entity.B;
import ru.sbt.pet.injector.exception.MultipleEntityException;
import ru.sbt.pet.injector.injector.Injector;
import ru.sbt.pet.injector.injector.Scope;

public class TagInjectionTest {
    
    @Test
    public void test_addATag_addBTag_takeA_success() {
        AtomicReference<A> ARef = new AtomicReference<>();
        Assertions.assertDoesNotThrow(() -> {
            Injector.add(A.class, Scope.SINGLETON, "a");
        });

        Assertions.assertDoesNotThrow(() -> {
            Injector.add(B.class, Scope.SINGLETON, "b");
        });

        Assertions.assertDoesNotThrow(() -> {
            ARef.set((A)Injector.get(A.class, "a"));
        });

        Assertions.assertEquals(9, ARef.get().a);
        Injector.clearContainer();
    }

    @Test
    public void test_addA_addB_takeA_fail() {
        AtomicReference<A> ARef = new AtomicReference<>();
        Assertions.assertDoesNotThrow(() -> {
            Injector.add(A.class, Scope.SINGLETON);
        });

        Assertions.assertDoesNotThrow(() -> {
            Injector.add(B.class, Scope.SINGLETON);
        });

        Assertions.assertThrows(MultipleEntityException.class, () -> {
            ARef.set((A)Injector.get(A.class));
        });
    }
}
