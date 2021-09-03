package com.netty.server;

import com.netty.server.system.NettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * netty 服务端
 * @author suphowe
 */
@SpringBootApplication
public class NettyServerApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(NettyServerApplication.class);


	@Autowired
	private NettyServer server;

	public static void main(String[] args) {
		SpringApplication.run(NettyServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("run  .... . ... ");
		server.start();
	}
}
