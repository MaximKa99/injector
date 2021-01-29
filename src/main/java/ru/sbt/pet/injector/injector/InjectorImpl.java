package ru.sbt.pet.injector.injector;

import ru.sbt.pet.injector.exception.MultipleEntityException;
import ru.sbt.pet.injector.exception.NoEntityFoundException;
import ru.sbt.pet.injector.model.InjectionEntity;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class InjectorImpl implements Injector {
    private List<InjectionEntity> container;

    public InjectorImpl() {
        this.container = new ArrayList<>();
    }

    public void add(Class<?> type, Scope scope) throws Exception {
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

    public void add(Class<?> type, Scope scope, String tag) throws Exception {
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

    public Object get(Class<?> type) throws Exception{
        Object object = null;
        for (InjectionEntity entity : container) {
            if (type.isAssignableFrom(entity.getClazz())) {
                if (object != null) {
                    throw new MultipleEntityException();
                }
                if (entity.getScope().equals(Scope.SINGLETON)) {
                    object = entity.getObject();
                } else {
                    object = ((Constructor) entity.getObject()).newInstance();
                }
            }
        }
        if (object == null) {
            throw new NoEntityFoundException();
        }
        return object;
    }

    public Object get(Class<?> type, String tag)  throws Exception{
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
                        object = ((Constructor) entity.getObject()).newInstance();
                    }
                }
            }
        }
        return object;
    }
}