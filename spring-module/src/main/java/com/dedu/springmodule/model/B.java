package com.dedu.springmodule.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class B {

    public B() {
        System.out.println("B()..");
    }
}
