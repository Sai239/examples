package com.zagyvaib.example.spring.aop;

public class ClassToBeAdvised implements ExampleInterface {

    @Override
    public void methodToBeAdvised() {
        System.out.println("Hello AOP!");
    }

    @Override
    public int multiplyByTwo(int i) {
        return i * 2;
    }
}
