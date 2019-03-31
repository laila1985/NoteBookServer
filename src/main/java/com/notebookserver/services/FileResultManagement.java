package com.notebookserver.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface FileResultManagement {
	
	public void clearFile(String fileName) throws IOException;
	public Boolean writeInFile(String fileName ,String data) throws FileNotFoundException, UnsupportedEncodingException;
	public Map<String, Integer> readFromFile( String fileName) throws IOException;
	

}
