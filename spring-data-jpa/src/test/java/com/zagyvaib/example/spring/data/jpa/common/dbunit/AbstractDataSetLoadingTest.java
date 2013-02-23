package com.zagyvaib.example.spring.data.jpa.common.dbunit;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.io.InputStream;

/**
 * Test classes extending this class will load an initial data set with DBUnit before the first test method is invoked.
 * The data set is not re-loaded on the invocation of subsequent test methods.
 * <p>
 * It is assumed that extending test classes will be run with
 * {@link org.springframework.test.context.junit4.SpringJUnit4ClassRunner SpringJUnit4ClassRunner},
 * and the test context contains a single configured {@link javax.sql.DataSource}.
 * <p>
 * Subclasses are supposed to specify the data set to be loaded by overriding {@link
 * AbstractDataSetLoadingTest#getPathToDataSet()}.
 */
public abstract class AbstractDataSetLoadingTest {

    private static boolean isDataSetLoaded = false;

    @Autowired
    private DataSource _dataSource;

    protected abstract String getPathToDataSet();

    @Before
    public void loadDataSet() throws Exception {
        if (!isDataSetLoaded) {
            loadDataset(getPathToDataSet());
            isDataSetLoaded = true;
        }
    }

    private void loadDataset(String pathToDataSet) throws Exception {
        IDatabaseConnection connection = new DatabaseConnection(_dataSource.getConnection());
        InputStream inputStream = new ClassPathResource(pathToDataSet).getInputStream();
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(inputStream);
        InsertIdentityOperation.CLEAN_INSERT.execute(connection, dataSet);
    }
}
