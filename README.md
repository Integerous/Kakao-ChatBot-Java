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
- Keyboard
- Message
- MessageButton
- Photo

### 2.2. APIs
- Home Keyboard API
- 메시지 수신 및 자동응답 API
- 친구 추가/차단 알림 API
- 채팅방 나가기
