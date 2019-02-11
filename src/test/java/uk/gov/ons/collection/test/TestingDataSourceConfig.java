package uk.gov.ons.collection.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

@ActiveProfiles("mocking")
@Configuration
public class TestingDataSourceConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setName("testDb;DB_CLOSE_ON_EXIT=FALSE;MODE=MSSQLServer;INIT=create " +
                        "schema if not exists " +
                        "schema_a\\;create schema if not exists schema_b;" +
                        "DB_CLOSE_DELAY=-1;")
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .build();
    }
}