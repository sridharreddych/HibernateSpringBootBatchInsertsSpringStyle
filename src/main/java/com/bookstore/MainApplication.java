package com.bookstore;

import com.bookstore.impl.BatchRepositoryImpl;
import com.bookstore.service.BookstoreService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = BatchRepositoryImpl.class)
public class MainApplication {

    private final BookstoreService bookstoreService;

    public MainApplication(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
            bookstoreService.batchAuthors();
        };
    }
}

/* Removed in pom maven related 
 * <!--  <maven.compiler.source>12</maven.compiler.source>
        <maven.compiler.target>12</maven.compiler.target> -->
        
        Batch Inserts In Spring Boot Style

Description: Batch inserts (in MySQL) in Spring Boot style.

Key points:

in application.properties set spring.jpa.properties.hibernate.jdbc.batch_size
in application.properties set spring.jpa.properties.hibernate.generate_statistics (just to check that batching is working)
in application.properties set JDBC URL with rewriteBatchedStatements=true (optimization for MySQL)
in application.properties set JDBC URL with cachePrepStmts=true (enable caching and is useful if you decide to set prepStmtCacheSize, prepStmtCacheSqlLimit, etc as well; without this setting the cache is disabled)
in application.properties set JDBC URL with useServerPrepStmts=true (this way you switch to server-side prepared statements (may lead to signnificant performance boost))
in case of using a parent-child relationship with cascade persist (e.g. one-to-many, many-to-many) then consider to set up spring.jpa.properties.hibernate.order_inserts=true to optimize the batching by ordering inserts
in entity, use the assigned generator since the Hibernate IDENTITY will cause insert batching to be disabled
if is not needed then ensure that Second Level Cache is disabled via spring.jpa.properties.hibernate.cache.use_second_level_cache=false
 * 
 */
