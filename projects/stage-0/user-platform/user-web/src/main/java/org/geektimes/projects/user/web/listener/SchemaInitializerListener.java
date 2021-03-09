package org.geektimes.projects.user.web.listener;

import org.geektimes.projects.user.sql.DBConnectionManager;
import org.geektimes.web.mvc.context.ComponentContext;
import org.geektimes.web.mvc.util.IOUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SchemaInitializerListener implements ServletContextListener {

    private Logger logger = Logger.getLogger(SchemaInitializerListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String[] sqlArray = loadSql();
        DBConnectionManager connectionManager = ComponentContext.getInstance().getComponent("bean/DBConnectionManager");

        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {

            for (String sql : sqlArray) {
                try {
                    statement.execute(sql);
                } catch (SQLException exception) {
                    ignoredIfDropSql(sql, exception);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "执行数据库初始化失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 如果是DROP语句执行失败，则忽略
     * @param sql
     * @param exception
     */
    private void ignoredIfDropSql(String sql, SQLException exception) {
        if (sql.contains("DROP")) {
            logger.warning("执行sql脚本失败, sql: " + sql + ", error: " + exception.getMessage());
        } else {
            throw new RuntimeException(exception);
        }
    }

    /**
     * 加载数据库脚本
     * @return
     */
    private String[] loadSql() {
        String sqls = IOUtils.getResourceAsString(this.getClass(), "/META-INF/db/DDL/users_table_ddl.sql", "执行数据库初始化失败");
        logger.info("加载数据库脚本: " + sqls);
        return sqls.split(";");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
