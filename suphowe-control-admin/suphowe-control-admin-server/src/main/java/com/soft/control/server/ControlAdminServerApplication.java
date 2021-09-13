package com.soft.control.server;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springboot-admin-server
 * @author suphowe
 */
@EnableAdminServer
@SpringBootApplication
public class ControlAdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ControlAdminServerApplication.class, args);
    }

}
