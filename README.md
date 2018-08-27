# 카카오톡 플러스친구 챗봇 개발
>추후 회사 업무에 필요한 챗봇을 만들기 위해 테스트로 개발중인 카카오톡 플러스친구 챗봇 개발기 (작성중)
>>[카카오톡 플러스친구 API v.2.0](https://github.com/plusfriend/auto_reply)의 Document를 보고 만들었다.

## 1. 개발 환경
- AWS Ubuntu EC2
- Spring boot 2.0.3.
- Java 8

## 2. 카카오톡 플러스친구 API v.2.0 간단 설명

### 2.1. Objects
>카톡 플러스친구 챗봇 개발시 사용할 수 있는 객체는 [API Document](https://github.com/plusfriend/auto_reply#6-object)에 명시되어있다.
- [Keyboard](https://github.com/plusfriend/auto_reply#61-keyboard)
- [Message](https://github.com/plusfriend/auto_reply#62-message)
- [MessageButton](https://github.com/plusfriend/auto_reply#621-messagebutton)
- [Photo](https://github.com/plusfriend/auto_reply#63-photo)

### 2.2. APIs
- [Home Keyboard API](https://github.com/plusfriend/auto_reply#51-home-keyboard-api)
- [메시지 수신 및 자동응답 API](https://github.com/plusfriend/auto_reply#52-%EB%A9%94%EC%8B%9C%EC%A7%80-%EC%88%98%EC%8B%A0-%EB%B0%8F-%EC%9E%90%EB%8F%99%EC%9D%91%EB%8B%B5-api)
- [친구 추가/차단 알림 API](https://github.com/plusfriend/auto_reply#53-%EC%B9%9C%EA%B5%AC-%EC%B6%94%EA%B0%80%EC%B0%A8%EB%8B%A8-%EC%95%8C%EB%A6%BC-api)
- [채팅방 나가기](https://github.com/plusfriend/auto_reply#54-%EC%B1%84%ED%8C%85%EB%B0%A9-%EB%82%98%EA%B0%80%EA%B8%B0)

## 3. 구현
### 3.1. API 명세서에 기재된 Objects를 VO 파일로 생성  
>Getter/Setter, ToString()을 생성하고, KeyboardVO의 경우에만 생성자로 버튼 배열 지정(버튼으로만 쓰이므로)
  - KeyboardVO.java
    - String type;
    - String[] buttons;
  - MessageButtonVO.java
    - String label;
    - String url;
  - MessageVO.java
    - String text;
    - PhotoVO photo;
    - MessageButtonVO message_button;
  - PhotoVO.java
    - String url;
    - int width;
    - int height;
### 3.2. 메세지 수신 및 자동응답 API의 Objects를 VO 파일로 생성
  - RequestMessageVO.java (메세지 수신)
    - String user_key;
    - String type;
    - String conent;
  - ResponseMessageVO.java (자동 응답)
    - MessageVO message;
    - KeyboardVO keyboard;
### 3.3. Controller 생성 
~~~java
package com.example.demo.controller;

~~~~~~ import 생략 ~~~~~~

@RestController
public class BotController {


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
		
		~~~~~~~~~~~~~~~~~~~~~~ 생략 ~~~~~~~~~~~~~~~~~~~~~
		
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
~~~
## 4. AWS EC2 Linux 서버에 배포
