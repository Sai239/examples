Examples for using Spring Data JPA
----------------------------------

The root package of this module is com.zagyvaib.example.spring.data.jpa, under which there are a couple of distinct
classes showcasing different things:

1. [basic](com.zagyvaib.example.spring.data.jpa.basic)
    - using a JPA repository generated on the fly by Spring
    - basic operations on a simple JPA entity
    - exception translation advice applied on the repository

2. [layering](com.zagyvaib.example.spring.data.jpa.layering)
    - using a special repository that fails with an exception if invoked outside a transaction (using such
    repositories promotes proper application layering, see [ExistingTransactionDemandingRepositoryImpl]
    (src/main/java/com/zagyvaib/example/spring/data/jpa/layering/infrastructure/ExistingTransactionDemandingRepositoryImpl.java)

3. [common](com.zagyvaib.example.spring.data.jpa.common)
    - common code and configuration referenced from the above packages