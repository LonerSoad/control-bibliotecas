package com.lonner.biblioteca.configuration;

import com.lonner.biblioteca.repositories.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Configuration
@EntityScan(basePackages = {"com.lonner.biblioteca.entities","com.lonner.biblioteca.converters"})
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.lonner.biblioteca.repositories.*"}, entityManagerFactoryRef = "entityManager",
        transactionManagerRef = "transactionManager",repositoryBaseClass = CrudRepository.class)
public class HibernateConfiguration {

    @Autowired
    private DataSource dataSource;

    @PersistenceContext(unitName = "biblioteca")
    @Bean(name = "entityManager")
    public LocalContainerEntityManagerFactoryBean mySqlEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource).persistenceUnit("biblioteca")
                .packages("com.lonner.biblioteca").build();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(mySqlEntityManagerFactory(builder).getObject());
        tm.setDataSource(dataSource);
        return tm;
    }
}
