# ordinal 인덱싱 대신 EnumMap을 사용하라



## 질문

- 한정적 타입 토큰이란?
  
  - 한정적 타입의 매개변수, 와일드 카드를 사용하여 표한 가능한 타입을 제한한 타입 토큰.
- 타입 토큰?
  - 	타입을 나타내는 토큰(클래스 리터럴)
  - 클래스 리터럴?
    - 	String.class --> 이 클래스의 타입이 `Class<String>`이다

  





## 정리

- EnumMap은 열거타입을 키로 사용하도록 설계한 아주 빠른 Map의 구현체
  - 내부에서 배열을 사용
  - 그래서 Map의 타입 안정성 + 배열의 성능을 모두 취했음.

- Enum의 ordinal을 사용하려 하지 마라. 
  - 오류만 발생시킬 확률이 높다..

- 예시 코드 직접 쳐보자