package bookshelf.jpa.repos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

class DataAccessFieldCallback implements ReflectionUtils.FieldCallback {
    private static final Logger logger = LoggerFactory.getLogger(DataAccessFieldCallback.class);
    private static final int AUTOWIRE_MODE = AutowireCapableBeanFactory.AUTOWIRE_BY_NAME;

    private final ConfigurableListableBeanFactory factory;
    private final Object bean;

    DataAccessFieldCallback(ConfigurableListableBeanFactory factory, Object bean) {
        this.factory = factory;
        this.bean = bean;
    }

    @Override
    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
        if (!field.isAnnotationPresent(DataAccess.class)) return;

        ReflectionUtils.makeAccessible(field);
        Type fieldGenericType = field.getGenericType();

        Class<?> generic = field.getType();
        Class<?> classValue = field.getDeclaredAnnotation(DataAccess.class).entity();

        if (genericTypeIsValid(classValue, fieldGenericType)) {
            String beanName = classValue.getSimpleName() + generic.getSimpleName();
            Object beanInstance = getBeanInstance(beanName, generic, classValue);
            field.set(bean, beanInstance);
        } else throw new IllegalArgumentException(Message.ERROR_ENTITY_VALUE_NOT_SAME._message());
    }

    private boolean genericTypeIsValid(Class<?> clazz, Type field) {
        if (field instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) field;
            Type type = parameterizedType.getActualTypeArguments()[0];

            return type.equals(clazz);
        } else {
            logger.warn(Message.WARN_NON_GENERIC_VALUE._message());
            return true;
        }
    }

    private Object getBeanInstance(String beanName, Class<?> genericClass, Class<?> paramClass) {
        Object daoInstance = null;

        if (!factory.containsBean(beanName)) {
            logger.info(Message.INFO_BEAN_CREATING_NEW._message(), beanName);

            Object toRegister = null;
            try {
                Constructor<?> constructor = genericClass.getConstructor(Class.class);
                toRegister = constructor.newInstance(paramClass);
            } catch (Exception e) {
                logger.error(Message.ERROR_CREATE_INSTANCE._message(), genericClass.getTypeName(), e);
                throw new RuntimeException(e);
            }

            daoInstance = factory.initializeBean(toRegister, beanName);
            factory.autowireBeanProperties(daoInstance, AUTOWIRE_MODE, true);
            factory.registerSingleton(beanName, daoInstance);
            logger.info(Message.INFO_BEAN_CREATE_SUCCESS._message(), beanName);
        } else {
            logger.info(Message.INFO_BEAN_EXISTS._message(), beanName);
            daoInstance = factory.getBean(beanName);
        }
        return daoInstance;
    }

    private enum Message {
        ERROR_ENTITY_VALUE_NOT_SAME("@DataAccess(entity) value should have same type with injected generic type."),
        ERROR_CREATE_INSTANCE("Cannot create instance of type '{}' or instance creation is failed because: {}"),
        WARN_NON_GENERIC_VALUE("@DataAccess annotation assigned to raw (non-generic) declaration. " +
                "This will make your code less type-safe."),
        INFO_BEAN_CREATING_NEW("Creating new DataAccess bean named '{}'."),
        INFO_BEAN_CREATE_SUCCESS("Bean named '{}' created successfully."),
        INFO_BEAN_EXISTS("Bean named '{}' already exists used as current bean reference.");
        private final String message;

        public String _message() { return message; }

        Message(String message) {
            this.message = message;
        }
    }
}
