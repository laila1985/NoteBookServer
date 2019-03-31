package com.notebookserver.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileResultManagementImp implements FileResultManagement {

	

	/*
	 * Clear file (non-Javadoc)
	 * 
	 * @see com.service.python.FileResultManagement#clearFile()
	 */
	@Override
	public void clearFile(String fileName) throws IOException {
		// TODO Auto-generated method stub
		try {
			FileChannel.open(Paths.get(fileName), StandardOpenOption.WRITE).truncate(0).close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new IOException();
		}

	}

	/*
	 * save the variable on the file (non-Javadoc)
	 * 
	 * @see com.service.python.FileResultManagement#writeInFile(java.lang.String)
	 */
	@Override
	public Boolean writeInFile(String fileName,String data) throws FileNotFoundException,UnsupportedEncodingException {

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(fileName, "UTF-8");
			writer.println(data);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new FileNotFoundException();
		}catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			throw new UnsupportedEncodingException();
		}
		return Boolean.TRUE;

	}
	/*
	 * Read the saved variable (non-Javadoc)
	 * 
	 * @see com.service.python.FileResultManagement#readFromFile()
	 */

	@Override
	public Map<String, Integer> readFromFile(String fileName) throws IOException {
		
		Map<String, Integer> map = null;
		try {
			if (Files.size(Paths.get(fileName)) > 0) {
				Stream<String> rows3 = Files.lines(Paths.get(fileName));
				map = new HashMap<>();
				map = rows3.map(x -> x.split("="))
						.filter(x -> x.length == 2)
						.collect(Collectors.toMap(
								x -> x[0], 
								x -> Integer.parseInt(x[1].trim())));

				rows3.close();
				for (String key : map.keySet()) {
					System.out.println("****** variable has been read :"+key + "=" + map.get(key));
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new IOException();
		}
		return map;
	}

}
