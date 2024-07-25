package com.springboot.commers;

import org.springframework.boot.SpringApplication;

public class TestGenericCommersApplication {

	public static void main(String[] args) {
		SpringApplication.from(GenericCommersApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
