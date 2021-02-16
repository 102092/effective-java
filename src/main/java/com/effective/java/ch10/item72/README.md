# 표준 예외를 사용하라



## 질문



## 정리

- 표준 예외를 사용하면, 클라이언트가 개발자가 만든 API를 익히고 사용하기가 쉬워진다.
  - 왜? 익숙하니까!
- IlleaglArgmunetException : 호출자 인수로 부적절한 값을 넘길 때
  - null을 허용하지 않는 메서드에 null을 던지면..?
  - NullPointerException이 발생!
- IllegalStateException : 대상 객체의 상태가 호출된 메서드를 수행하기에 적합하지 않을 때
- UnsupportedOperationException : 클라이언트가 요청한 동작을 대상 객체가 지원하지 않을 때
- Exception, RuntimException, Throwable, Error는 직접 재사용하지 말자.
  - 이 클래스는 추상 클래스라 생각하자.
    - 즉 여러 예외를 포함하고 있으므로, 테스트에 어려움이 따른다.
  - 다른 예외를 구현할 때만 사용되도록 만들어야함..

- 예외는 직렬화 할 수 있다.
  - 그렇지만 직렬화에는 많은 부담이 따른다.
  - 그러므로, 이미 존재하는 표준 예외를 사용하는 게 성능상 유리할 수 도..?