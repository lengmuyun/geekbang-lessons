package org.geektimes.projects.user.web.holder;

public class ThreadLocalHolder {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void set(String message) {
        threadLocal.set(message);
    }

    public static String getAndRemove() {
        try {
            return threadLocal.get();
        } finally {
            threadLocal.remove();
        }
    }

}
