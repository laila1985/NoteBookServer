package com.notebookserver.services;

import java.io.IOException;

import com.notebookserver.exceptions.WrongCodeException;
import com.notebookserver.model.CodeSnippet;

public interface PytonExecutor {

	public String execute(String code) throws IOException;

	public void setVariable(int var);

	public String CheckAndExecutCode(CodeSnippet code) throws IOException,WrongCodeException;
}
