Examples for using Spring Data JPA
----------------------------------

The root of this module is the package `com.zagyvaib.example.spring.data.jpa` under which there are a couple of independent
packages showing different things:

1. `basic` (see [`EmployeeRepositoryTest`] [bsc])
    - using a JPA repository the implementation of which is generated at runtime by Spring
    - basic operations on a simple JPA entity
    - application of the **exception translation** advice on the repository
2. `layering` (see [`ExistingTransactionDemandingEmployeeRepositoryTest`] [lyr])
    - using a special repository that fails with an `IllegalTransactionStateException` if invoked outside of a transaction (using such
    repositories promotes proper application layering, see the javadoc on [`ExistingTransactionDemandingRepositoryImpl`] [erdri])
3. `common`
    - common code and configuration referenced from the above packages in order to avoid code duplication

 [bsc]: src/test/java/com/zagyvaib/example/spring/data/jpa/basic/repository/EmployeeRepositoryTest.java
 [lyr]: src/test/java/com/zagyvaib/example/spring/data/jpa/layering/repository/ExistingTransactionDemandingEmployeeRepositoryTest.java
 [erdri]: src/main/java/com/zagyvaib/example/spring/data/jpa/layering/infrastructure/ExistingTransactionDemandingRepositoryImpl.java
 
