package com.zagyvaib.example.spring.aop;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/context.xml")
public class ClassToBeAdvisedTest {

    @Autowired
    ExampleInterface _advised;

    @Test
    public void twoTimeTwoShouldBeFive() {
        _advised.methodToBeAdvised();
        Assert.assertEquals(_advised.multiplyByTwo(2), 5);
    }
}
