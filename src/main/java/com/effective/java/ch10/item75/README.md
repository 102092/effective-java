# 예외의 상세 메세지에 실패 관련 정보를 담으라



## 질문



## 정리

- 예외가 발생한 순간, 관여한 모든 매개변수와 필드 값을 실패 메세지에 담아야한다.
  - 그렇지만 이 중에 중요 정보가..있다면? 안 담아야겠지
  - 예외 생성자에 모두 담을수 있도록..?
- 예외 메세지와 최종 사용자에게 보여줄 오류 메세지를 혼동해서는 안된다.

- 비검사 예외라도, 상세 정보를 알려주는 접근자 메서드를 제공해도 좋을듯..?