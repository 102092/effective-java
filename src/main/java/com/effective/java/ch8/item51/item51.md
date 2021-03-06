# 메서드 시그니처를 신중히 설계하라

## 질문



## 정리

- 메서드 이름을 신중히 짓자 (항상 표준 명명규칙을 따라야 한다)
  - 같은 패키지에 속한 다른 이름들과 일관되게 짓자
- 편의 메서드를 너무 많이 만들지 말자
- 매개 변수 목록을 짦게 유지하자
  - 4개 이하가 최선
- 매개 변수를 줄이려면?
  - 메서드를 여러 메서드로 쪼개는 방법
  - 매개 변수를 여러개를 묶어주는 도우미 클래스를 만드는 것
  - 빌더 패턴을 메서드 호출에 응용

- 매개 변수 타입으로는 클래스보다는 인터페이스가 더 낫다
  - 해시 맵보다는 맵을 넘겨라
  - boolean을 넘기기 보다는 원소 2개짜리 열거타입이 낫다.
    - 왜? 명확하게 어떤 걸 넘기는 지 알려주기 때문에
- 왜? 신중하게 설계해야할까?
  - 아마도 코드 가독성을 위함인듯.
  - 개발자가 보았을 때, 한 눈에 알아보기 쉽게, 읽기 쉽게, 파악하기 쉽게 하기 위해서 메서드를 쪼개고, 이름을 신중하게 짓고, 매개변수도 명확하게 받을 수 있으면 받으라는 뜻 같다.