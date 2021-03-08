package org.geektimes.web.mvc.processor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 模仿Spring InitDestroyAnnotationBeanPostProcessor
 * 缓存Bean Destory方法
 */
public class DestroyAnnotationProcessor {

    private final List<LifecycleMetadata> lifecycleMetadataList = new ArrayList<>();

    public void addLifecycleMetadata(Object component, Method method) {
        lifecycleMetadataList.add(new LifecycleMetadata(component, method));
    }

    public void invokeDestory() {
        for (LifecycleMetadata lifecycleMetadata : lifecycleMetadataList) {
            Object component = lifecycleMetadata.getComponent();
            Method destroyMethod = lifecycleMetadata.getDestroyMethod();
            try {
                destroyMethod.invoke(component);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static class LifecycleMetadata {

        private Object component;

        private Method destroyMethod;

        public LifecycleMetadata() {
        }

        public LifecycleMetadata(Object component, Method destroyMethod) {
            this.component = component;
            this.destroyMethod = destroyMethod;
        }

        public Object getComponent() {
            return component;
        }

        public Method getDestroyMethod() {
            return destroyMethod;
        }

    }

}
