package org.geektimes.projects.user.service;

import cn.hutool.crypto.SecureUtil;
import org.apache.commons.collections.CollectionUtils;
import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.sql.LocalTransactional;
import org.geektimes.projects.user.web.holder.ThreadLocalHolder;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserServiceImpl implements UserService {

    private Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Resource(name = "bean/EntityManager")
    private EntityManager entityManager;

    @Resource(name = "bean/Validator")
    private Validator validator;

    @Override
    // 默认需要事务
    @LocalTransactional
    public boolean register(User user) {
        Set<ConstraintViolation<User>> validateInfoSet = validator.validate(user);
        if (!CollectionUtils.isEmpty(validateInfoSet)) {
            String message = getValidateInfo(validateInfoSet);
            ThreadLocalHolder.set("数据校验失败: " + message);
            return false;
        }

        // 密码加密
        user.setPassword(SecureUtil.md5(user.getPassword()));

        EntityTransaction transaction = null;
        try {
            // before process
            transaction = entityManager.getTransaction();
            transaction.begin();

            // 主调用
            entityManager.persist(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            ThreadLocalHolder.set(e.getMessage());

            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }

        // 调用其他方法方法
//        update(user); // 涉及事务
        // register 方法和 update 方法存在于同一线程
        // register 方法属于 Outer 事务（逻辑）
        // update 方法属于 Inner 事务（逻辑）
        // Case 1 : 两个方法均涉及事务（并且传播行为和隔离级别相同）
        // 两者共享一个物理事务，但存在两个逻辑事务
        // 利用 ThreadLocal 管理一个物理事务（Connection）

        // rollback 情况 1 : update 方法（Inner 事务），它无法主动去调用 rollback 方法
        // 设置 rollback only 状态，Inner TX(rollback only)，说明 update 方法可能存在执行异常或者触发了数据库约束
        // 当 Outer TX 接收到 Inner TX 状态，它来执行 rollback
        // A -> B -> C -> D -> E 方法调用链条
        // A (B,C,D,E) 内联这些方法，合成大方法
        // 关于物理事务是哪个方法创建
        // 其他调用链路事务传播行为是一致时，都是逻辑事务

        // Case 2: register 方法是 PROPAGATION_REQUIRED（事务创建者），update 方法 PROPAGATION_REQUIRES_NEW
        // 这种情况 update 方法也是事务创建者
        // update 方法 rollback-only 状态不会影响 Outer TX，Outer TX 和 Inner TX 是两个物理事务

        // Case 3: register 方法是 PROPAGATION_REQUIRED（事务创建者），update 方法 PROPAGATION_NESTED
        // 这种情况 update 方法同样共享了 register 方法物理事务，并且通过 Savepoint 来实现局部提交和回滚

        // after process
        // transaction.commit();
    }

    private String getValidateInfo(Set<ConstraintViolation<User>> validateInfoSet) {
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<User> violation : validateInfoSet) {
            Path path = violation.getPropertyPath();
            Iterator<Path.Node> iterator = path.iterator();
            String propertyName = null;
            if (iterator.hasNext()) {
                Path.Node node = iterator.next();
                propertyName = node.getName();
            }

            sb.append("Property Name: ").append(propertyName)
                    .append(", Property Value: ").append(violation.getInvalidValue())
                    .append(", Error Message: ").append(violation.getMessage())
                    .append("<br/>");
        }
        return sb.toString();
    }

    @Override
    public boolean deregister(User user) {
        return false;
    }

    @Override
    @LocalTransactional
    public boolean update(User user) {
        return false;
    }

    @Override
    public User queryUserById(Long id) {
        return null;
    }

    @Override
    public User queryUserByNameAndPassword(String name, String password) {
        return null;
    }

    @Override
    public Collection<User> getAll() {
        Query nativeQuery = entityManager.createNativeQuery("SELECT id, name, password, email, phoneNumber FROM users", User.class);
        return nativeQuery.getResultList();
    }

    @PreDestroy
    public void destory() {
        System.out.println("===================执行@PreDestory中的方法===================");
    }

}
