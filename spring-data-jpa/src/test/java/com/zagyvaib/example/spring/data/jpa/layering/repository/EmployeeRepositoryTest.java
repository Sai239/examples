package com.zagyvaib.example.spring.data.jpa.layering.repository;

import com.zagyvaib.example.spring.data.jpa.common.dbunit.AbstractDataSetLoadingTest;
import com.zagyvaib.example.spring.data.jpa.common.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.IllegalTransactionStateException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = "classpath:/com/zagyvaib/example/spring/data/jpa/layering/spring/test-application-context.xml")
public class EmployeeRepositoryTest extends AbstractDataSetLoadingTest {

    @Autowired
    private EmployeeRepository _employeeRepository;

    @Override
    protected String getPathToDataSet() {
        return "com/zagyvaib/example/spring/data/jpa/common/dataset/employee.xml";
    }

    @Test(expected = IllegalTransactionStateException.class)
    public void customReopsitoryOperationsShouldFailWithoutATransactionOpenedPreviously() {
        _employeeRepository.findByName("foo");
    }

    @Test(expected = IllegalTransactionStateException.class)
    public void inheritedReopsitoryOperationsShouldFailWithoutATransactionOpenedPreviously() {
        _employeeRepository.save(new Employee());
    }
}
