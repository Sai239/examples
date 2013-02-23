package com.zagyvaib.example.spring.data.jpa.layering.infrastructure;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Shortcut and more descriptive "alias" for {@code @Transactional(propagation = Propagation.MANDATORY)}.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Transactional(propagation = Propagation.MANDATORY)
public @interface RequiresExistingTransaction {
}
