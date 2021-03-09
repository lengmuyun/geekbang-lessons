package org.geektimes.web.mvc.annotation;

import org.geektimes.web.mvc.function.ThrowableAction;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 模仿Spring InitDestroyAnnotationBeanPostProcessor
 * 缓存Bean Destory方法
 */
public class DestroyAnnotationProcessor {

    private final List<LifecycleElement> lifecycleElementList = new ArrayList<>();

    public void addLifecycleMetadata(Object component, Method method) {
        lifecycleElementList.add(new LifecycleElement(component, method));
    }

    public void invokeDestroyMethods() {
        for (LifecycleElement lifecycleMetadata : lifecycleElementList) {
            ThrowableAction.execute(lifecycleMetadata::invoke);
        }
    }

    private static class LifecycleElement {

        private Object obj;

        private Method method;

        public LifecycleElement(Object component, Method destroyMethod) {
            this.obj = component;
            this.method = destroyMethod;
        }

        public void invoke() throws Throwable {
            method.invoke(obj);
        }

    }

}
