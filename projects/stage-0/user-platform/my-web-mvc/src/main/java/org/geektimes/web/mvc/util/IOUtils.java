package org.geektimes.web.mvc.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IOUtils {

    private static final Logger logger = Logger.getLogger(IOUtils.class.getName());

    /**
     * 从指定路径加载文件，转换成String
     * @param clazz
     * @param name 文件路径
     * @param message 加载失败时的日志输出信息
     * @return
     */
    public static String getResourceAsString(Class<?> clazz, String name, String message) {
        try (InputStream inputStream = clazz.getResourceAsStream(name);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            return outputStream.toString(StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            logger.log(Level.SEVERE, message);
            throw new RuntimeException(e);
        }
    }

    /**
     * 静默关闭流，不推荐使用
     * @param closeables
     */
    @Deprecated
    public static void closeQuiet(AutoCloseable... closeables) {
        if (closeables == null || closeables.length == 0) {
            return;
        }

        for (AutoCloseable autoCloseable : closeables) {
            if (autoCloseable != null) {
                try {
                    autoCloseable.close();
                } catch (Exception e) {
                    logger.log(Level.SEVERE, e.getMessage());
                }
            }
        }
    }

}
