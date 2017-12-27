package br.com.rodolfocastanho.controle2M;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class Controle2MApplication extends SpringBootServletInitializer {

	//Override apenas para packaging = war
	//Não esquecer de retirar o extends da classe
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Controle2MApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Controle2MApplication.class, args);
	}
}
