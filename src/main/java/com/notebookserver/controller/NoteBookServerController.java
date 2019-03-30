package com.notebookserver.controller;

import java.io.IOException;

import org.python.jline.internal.Log;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.notebookserver.model.CodeSnippet;
import com.notebookserver.services.PytonExecutor;
import com.notebookserver.services.PytonExecutorImp;

import ch.qos.logback.classic.Logger;


@RestController
public class NoteBookServerController {
	
	private final PytonExecutor pytonExecutor=new PytonExecutorImp();
	private Logger log;

	@PostMapping("/execute")
	public String executing(@RequestBody CodeSnippet code) {
	
		String text=code.getCode().toString();
		//log.info("The get code is : "+ text);
		System.out.println(text);
		if(StringUtils.startsWithIgnoreCase(text,"%python")) {
			try {
				text =text.replace("%python", "");
				System.out.println("The executed code: "+ text);
				String result =pytonExecutor.execute(text);
				
				Log.info("The result value is ===>" +result);
				return result; 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return code.getCode();
	}
}
