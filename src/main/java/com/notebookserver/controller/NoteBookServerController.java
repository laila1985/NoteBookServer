package com.notebookserver.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.python.jline.internal.Log;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notebookserver.model.CodeSnippet;
import com.notebookserver.services.PytonExecutor;
import com.notebookserver.services.PytonExecutorImp;



@RestController
@RequestMapping("/api")
public class NoteBookServerController {
	
	private final PytonExecutor pytonExecutor=new PytonExecutorImp();
	
	
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

	
	@PostMapping(path="/execute",produces=MediaType.APPLICATION_JSON_VALUE)
	public String executing(@RequestBody CodeSnippet code,Model model, HttpServletRequest request) {
		
		JSONObject entity = new JSONObject();
		String text=code.getCode().toString();
		
		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		
		if (messages == null) {
			messages = new ArrayList<>();
			request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
		}
		
		
		System.out.println(text);
		
		if(StringUtils.startsWithIgnoreCase(text,"%python")) {
			
			try {
				text =text.replace("%python", "");
				System.out.println("The executed code: "+ text);
				String result =pytonExecutor.execute(text);
				
				Log.info("The result value is ===>" +result);
				
				entity = new JSONObject();
		        entity.put("result", result);
		        Log.info(entity.toString());
		        messages.add(entity.toString());
				request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
				return entity.toString(); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//The code should start by %
		}else if(!StringUtils.startsWithIgnoreCase(text,"%"))  {
			entity.put("error", "Please respect the following format : %<interpreter-name><whitespace><code>");
			Log.info(entity.toString());
			return entity.toString();
			//Only python interpreter has been  imlepemented 
		}else {
			entity.put("error", "Only python interpreter has been  imlepemented!! ");
			Log.info(entity.toString());
			return entity.toString();
		}
		return text;
		
	}
	
	@PostMapping("/destroy")
	public String destroySession(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/execute";
	}
}
