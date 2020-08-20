package com.example.springhibernate;

import com.example.springhibernate.entity.Foo;
import com.example.springhibernate.util.ValidationTools;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@SpringBootApplication
public class SpringHibernateApplication {

    public static void main(String[] args) {
//        SpringApplication.run(SpringHibernateApplication.class, args);
        Foo foo = new Foo();
        foo.setName("_HelloWorld");
        ValidationTools.validate(foo);
    }

}
