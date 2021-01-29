package ru.sbt.pet.injector.injector;

import ru.sbt.pet.injector.entity.InjectionEntity;
import ru.sbt.pet.injector.exception.MultipleEntityException;
import ru.sbt.pet.injector.exception.NoEntityFoundException;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class Injector{
    private static List<InjectionEntity> container = new ArrayList<>();

    public static void clearContainer() {
        Injector.container = new ArrayList<>();
    }

    public static void add(Class<?> type, Scope scope) throws Exception {
        InjectionEntity entity;
        if (scope == Scope.SINGLETON) {
            entity = new InjectionEntity(type, scope, type.getConstructor().newInstance());
            container.add(entity);
        }
        else {
            try {
                entity = new InjectionEntity(type, scope, type.getConstructor());
                container.add(entity);
            } catch (NoSuchMethodException ex) {
                throw ex;
            }
        }
    }

    public static void add(Class<?> type, Scope scope, Object[] args) throws Exception {
        InjectionEntity entity;
        if (scope == Scope.SINGLETON) {
            Class<?>[] classes = new Class<?>[args.length];
            int i = 0;
            for (Object arg : args) {
                classes[i] = arg.getClass();
                i++;
            }
            Constructor<?> constructor = type.getConstructor(classes);
            Object object = constructor.newInstance(args);
            entity = new InjectionEntity(type, scope, object);
            container.add(entity);
        }
        else {
            try {
                Class<?>[] classes = new Class<?>[args.length];
                int i = 0;
                for (Object arg : args) {
                    classes[i] = arg.getClass();
                    i++;
                }
                Constructor<?> constructor = type.getConstructor(classes);
                entity = new InjectionEntity(type, scope, constructor);
                entity.setArgs(args);
                container.add(entity);
            } catch (NoSuchMethodException ex) {
                throw ex;
            }
        }
    }

    public static void add(Class<?> type, Scope scope, String tag) throws Exception {
        InjectionEntity entity;
        if (scope == Scope.SINGLETON) {
            entity = new InjectionEntity(type, scope, type.getConstructor().newInstance(), tag);
            container.add(entity);
        }
        else {
            try {
                entity = new InjectionEntity(type, scope, type.getConstructor(), tag);
                container.add(entity);
            } catch (NoSuchMethodException ex) {
                throw ex;
            }
        }
    }

    public static void add(Class<?> type, Scope scope, Object[] args, String tag) throws Exception {
        InjectionEntity entity;
        if (scope == Scope.SINGLETON) {
            Class<?>[] classes = new Class<?>[args.length];
            int i = 0;
            for (Object arg : args) {
                classes[i] = arg.getClass();
                i++;
            }
            Constructor<?> constructor = type.getConstructor(classes);
            Object object = constructor.newInstance(args);
            entity = new InjectionEntity(type, scope, object, tag);
            container.add(entity);
        }
        else {
            try {
                Class<?>[] classes = new Class<?>[args.length];
                int i = 0;
                for (Object arg : args) {
                    classes[i] = arg.getClass();
                    i++;
                }
                Constructor<?> constructor = type.getConstructor(classes);
                entity = new InjectionEntity(type, scope, constructor, tag);
                entity.setArgs(args);
                container.add(entity);
            } catch (NoSuchMethodException ex) {
                throw ex;
            }
        }
    }

    public static Object get(Class<?> type) throws Exception{
        Object object = null;
        for (InjectionEntity entity : container) {
            if (type.isAssignableFrom(entity.getClazz())) {
                if (object != null) {
                    throw new MultipleEntityException();
                }
                if (entity.getScope().equals(Scope.SINGLETON)) {
                    object = entity.getObject();
                } else {
                    if (entity.getArgs() == null){
                        object = ((Constructor) entity.getObject()).newInstance();
                    }
                    else {
                        object = ((Constructor) entity.getObject()).newInstance(entity.getArgs());
                    }
                }
            }
        }
        if (object == null) {
            throw new NoEntityFoundException();
        }
        return object;
    }

    public static Object get(Class<?> type, String tag)  throws Exception{
        Object object = null;
        for (InjectionEntity entity : container) {
            if (type.isAssignableFrom(entity.getClazz())) {
                if (entity.getTag().equals(tag)) {
                    if (object != null) {
                        throw new MultipleEntityException("У сущностей одного типа одинаковый тег!");
                    }

                    if (entity.getScope().equals(Scope.SINGLETON)) {
                        object = entity.getObject();
                    } else {
                        if (entity.getArgs() == null){
                            object = ((Constructor) entity.getObject()).newInstance();
                        }
                        else {
                            object = ((Constructor) entity.getObject()).newInstance(entity.getArgs());
                        }
                    }
                }
            }
        }
        return object;
    }
}
