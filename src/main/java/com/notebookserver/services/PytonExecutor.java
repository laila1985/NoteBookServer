package com.notebookserver.services;

import java.io.IOException;

public interface PytonExecutor {

	public String execute(String code) throws IOException;

	public void setVariable(int var);

}
