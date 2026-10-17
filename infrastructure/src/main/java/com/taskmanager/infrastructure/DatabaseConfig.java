package com.taskmanager.infrastructure;

import org.flywaydb.core.Flyway;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.postgresql.ds.PGSimpleDataSource;

public class DatabaseConfig {

    private final String url;
    private final String username;
    private final String password;
    private final PGSimpleDataSource dataSource;
    private final DSLContext dslContext;

    public DatabaseConfig(String url, String user, String password) {
        this.url = url;
        this.username = user;
        this.password = password;
        this.dataSource = buildPGSimpleDataSource();
        this.dslContext = DSL.using(this.dataSource, SQLDialect.POSTGRES);
        migrate();
    }

    private PGSimpleDataSource buildPGSimpleDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        return dataSource;
    }
    
    public void migrate() {
        Flyway flyway = Flyway.configure()
                .dataSource(this.dataSource)
                .locations("classpath:/db/migration") // locations tells Flyway where to find SQL files, not the database URL
                .load();
        flyway.migrate();
    }

    public DSLContext dsl() {
        // DSLContext should be created only one time and re-used
        return dslContext;
    }
}
