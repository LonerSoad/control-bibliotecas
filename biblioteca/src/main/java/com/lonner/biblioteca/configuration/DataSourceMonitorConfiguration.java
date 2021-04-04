/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lonner.biblioteca.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 *
 * @author carlos
 */
@Configuration
public class DataSourceMonitorConfiguration {


    @Bean
    public DataSource dataSourceVortex( @Value("${database.url:}") final String databaseUrl,
                                        @Value("${database.user:}") final String databaseUser,
                                        @Value("${database.password:}") final String databasePassword ) {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl(databaseUrl);
        dataSource.setUser(databaseUser);
        dataSource.setPassword(databasePassword);
        // Para cuando se desconecte por mucho tiempo de la base se vuelva a reconectar
        dataSource.setTestConnectionOnCheckout(true);
        try {
            dataSource.setDriverClass("org.mariadb.jdbc.Driver");
        } catch (PropertyVetoException ex) {
            throw new InternalError(ex.toString());
        }
        return dataSource;
    }

}
