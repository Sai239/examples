Example for @AspectJ aspects in Spring AOP.
-------------------------------------------

@AspectJ aspects are POJOs decorated with annotations defined in AspectJ (`org.aspectj.lang.annotation.*`). This module
shows how Spring can make these aspects work on configured Spring beans using JDK dynamic proxies without depending on
AspectJ compiler/weaver. (Note that `aspectjweaver.jar` still needs to be on the classpath for the annotation classes
and for pointcut parsing/matching.)

This is what we need to have aspects on a bean working at runtime:

1. An interface that the bean implements, because JDK proxies can only proxy interfaces.  
   See [ExampleInterface](src/main/java/com/zagyvaib/example/spring/aop/ExampleInterface.java)

2. An implementation of the interface. This will be the type of the bean.
   See [ClassToBeAdvised](src/main/java/com/zagyvaib/example/spring/aop/ClassToBeAdvised.java)

3. An @AspectJ aspect that is also instantiated as a bean in our config.
   See [ExampleAspect](src/main/java/com/zagyvaib/example/spring/aop/ExampleAspect.java)

4. Spring configuration with the above two beans and `<aop:aspectj-autoproxy/>` that enables proxies to be
automatically created based on the aspect beans in the Spring context and the annotations in them.  
   See [context.xml](src/main/resources/spring/context.xml)


There is a Junit test [ClassToBeAdvisedTest](src/test/java/com/zagyvaib/example/spring/aop/ClassToBeAdvisedTest.java) checking that the aspect works.
