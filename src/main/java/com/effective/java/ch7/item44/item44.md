# 표준 함수형 인터페이스를 사용하라



## 질문

- 표준 함수형 인터페이스란?

  ```java
  @FunctionalInterface interface EldestEntryRemovalFunction<K,V> {
      ...
  }
  ```

  - 위와 같이 `@Functional..` 로 정의된 인터페이스를 말하는 듯

## 정리

- 6개 기본 인터페이스
  1. Operator
     - 인수 1개 --> `UnaryOperator<T>`
     - 인수 2개 --> `BinaryOperator<T>` 
     - 반환 값과 인수값이 같은 함수
     - 예시
       - `String::toLowerCase`
  2. Predicate
     - 인수 1개
     - 반환값이 boolean 타입
     - 예시
       - `Collections::isEmpty`
  3. Function
     - T를 받아 R을 리턴함.
     - 예시
       - `Arrays::asList`
  4. Supplier
     - 공급자
     - T를 받아서 뭔가하고 리턴하는 방식
  5. Consumer
     - 소비자
     - 뭔가 받길 하는데, 리턴하는 건 없고
- 표준 함수형 인터페이스는 기본 타입만 지원
  - 박싱된 기본 타입을 사용할 수 도있지만, 성능상 문제가 있을 수도 있기에 사용하지말자
- 함수형 인터페이스를 사용해야할 때, 만들지 말고 이미 있는 것을 사용하자
