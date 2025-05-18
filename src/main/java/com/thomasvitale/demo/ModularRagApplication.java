package com.thomasvitale.demo;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@PWA(name = "Modular RAG", shortName = "Modular RAG")
@Push
public class ModularRagApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(ModularRagApplication.class, args);
	}

}
