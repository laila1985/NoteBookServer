package com.notebookserver.controller;

import java.io.IOException;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.notebookserver.model.CodeSnippet;
import com.notebookserver.services.PytonExecutor;


@RestController
public class NoteBookServerController {
	
	private PytonExecutor pytonExecutor;

	@PostMapping("/run")
	public String executing(@RequestBody CodeSnippet code,
			Model model) {
	
		String text=code.getCode();
		if(StringUtils.startsWithIgnoreCase(text,"%python")) {
			try {
				return pytonExecutor.execute(text.replace("%python", ""));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return code.getCode();
	}
}
