package com.koalacute.micro.koala.utils.util;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ServiceUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 根据SpringBeanId获取服务接口或实现类
     * @param beanId Spring容器存放的类id
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T>T getService(String beanId){
        try {
            return (T) context.getBean(beanId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据class获取服务接口或实现类
     * @param clazz Spring容器存放的类
     * @return
     */
    public static <T> T getService(Class<T> clazz){
        try {
            return context.getBean(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
