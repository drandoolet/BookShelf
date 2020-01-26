package bookshelf.jpa.repos;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class DataAccessAnnotationProcessor implements BeanPostProcessor {
    private ConfigurableListableBeanFactory factory;

    @Autowired
    public DataAccessAnnotationProcessor(ConfigurableListableBeanFactory factory) {
        this.factory = factory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        scanDataAccessAnnotation(bean, beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    protected void scanDataAccessAnnotation(Object bean, String beanName) {
        configureBeanInjection(bean);
    }

    private void configureBeanInjection(Object bean) {
        Class<?> managedBeanClass = bean.getClass();
        ReflectionUtils.FieldCallback fieldCallback = new DataAccessFieldCallback(factory, bean);
        ReflectionUtils.doWithFields(managedBeanClass, fieldCallback);
    }
}
