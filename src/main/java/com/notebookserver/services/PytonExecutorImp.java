package com.notebookserver.services;

import java.io.IOException;
import java.util.Map;

import org.json.JSONObject;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.jline.internal.Log;
import org.python.util.PythonInterpreter;
import org.springframework.util.StringUtils;

import com.notebookserver.exceptions.WrongCodeException;
import com.notebookserver.model.CodeSnippet;

import ch.qos.logback.classic.Logger;

public class PytonExecutorImp implements PytonExecutor {

	private Logger log;
	private final String fileName = "result.txt";

	private int variable;
	FileResultManagement fileManagement = new FileResultManagementImp();

	public String execute(String code) throws IOException {

		PyObject x = null;
		try {
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
				fileManagement.clearFile(fileName);
				fileManagement.writeInFile(fileName, code);
				return "";
				// Print affiche result
			} else if (code.contains("print")) {

				// remove print
				String executedCode = StringUtils.replace(code, "print", " ");
				if (map == null) {
					interp.set("a", new PyInteger(0));
					interp.exec("a= " + executedCode);
					x = interp.get("a");
				} else {
					interp.set("a", new PyInteger(variable));
					interp.exec("print a ");
					interp.exec("a=" + executedCode);
					x = interp.get("a");

				}

				System.out.println("****** Python code ended  *****************");
				interp.close();
			}

		} catch (Exception ex) {
			log.error("Exception while creating python interpreter: " + ex.toString());
		}

		return x.toString();
	}

	@Override
	public void setVariable(int var) {

		this.variable = var;

	}

	@Override
	public String CheckAndExecutCode(CodeSnippet code) throws IOException,WrongCodeException {

		JSONObject entity = new JSONObject();
		String text = code.getCode().toString();

		System.out.println(text);

		if (StringUtils.startsWithIgnoreCase(text, "%python")) {

			try {
				text = text.replace("%python", "");
				System.out.println("The executed code: " + text);
				String result = execute(text);

				Log.info("The result value is ===>" + result);

				entity = new JSONObject();
				entity.put("result", result);
				Log.info(entity.toString());

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
