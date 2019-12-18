package com.dedu.datastructmodule;

public class StaticMethodImpl implements IStaticMethod {
    static String hello() {
        return "hello, this is a static class method";
    }

    public static void main(String[] args) {
        String a = IStaticMethod.hello();
        String b = StaticMethodImpl.hello();
        System.out.println(a);
        System.out.println(b);
    }
}
