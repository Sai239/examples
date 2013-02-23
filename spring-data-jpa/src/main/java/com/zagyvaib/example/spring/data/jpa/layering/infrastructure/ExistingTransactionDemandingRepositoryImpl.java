package com.zagyvaib.example.spring.data.jpa.layering.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * Custom base class for the repository proxies created automatically by the infrastructure if {@link
 * ExistingTransactionDemandingRepositoryFactoryBean} is used.
 * <p>
 * Changes the transactional propagation attribute of the repository operations which defaults to {@link
 * org.springframework.transaction.annotation.Propagation#REQUIRED REQUIRED} with the Spring provided {@link
 * org.springframework.data.jpa.repository.support.SimpleJpaRepository SimpleJpaRepository} implementation.
 * This custom repository implementation uses  {@link org.springframework.transaction.annotation.Propagation#MANDATORY
 * MANDATORY} instead, which helps enforcing proper application layering, because repository methods won't work
 * unless an existing transaction has already been started in layers above. Normally controllers are the transaction
 * owners, so it must be their responsibility to start new transactions. It should be considered an error, if code in
 * lower layers needed to start a transaction on their own.
 * <p>
 * Implemented according to section
 * <a href="http://static.springsource.org/spring-data/data-jpa/docs/1.3.0.RELEASE/reference/html/repositories.html#repositories.custom-behaviour-for-all-repositories">
 * "Adding custom behaviour to all repositories"</a> in the Spring Data JPA reference documentation.
 */
@ReadOnlyAndRequiresExistingTransaction
public class ExistingTransactionDemandingRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements ExistingTransactionDemandingRepository<T, ID> {

    public ExistingTransactionDemandingRepositoryImpl(
            JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public ExistingTransactionDemandingRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
    }

    @Override
    @RequiresExistingTransaction
    public void delete(ID id) { super.delete(id); }

    @Override
    @RequiresExistingTransaction
    public void delete(T entity) { super.delete(entity); }

    @Override
    @RequiresExistingTransaction
    public void delete(Iterable<? extends T> entities) { super.delete(entities); }

    @Override
    @RequiresExistingTransaction
    public void deleteInBatch(Iterable<T> entities) { super.deleteInBatch(entities); }

    @Override
    @RequiresExistingTransaction
    public void deleteAll() { super.deleteAll(); }

    @Override
    @RequiresExistingTransaction
    public void deleteAllInBatch() { super.deleteAllInBatch(); }

    @Override
    public T findOne(ID id) { return super.findOne(id); }

    @Override
    public boolean exists(ID id) { return super.exists(id); }

    @Override
    public List<T> findAll() { return super.findAll(); }

    @Override
    public List<T> findAll(Iterable<ID> ids) { return super.findAll(ids); }

    @Override
    public List<T> findAll(Sort sort) { return super.findAll(sort); }

    @Override
    public Page<T> findAll(Pageable pageable) { return super.findAll(pageable); }

    @Override
    public T findOne(Specification<T> spec) { return super.findOne(spec); }

    @Override
    public List<T> findAll(Specification<T> spec) { return super.findAll(spec); }

    @Override
    public Page<T> findAll(Specification<T> spec, Pageable pageable) { return super.findAll(spec, pageable);
    }

    @Override
    public List<T> findAll(Specification<T> spec, Sort sort) { return super.findAll(spec, sort); }

    @Override
    public long count() { return super.count(); }

    @Override
    public long count(Specification<T> spec) { return super.count(spec); }

    @Override
    @RequiresExistingTransaction
    public <S extends T> S save(S entity) { return super.save(entity); }

    @Override
    @RequiresExistingTransaction
    public <S extends T> List<S> save(Iterable<S> entities) { return super.save(entities); }

    @Override
    @RequiresExistingTransaction
    public T saveAndFlush(T entity) { return super.saveAndFlush(entity); }

    @Override
    @RequiresExistingTransaction
    public void flush() { super.flush(); }
}