# 공개된 API 요소에는 항상 문서화 주석을 작성하라



## 질문



## 정리

- 자바 8 `implSpec`
- 공개된 API라면 연관된 모든 공개된 클래스, 인터페이스, 메서드, 필드 선언에 문서화 주석을 달아야 한다.
  - 공개 클래스는 절대 기본 생성자를 이용하면 안됨. 
  - 왜? 기본 생성자에 문서화를 달을 방법이 없기 때문에
- 메서드용 문서화 주석에는 무슨 일을 하는 지 기술해야함.
  - 해당 메서드를 호출하기 위한 전제조건
  - 해당 메서드를 호출한 후에, 만족해야하는 사후 조건
  - 부작용
- `@param` , `@return` , `@throws` 태그 설명에는 마침표를 붙이지 않는다
- `@code` 태그는
  1. 태그로 감싼 내용을 코드용 폰드로 렌더링
  2. 태그로 감싼 내용에 포함된 html 요소나 다른 자바독 태그를 무시
- 클래스가 상속용으로 설계되었을 경우, 자기 사용 패턴(self-use pattern) 에 대해서도 문서에 남겨야함
- 각 문서화 주석의 첫번째 문장은 해당 요소의 요약 설명으로 간주된다.
  - 메서드와 생성자의 동작을 설명하는, 주어 없는 동사구어야함.

- 제네릭 타입이나 제네릭 메서드를 문서화 할때는 모든 타입 매개변수에 주석을 달아야한다.
- 열거 타입을 문서화 할때는 상수들에 주석을 달아야 한다.
- 애너테이션 타입을 문서화 할때도 멤버들에 모두 주석을 달아야한다.
- API 문서화 할때 누락되는 두가지
  - 스레드 안전성
  - 직렬화 가능성



## 생각