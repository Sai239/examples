package com.zagyvaib.example.spring.data.jpa.layering.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Factory bean creating a factory that returns {@link ExistingTransactionDemandingRepositoryImpl} instances.
 * <p>
 * Refer to this class in the Spring xml context configuration from the {@code <jpa:repositories factory-class>}
 * attribute, in order to instruct the infrastructure to provide {@link ExistingTransactionDemandingRepositoryImpl}
 * as the implementation of any interfaces that extend the {@link org.springframework.data.repository.Repository
 * Repository} interface, replacing the standard
 * {@link org.springframework.data.jpa.repository.support.SimpleJpaRepository SimpleJpaRepository} implementation.
 * <p>
 * Implemented according to section
 * <a href="http://static.springsource.org/spring-data/data-jpa/docs/1.3.0.RELEASE/reference/html/repositories.html#repositories.custom-behaviour-for-all-repositories">
 * "Adding custom behaviour to all repositories"</a> in the Spring Data JPA reference documentation.
 */
public class ExistingTransactionDemandingRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>
        extends JpaRepositoryFactoryBean<R, T, I> {

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new ExistingTransactionDemandingRepositoryFactory(entityManager);
    }

    private static class ExistingTransactionDemandingRepositoryFactory<T, I extends Serializable>
            extends JpaRepositoryFactory {

        private EntityManager entityManager;

        public ExistingTransactionDemandingRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
            this.entityManager = entityManager;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected Object getTargetRepository(RepositoryMetadata metadata) {
            return new ExistingTransactionDemandingRepositoryImpl<T, I>(
                    (Class<T>) metadata.getDomainType(), entityManager);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return ExistingTransactionDemandingRepository.class;
        }
    }
}