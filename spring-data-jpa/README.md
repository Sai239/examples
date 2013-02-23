Examples for using Spring Data JPA
----------------------------------

The root of this module is the `com.zagyvaib.example.spring.data.jpa` package under which there are a couple of independent
packages showing different things:

1. [`basic`] [bsc]
    - using a JPA repository generated on the fly by Spring
    - basic operations on a simple JPA entity
    - exception translation advice applied on the repository
2. [`layering`] [lyr]
    - using a special repository that fails with an exception if invoked outside a transaction (using such
    repositories promotes proper application layering, see the javadoc on [`ExistingTransactionDemandingRepositoryImpl`] [erdri]
3. [`common`] [cmn]
    - common code and configuration referenced from the above packages

 [bsc]: com.zagyvaib.example.spring.data.jpa.basic
 [lyr]: com.zagyvaib.example.spring.data.jpa.layering
 [cmn]: com.zagyvaib.example.spring.data.jpa.common
 [erdri]: src/main/java/com/zagyvaib/example/spring/data/jpa/layering/infrastructure/ExistingTransactionDemandingRepositoryImpl.java
