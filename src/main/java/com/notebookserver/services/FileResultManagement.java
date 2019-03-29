package com.notebookserver.services;

import java.io.IOException;
import java.util.Map;

public interface FileResultManagement {
	
	public void clearFile(String fileName);
	public Boolean writeInFile(String fileName ,String data);
	public Map<String, Integer> readFromFile( String fileName) throws IOException;
	

}
