package org.shalim.heybeach.config.data;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

	@Value("${db.url}")
	private String dbUrl;

	@Value("${db.username}")
	private String dbUsername;

	@Value("${db.password}")
	private String dbPassword;

	@Value("${data.model.package}")
	private String dataModelPackage;

	@Value("${hibernate.dialect}")
	private String hibernateDialect;

	@Value("${hibernate.show_sql}")
	private String hibernateShowSql;

	@Value("${hibernate.hbm2ddl.auto}")
	private String hibernateHbm2ddl;

	@Autowired
	DataSource dataSource;

	@Autowired
	private LocalContainerEntityManagerFactoryBean entityManagerFactory;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(dbUsername);
		dataSource.setPassword(dbPassword);
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setPackagesToScan(dataModelPackage);
		entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactory.setJpaProperties(getHibernateProperties());

		return entityManagerFactory;
	}

	private Properties getHibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", hibernateDialect);
		hibernateProperties.put("hibernate.show_sql", hibernateShowSql);
		hibernateProperties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddl);

		return hibernateProperties;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory.getObject());

		return jpaTransactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}
