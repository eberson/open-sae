package br.org.sae.test;

import java.io.File;
import java.net.URI;
import java.sql.Connection;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/br/org/sae/test/opensae.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class TestImportacaoMatricula {
	
	@Autowired
	private DataSource dataSource;
	
	@Before
	public void before() throws Exception{
		DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());
	}
	
	@After
	public void after() throws Exception{
		DatabaseOperation.DELETE_ALL.execute(getConnection(), getDataSet());
	}
	
	private IDatabaseConnection getConnection() throws Exception{
		Connection conexao = DataSourceUtils.getConnection(dataSource);
		return new DatabaseConnection(conexao);
	}
	
	private IDataSet getDataSet() throws Exception{
		URI uri = getClass().getResource("/br/org/sae/test/dataset.xml").toURI();
		File file = new File(uri);
		
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		return builder.build(file);
	}
	
	
	@Test
	public void testImportacao(){
		
	}

}
