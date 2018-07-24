package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@RestController
public class BotController {
	
	@RequestMapping("/home")
	public String home() {
		
		return "된다~~~~~";
	}
	
	@RequestMapping(value = "/keyboard", method = RequestMethod.GET)
	public String keyboard() {
		
		JSONObject joBtn = new JSONObject();
		joBtn.put("type", "text");
		
		return joBtn.toJSONString();
	}
	
	@RequestMapping(value = "/message", method = RequestMethod.POST, headers = "Accept=application/json")
	public String message(@RequestBody JSONObject resObj) {
		
		String content;
		content = (String) resObj.get("content");
		JSONObject joRes = new JSONObject();
		JSONObject joText = new JSONObject();
		
		if(content.contains("안녕")) {
			joText.put("text", "안녕하세요");
		}else if(content.contains("뭐해")) {
			joText.put("text", "챗봇만들어요");
		}else {
			joText.put("text", "무슨 소리야?");
		}
		
		joRes.put("message", joText);
		System.out.println(joRes.toJSONString());
		
		return joRes.toJSONString();
	}
}
