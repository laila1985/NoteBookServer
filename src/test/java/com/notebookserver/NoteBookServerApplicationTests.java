package com.notebookserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.notebookserver.exceptions.PytonExecutionException;
import com.notebookserver.exceptions.WrongCodeException;
import com.notebookserver.model.CodeSnippet;
import com.notebookserver.services.FileResultManagement;
import com.notebookserver.services.FileResultManagementImp;
import com.notebookserver.services.PytonExecutorImp;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteBookServerApplicationTests {

	private FileResultManagement fileManagemet = new FileResultManagementImp();
	private PytonExecutorImp python = new PytonExecutorImp();

	@Test
	public void TestWriteInFile() throws IOException {

		String file = "testWriteInFile.txt";
		fileManagemet.clearFile(file);
		assertTrue(fileManagemet.writeInFile(file, "a=1"));

		Map<String, Integer> map = fileManagemet.readFromFile(file);
		String s = "";
		if (map != null) {
			for (Map.Entry mapentry : map.entrySet()) {
				System.out.println("clé: " + mapentry.getKey() + " | valeur: " + mapentry.getValue());
				s = mapentry.getKey() + "=" + mapentry.getValue();
			}
		}
		assertEquals(s, "a=1");

	}

	@Test
	public void testPytonExecutor() throws IOException {

		String res = python.execute("print 1+1");
		assertEquals("2", res);

	}

	@Test
	public void testPytonExecutorVariableInitialisation() throws IOException {
		String res = python.execute("a = 1");
		assertEquals("", res);

	}

	@Test
	public void testPytonExecutorWithVariable() throws IOException {
		String res = python.execute("a=20");
		res = python.execute("print a+51");
		assertEquals("71", res);

	}
	
	@Test(expected = PytonExecutionException.class)
	public void testPytonExecutionException1() throws IOException {
		String res = python.execute("print dfjhhs print djf+56bfd+fjhds-sjdhf");
	}

	
	@Test(expected = WrongCodeException.class)
	public void testWrongCodeException() throws IOException {
		
		CodeSnippet code=new CodeSnippet();
		code.setCode("%  print a");
		String res = python.CheckAndExecutCode(code);
	}
	
	@Test(expected = WrongCodeException.class)
	public void testWrongCodeExceptionWrongInterpreter() throws IOException {
		CodeSnippet code=new CodeSnippet();
		code.setCode("%sql select data");
		
		String res = python.CheckAndExecutCode(code);
	}
	

}
