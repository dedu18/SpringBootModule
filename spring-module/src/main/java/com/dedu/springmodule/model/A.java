package com.dedu.springmodule.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class A {
    @Autowired
    private B b;

    public A() {
        System.out.println("A()..");
        System.out.println(b);
    }

    @Override
    public String toString() {
        return "A{" +
                "b=" + b +
                '}';
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }
}
