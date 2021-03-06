package com.notebookserver.services;

import java.io.IOException;
import java.util.Map;

import org.json.JSONObject;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.util.StringUtils;

import com.notebookserver.exceptions.PytonExecutionException;
import com.notebookserver.exceptions.WrongCodeException;
import com.notebookserver.model.CodeSnippet;


public class PytonExecutorImp implements PytonExecutor {

	private final String fileName = "result.txt";

	private int variable;
	FileResultManagement fileManagement = new FileResultManagementImp();

	public String execute(String code) throws IOException {

		PyObject x = null;
		
			PythonInterpreter interp = new PythonInterpreter();
			System.out.println("****** Python code process Started *******");

			// Read from file
			Map<String, Integer> map = fileManagement.readFromFile(fileName);
			if (map != null) {
				for (Map.Entry mapentry : map.entrySet()) {
					System.out.println("clé: " + mapentry.getKey() + " | valeur: " + mapentry.getValue());
					setVariable((int) mapentry.getValue());
				}
			}

			if (code.contains("=")) {
				String executedCode = StringUtils.replace(code, "print", " ");
				
				// verify the code before parsing it in the file:
				try {
					interp.exec(executedCode.trim());
				} catch (Exception ex) {
					throw new PytonExecutionException(interp.getSystemState());
				}
				
				fileManagement.clearFile(fileName);
				fileManagement.writeInFile(fileName, code);
				return "";
				// Print affiche result
			} else if (code.contains("print")) {

				// remove print
				String executedCode = StringUtils.replace(code, "print", " ");
				if (map == null) {
					interp.set("a", new PyInteger(0));

					// handle exception if execution went wrong
					try {
						interp.exec("a= " + executedCode);
						x = interp.get("a");
					} catch (Exception ex) {
						throw new PytonExecutionException(interp.getSystemState());
					}
				} else {
					try {
						interp.set("a", new PyInteger(variable));
						interp.exec("print a ");
						interp.exec("a=" + executedCode);
						x = interp.get("a");

					} catch (Exception ex) {
						throw new PytonExecutionException(interp.getSystemState());
					}

				}

				System.out.println("****** Python code ended  *****************");
				interp.close();
			}

		return x.toString();
	}

	@Override
	public void setVariable(int var) {

		this.variable = var;

	}

	@Override
	public String CheckAndExecutCode(CodeSnippet code) throws IOException, WrongCodeException {

		JSONObject entity = new JSONObject();
		String text = code.getCode().toString();

		System.out.println(text);

		if (StringUtils.startsWithIgnoreCase(text, "%python")) {

			try {
				text = text.replace("%python", "");
				System.out.println("The executed code: " + text);
				String result = execute(text);


				entity = new JSONObject();
				entity.put("result", result);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new IOException();
			}
			// The code should start by %
		} else if (!StringUtils.startsWithIgnoreCase(text, "%") | StringUtils.startsWithIgnoreCase(text, "% ")) {
			throw new WrongCodeException("Please respect the following format : %<interpreter-name><whitespace><code>");

			// Only python interpreter has been imlepemented
		} else {
			throw new WrongCodeException("Only python interpreter has been  imlepemented!! ");

		}

		return entity.toString();

	}

}
