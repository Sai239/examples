package com.zagyvaib.example.spring.data.jpa.basic.repository;

import com.zagyvaib.example.spring.data.jpa.common.dbunit.AbstractDataSetLoadingTest;
import com.zagyvaib.example.spring.data.jpa.common.entity.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ch.lambdaj.Lambda.extract;
import static ch.lambdaj.Lambda.on;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.transaction.TransactionDefinition.PROPAGATION_REQUIRES_NEW;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = "classpath:/com/zagyvaib/example/spring/data/jpa/basic/spring/test-application-context.xml")
@DirtiesContext // don't cache this context, it's not reused (see http://goo.gl/5F4qs on why it's needed)
public class EmployeeRepositoryTest extends AbstractDataSetLoadingTest {

    @Autowired
    private EmployeeRepository _employeeRepository;

    @Autowired
    private PlatformTransactionManager _transactionManager;

    private TransactionTemplate _transactionTemplate;

    @Before
    public void init() {
        _transactionTemplate = new TransactionTemplate(
                _transactionManager, new DefaultTransactionAttribute(PROPAGATION_REQUIRES_NEW));
    }

    @Override
    protected String getPathToDataSet() {
        return "com/zagyvaib/example/spring/data/jpa/common/dataset/employee.xml";
    }

    @Transactional
    @Test(expected = DataAccessException.class)
    public void uniqueConstraintViolation_throwsSpringSpecificException() {
        _employeeRepository.save(new Employee().setEmail("uniqueEmail"));
        _employeeRepository.save(new Employee().setEmail("uniqueEmail"));
    }

    @Test
    public void shouldSaveEmployee() {
        final Employee employeeToAdd = new Employee();
        employeeToAdd.setName("name");
        employeeToAdd.setEmail("email");

        final Employee employeeAdded =
                _transactionTemplate.execute(new TransactionCallback<Employee>() {
                    @Override
                    public Employee doInTransaction(TransactionStatus status) {
                        return _employeeRepository.save(employeeToAdd);
                    }
                });

        assertNotNull(employeeAdded.getId());

        Employee employeeFound = _transactionTemplate.execute(new TransactionCallback<Employee>() {
            @Override
            public Employee doInTransaction(TransactionStatus status) {
                return _employeeRepository.findOne(employeeAdded.getId());
            }
        });

        assertEquals("name", employeeFound.getName());
        assertEquals("email", employeeFound.getEmail());
    }

    @Test
    public void shouldAssignDirectReportsToManager() {
        final Set<Employee> setOfEmp1to3 = new HashSet<>();

        // 1st transaction: assign emp1..3 as direct reports of emp0
        _transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                assignDirectReportsToEmp0(setOfEmp1to3);
            }
        });

        // 2nd transaction: load emp0 (the manager) and its associated direct reports
        Employee manager =
                _transactionTemplate.execute(new TransactionCallback<Employee>() {
                    @Override
                    public Employee doInTransaction(TransactionStatus status) {
                        Employee manager = _employeeRepository.findByName("emp0");
                        // load the lazily fetched direct reports association while we are within the transaction
                        // by invoking an arbitrary method on the collection proxy
                        manager.getDirectReports().size();
                        return manager;
                    }
                });

        Set<Employee> directReports = manager.getDirectReports();
        List<Employee> managers = extract(directReports, on(Employee.class).getManager());

        assertThat(directReports, equalTo(setOfEmp1to3));
        assertThat(managers, everyItem(is(theInstance(manager))));
    }

    private void assignDirectReportsToEmp0(Set<Employee> directReports1) {
        Employee emp0 = _employeeRepository.findByName("emp0");
        Employee emp1 = _employeeRepository.findByName("emp1");
        Employee emp2 = _employeeRepository.findByName("emp2");
        Employee emp3 = _employeeRepository.findByName("emp3");

        emp1.setManager(emp0);
        emp2.setManager(emp0);
        emp3.setManager(emp0);

        directReports1.add(emp1);
        directReports1.add(emp2);
        directReports1.add(emp3);
    }
}
