package com.dedu.springmodule;

import com.dedu.springmodule.model.A;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.dedu.springmodule")
public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
//        这里直接设置不允许循环依赖是失效的
//        context.setAllowCircularReferences(false);
        A a = context.getBean(A.class);
        System.out.println(a);
    }
}
