package com.notebookserver.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notebookserver.exceptions.WrongCodeException;
import com.notebookserver.model.CodeSnippet;
import com.notebookserver.services.PytonExecutor;
import com.notebookserver.services.PytonExecutorImp;

@RestController
@RequestMapping("/api")
public class NoteBookServerController {

	private final PytonExecutor pytonExecutor = new PytonExecutorImp();

	@GetMapping("/initSession")
	public String process(Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if (messages == null) {
			messages = new ArrayList<>();
		}
		model.addAttribute("sessionMessages", messages);

		return "redirect:/execute";
	}

	@PostMapping(path = "/execute", produces = MediaType.APPLICATION_JSON_VALUE)
	public String executing(@RequestBody CodeSnippet code, Model model, HttpServletRequest request) throws WrongCodeException, IOException {

		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");

		if (messages == null) {
			messages = new ArrayList<>();
			request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
		}

		String result = pytonExecutor.CheckAndExecutCode(code);
		messages.add(result);
		request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);

		return result;

	}

	@PostMapping("/destroy")
	public String destroySession(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/execute";
	}
	
}
