package com.notebookserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.notebookserver.controller.NoteBookServerController;

@SpringBootApplication
@ComponentScan(basePackageClasses = NoteBookServerController.class)
public class NoteBookServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteBookServerApplication.class, args);
	}

}
