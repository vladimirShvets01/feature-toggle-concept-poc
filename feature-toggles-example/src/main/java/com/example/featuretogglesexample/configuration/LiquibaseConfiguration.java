package com.example.featuretogglesexample.configuration;

import java.sql.SQLException;
import javax.sql.DataSource;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LiquibaseConfiguration {

    private final DataSource dataSource;

    @Value("${spring.liquibase.change-log}")
    private String changeLog;
//
//    @Bean
//    public SpringLiquibase liquibase() {
//        SpringLiquibase liquibase = new SpringLiquibase();
//        liquibase.setDataSource(dataSource);
//        liquibase.setChangeLog(changeLog);
//        return liquibase;
//    }

    @Bean
    public Liquibase liquibase2() throws LiquibaseException {
        try {
            Liquibase liquibase = new Liquibase(changeLog, new ClassLoaderResourceAccessor(),
                                                new JdbcConnection(dataSource.getConnection()));
            return liquibase;
        } catch(SQLException e) {

        }
        return null;
    }


}
