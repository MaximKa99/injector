package ru.sbt.pet.injector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sbt.pet.injector.entity.A;
import ru.sbt.pet.injector.entity.B;
import ru.sbt.pet.injector.exception.MultipleEntityException;
import ru.sbt.pet.injector.exception.NoEntityFoundException;
import ru.sbt.pet.injector.injector.Injector;
import ru.sbt.pet.injector.injector.InjectorImpl;
import ru.sbt.pet.injector.injector.Scope;

import java.util.concurrent.atomic.AtomicReference;

public class InjectionTest {
    private final Injector injector = new InjectorImpl();

    @Test
    public void test_addB_takeB_success() {
        AtomicReference<B> bRef = new AtomicReference<>();
        Assertions.assertDoesNotThrow(() -> {
            injector.add(B.class, Scope.SINGLETON);
        });
        Assertions.assertDoesNotThrow(() -> {
            bRef.set((B)injector.get(B.class));
        });

        Assertions.assertEquals(bRef.get().b, 10);
    }

    @Test
    public void test_addA_takeB_fail() {
        Assertions.assertDoesNotThrow(() -> {
            injector.add(A.class, Scope.SINGLETON);
        });
        Assertions.assertThrows(NoEntityFoundException.class, () -> {
            injector.get(B.class);
        });
    }

    @Test
    public void test_addNone_takeB_fail() {
        Assertions.assertThrows(NoEntityFoundException.class, () -> {
            injector.get(B.class);
        });
    }

    @Test
    public void test_addNone_takeA_fail() {
        Assertions.assertThrows(NoEntityFoundException.class, () -> {
            injector.get(A.class);
        });
    }
    @Test
    public void test_addB_addA_takeB_success() {
        AtomicReference<B> bRef = new AtomicReference<>();
        Assertions.assertDoesNotThrow(() -> {
            injector.add(B.class, Scope.SINGLETON);
        });
        Assertions.assertDoesNotThrow(() -> {
            injector.add(A.class, Scope.SINGLETON);
        });
        Assertions.assertDoesNotThrow(() -> {
            bRef.set((B)injector.get(B.class));
        });

        Assertions.assertEquals(bRef.get().b, 10);
    }

    @Test
    public void test_addB_addA_takeA_fail() {
        Assertions.assertDoesNotThrow(() -> {
            injector.add(B.class, Scope.SINGLETON);
        });
        Assertions.assertDoesNotThrow(() -> {
            injector.add(A.class, Scope.SINGLETON);
        });
        Assertions.assertThrows(MultipleEntityException.class, () -> {
            injector.get(A.class);
        });
    }

}
