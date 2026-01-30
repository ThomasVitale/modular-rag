package com.thomasvitale.demo;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.aura.Aura;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@PWA(name = "Modular RAG", shortName = "Modular RAG")
@Push
@StyleSheet(Aura.STYLESHEET)
@StyleSheet(Lumo.UTILITY_STYLESHEET)
@StyleSheet("styles.css")
public class ModularRagApplication implements AppShellConfigurator {

	static void main(String[] args) {
		SpringApplication.run(ModularRagApplication.class, args);
	}

}
