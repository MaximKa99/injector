package ru.sbt.pet.injector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sbt.pet.injector.entity.A;
import ru.sbt.pet.injector.entity.B;
import ru.sbt.pet.injector.exception.MultipleEntityException;
import ru.sbt.pet.injector.exception.NoEntityFoundException;
import ru.sbt.pet.injector.injector.Injector;
import ru.sbt.pet.injector.injector.Scope;

import java.util.concurrent.atomic.AtomicReference;

public class InjectionTest {

    @Test
    public void test_addB_takeB_success() {
        AtomicReference<B> bRef = new AtomicReference<>();
        Assertions.assertDoesNotThrow(() -> {
            Injector.add(B.class, Scope.SINGLETON);
        });
        Assertions.assertDoesNotThrow(() -> {
            bRef.set((B)Injector.get(B.class));
        });

        Assertions.assertEquals(bRef.get().b, 10);
        Injector.clearContainer();
    }

    @Test
    public void test_addA_takeB_fail() {
        Assertions.assertDoesNotThrow(() -> {
            Injector.add(A.class, Scope.SINGLETON);
        });
        Assertions.assertThrows(NoEntityFoundException.class, () -> {
            Injector.get(B.class);
        });
        Injector.clearContainer();
    }

    @Test
    public void test_addNone_takeB_fail() {
        Assertions.assertThrows(NoEntityFoundException.class, () -> {
            Injector.get(B.class);
        });
        Injector.clearContainer();
    }

    @Test
    public void test_addNone_takeA_fail() {
        Assertions.assertThrows(NoEntityFoundException.class, () -> {
            Injector.get(A.class);
        });
        Injector.clearContainer();
    }
    @Test
    public void test_addB_addA_takeB_success() {
        AtomicReference<B> bRef = new AtomicReference<>();
        Assertions.assertDoesNotThrow(() -> {
            Injector.add(B.class, Scope.SINGLETON);
        });
        Assertions.assertDoesNotThrow(() -> {
            Injector.add(A.class, Scope.SINGLETON);
        });
        Assertions.assertDoesNotThrow(() -> {
            bRef.set((B)Injector.get(B.class));
        });

        Assertions.assertEquals(bRef.get().b, 10);
        Injector.clearContainer();
    }

    @Test
    public void test_addB_addA_takeA_fail() {
        Assertions.assertDoesNotThrow(() -> {
            Injector.add(B.class, Scope.SINGLETON);
        });
        Assertions.assertDoesNotThrow(() -> {
            Injector.add(A.class, Scope.SINGLETON);
        });
        Assertions.assertThrows(MultipleEntityException.class, () -> {
            Injector.get(A.class);
        });
        Injector.clearContainer();
    }

}
