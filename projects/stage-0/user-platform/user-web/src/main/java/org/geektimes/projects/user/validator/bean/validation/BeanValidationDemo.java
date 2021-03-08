package org.geektimes.projects.user.validator.bean.validation;

import org.geektimes.projects.user.domain.User;

import javax.validation.*;
import java.util.Set;

public class BeanValidationDemo {

    public static void main(String[] args) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        // cache the factory somewhere
        Validator validator = factory.getValidator();

        User user = new User();
        user.setId(1L);
        user.setPassword("123456");
        user.setPhoneNumber("18767157675");

        // 校验结果
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        violations.forEach(c -> {
            Path propertyPath = c.getPropertyPath();
            String propertyName = null;
            for (Path.Node node : propertyPath) {
                propertyName = node.getName();
                break;
            }
            System.out.println("propertyName: " + propertyName + ", message: " + c.getMessage());
        });
    }
}
