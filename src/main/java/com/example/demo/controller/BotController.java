package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.vo.KeyboardVO;
import com.example.demo.vo.MessageButtonVO;
import com.example.demo.vo.MessageVO;
import com.example.demo.vo.PhotoVO;
import com.example.demo.vo.RequestMessageVO;
import com.example.demo.vo.ResponseMessageVO;

import net.minidev.json.JSONObject;

@RestController
public class BotController {
	
//========== VO로 구현 ==========	
	
	@RequestMapping(value = "/keyboard", method = RequestMethod.GET)
	public KeyboardVO keyboard() {
		
		KeyboardVO keyboard = new KeyboardVO
				(new String[] {"챗봇이랑 대화하기", "공지사항 보기", "FAQ 보기", "1:1 문의하기"});
		
		return keyboard;		
	}
	
	@RequestMapping(value = "/message", method = RequestMethod.POST)
	public ResponseMessageVO message(@RequestBody RequestMessageVO vo) {
		
		ResponseMessageVO res_vo = new ResponseMessageVO();
		MessageVO mes_vo = new MessageVO();
		String command = vo.getContent();
		
		
		if(command.equals("메뉴")) {
			
			mes_vo.setText("메뉴에서 명령을 선택해주세요~");
			KeyboardVO keyboard = new KeyboardVO(new String[] 
					{"챗봇이랑 대화하기", "공지사항 보기", "FAQ 보기", "1:1 문의하기"});
			res_vo.setKeyboard(keyboard);
		}
		else if(command.equals("1:1 문의하기")) {
			
			MessageButtonVO messageButton = new MessageButtonVO();
			messageButton.setLabel("1:1 문의하러 가기");
			messageButton.setUrl("https://www.funda.kr/v2/contact");
			mes_vo.setMessage_button(messageButton);
			mes_vo.setText("안녕하세요 고객님, 아래의 URL을 클릭하여 1:1 문의사항을 작성해주세요. (메뉴 다시보기 = '메뉴' 입력!)");
		}
		else if(command.equals("공지사항")) {
			
			MessageButtonVO messageButton = new MessageButtonVO();
			messageButton.setLabel("공지사항 보러 가기");
			messageButton.setUrl("https://www.funda.kr/v2/news?mode=story");
			mes_vo.setMessage_button(messageButton);
			mes_vo.setText("공지사항을 여기에 뿌려주는 기능을 차차 구현하려 합니다. "
					+ "지금은 아래 링크를 클릭하여 공지사항을 확인해주세요ㅎ (메뉴 다시보기 = '메뉴' 입력!)");
		}
		else if(command.equals("FAQ 보기")) {
			
			mes_vo.setText("FAQ 범위를 선택해주세요");
			KeyboardVO keyboard = new KeyboardVO(new String[] {"공통", "대출자", "투자자", "취소"});
			res_vo.setKeyboard(keyboard);
		}
		else if(command.equals("공통")) {
			
			MessageButtonVO messageButton = new MessageButtonVO();
			messageButton.setLabel("공통 FAQ 확인하기");
			messageButton.setUrl("https://www.funda.kr/v2/faq");
			mes_vo.setMessage_button(messageButton);
			mes_vo.setText("아래 링크를 통해 공통 FAQ를 확인하세요! (메뉴 다시보기 = '메뉴' 입력!)");
		}
		else if(command.equals("대출자")) {
			
			MessageButtonVO messageButton = new MessageButtonVO();
			messageButton.setLabel("대출자 FAQ 확인하기");
			messageButton.setUrl("https://www.funda.kr/v2/faq");
			mes_vo.setMessage_button(messageButton);
			mes_vo.setText("아래 링크를 통해 대출자 FAQ를 확인하세요! (메뉴 다시보기 = '메뉴' 입력!)");
		}
		else if(command.equals("투자자")) {
	
			MessageButtonVO messageButton = new MessageButtonVO();
			messageButton.setLabel("투자자 FAQ 확인하기");
			messageButton.setUrl("https://www.funda.kr/v2/faq");
			mes_vo.setMessage_button(messageButton);
			mes_vo.setText("아래 링크를 통해 투자자 FAQ를 확인하세요! (메뉴 다시보기 = '메뉴' 입력!)");
		}
		else if(command.equals("취소")) {
			
			mes_vo.setText("메뉴에서 명령을 선택해주세요~");
			KeyboardVO keyboard = new KeyboardVO(new String[] 
					{"챗봇이랑 대화하기", "공지사항 보기", "FAQ 보기", "1:1 문의하기"});
			res_vo.setKeyboard(keyboard);
		}
		else if(command.equals("챗봇이랑 대화하기")){
			
			PhotoVO photo = new PhotoVO();
			photo.setUrl("http://www.businesscomputingworld.co.uk/wp-content/uploads/2018/01/Chatbot.jpg");
			photo.setHeight(427);
			photo.setWidth(540);
			mes_vo.setPhoto(photo);
			mes_vo.setText("(하트뿅) 저와 대화를 나눠볼까용? 아직은 매우 멍청하답니다ㅎㅎ (메뉴 다시보기 = '메뉴' 입력!)");
		}
		else if(command.contains("안녕")){
			mes_vo.setText("안녕하세요ㅎㅎ");
		}
		else {
			mes_vo.setText("아직 구현하지 않은 명령어입니다. Ryan에게 문의하세요~");
		}
		
		res_vo.setMessage(mes_vo);
		return res_vo;
	}
}	
//========== JSON 객체로 구현  ==========
		
	/*@RequestMapping(value = "/keyboard", method = RequestMethod.GET)
	public String keyboard() {
	
		JSONObject joBtn = new JSONObject();
		ArrayList<String> btns = new ArrayList<>();
		
		btns.add("챗봇이랑 대화하기");
		btns.add("공지사항 보기");
		btns.add("FAQ 보기");
		btns.add("1:1 문의하기");
		
		joBtn.put("type", "buttons");
		joBtn.put("buttons", btns);
		
		return joBtn.toJSONString();

	
	
	@RequestMapping(value = "/message", method = RequestMethod.POST, headers = "Accept=application/json")
	public String message(@RequestBody JSONObject resObj) {
		
		String content;
		content = (String) resObj.get("content");
		JSONObject joRes = new JSONObject();
		JSONObject joText = new JSONObject();
		JSONObject jomesBtn = new JSONObject();
		JSONObject joBtn = new JSONObject();
		JSONObject photo = new JSONObject();
		
		/// 메뉴얼 버튼들
		ArrayList<String> btns = new ArrayList<>();
			btns.add("챗봇이랑 대화하기");
			btns.add("공지사항 보기");
			btns.add("FAQ 보기");
			btns.add("1:1 문의하기");
		
		/// Depth2 버튼들
		ArrayList<String> btns2 = new ArrayList<>();
		
		
		joBtn.put("type", "text");
	
		
		if(content.contains("메뉴")) {
			joText.put("text", "메뉴에서 명령을 선택해주세요~");
			
			joBtn.put("type", "buttons");
			joBtn.put("buttons", btns);
		}
		else if(content.contains("1:1")) {
			joText.put("text", "안녕하세요 고객님, 아래의 URL을 클릭하여 1:1 문의사항을 작성해주세요. (메뉴 다시보기 = '메뉴' 입력!)");
			jomesBtn.put("label", "1:1 문의하러가기");
			jomesBtn.put("url", "https://www.funda.kr/v2/contact");
			joText.put("message_button", jomesBtn);
		
		}
		else if(content.contains("공지사항")) {
			joText.put("text", "공지사항을 크롤링해와서 여기에 뿌려주는 기능을 차차 구현하려 합니다. "
					+ "지금은 아래 링크를 클릭하여 공지사항을 확인해주세요ㅎ (메뉴 다시보기 = '메뉴' 입력!)");
			jomesBtn.put("label", "공지사항 확인하기");
			jomesBtn.put("url", "https://www.funda.kr/v2/news?mode=story");
			joText.put("message_button", jomesBtn);
			
		}
		else if(content.contains("FAQ")) {
			joText.put("text", "FAQ 범위를 선택해주세요");
			btns2.add("공통");
			btns2.add("대출자");
			btns2.add("투자자");
			btns2.add("취소");
			
			joBtn.put("type", "buttons");
			joBtn.put("buttons", btns2);
		}
		else if(content.contains("공통")) {
			joText.put("text", "아래 링크를 통해 공통 FAQ를 확인하세요! (메뉴 다시보기 = '메뉴' 입력!)");
			jomesBtn.put("label", "공통 FAQ 확인하기");
			jomesBtn.put("url", "https://www.funda.kr/v2/faq");
			joText.put("message_button", jomesBtn);
			
		}
		else if(content.contains("대출자")) {
			joText.put("text", "아래 링크를 통해 대출자 FAQ를 확인하세요! (메뉴 다시보기 = '메뉴' 입력!)");
			jomesBtn.put("label", "대출자 FAQ 확인하기");
			jomesBtn.put("url", "https://www.funda.kr/v2/faq");
			joText.put("message_button", jomesBtn);
			
		}
		else if(content.contains("투자자")) {
			joText.put("text", "아래 링크를 통해 투자자 FAQ를 확인하세요! (메뉴 다시보기 = '메뉴' 입력!)");
			jomesBtn.put("label", "투자자 FAQ 확인하기");
			jomesBtn.put("url", "https://www.funda.kr/v2/faq");
			joText.put("message_button", jomesBtn);
			
		}
		
		else if(content.contains("취소")) {
			joText.put("text", "취소되었습니다. 메뉴에서 명령을 선택해주세요~");
		
			joBtn.put("type", "buttons");
			joBtn.put("buttons", btns);
		}
		
		else if(content.contains("챗봇")) {
			joText.put("text", "(하트뿅) 저와 대화를 나눠볼까용? 아직은 매우 멍청하답니다ㅎㅎ (메뉴 다시보기 = '메뉴' 입력!)");
			photo.put("url", "http://www.businesscomputingworld.co.uk/wp-content/uploads/2018/01/Chatbot.jpg");
			photo.put("width", 540);
			photo.put("height", 427);
			joText.put("photo", photo);
			
			PhotoVO photo = new PhotoVO();
			photo.setUrl("https://www.channelsight.com/wp-content/uploads/2018/01/what-chatbot.png");
			photo.setWidth(481);
			photo.setHeight(395);
			
		}
		else if(content.contains("안녕")) {
			joText.put("text", "안녕하세요ㅎㅎ");
		}else if(content.contains("뭐해")) {
			joText.put("text", "챗봇만들어요");
		}else if(content.contains("누구")) {
			joText.put("text", "저는 현금구름 테스트봇이에요~");
		}else if(content.contains("꺼저")) {
			joText.put("text", "반사");
		}else if(content.contains("미친")) {
			joText.put("text", "뭐? 미친?");
		}else if(content.contains("ㅎㅇ")) {
			joText.put("text", "whassup~");
		}else if(content.contains("조용")) {
			joText.put("text", "쉿~");
		}else {
			joText.put("text", "아직 구현하지 않은 명령입니다ㅠ 정수한테 명령어 추가 해달라고 하세요~");
		}
		
		joRes.put("message", joText);
		joRes.put("keyboard", joBtn);
		System.out.println(joRes.toJSONString());
		
		return joRes.toJSONString();
	}
}*/
