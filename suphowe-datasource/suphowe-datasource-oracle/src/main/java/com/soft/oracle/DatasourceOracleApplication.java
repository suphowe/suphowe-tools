package com.soft.oracle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * ORACLE集成
 * @author suphowe
 */
@SpringBootApplication
@EnableTransactionManagement
public class DatasourceOracleApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatasourceOracleApplication.class, args);
    }

}
