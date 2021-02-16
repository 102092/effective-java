# 메서드가 던지는 모든 예외를 문서화하라



## 질문

> 자바독 유틸리티는 메서드 선언의 throws절에 등장하고 메서드 주석의 @throws 태그에도 명시한 예외와 @throws태그에만 명시한 예외를 시각적으로 구분해준다.?

```java
   /**
     *
     * @return secondField
     * @throws NullPointerException if parameter is null or empty 비검사예외
     * @throws IllegalAccessError if someone access 검사예외
     */
    public int getSecondField() throws IllegalAccessError {
        return secondField; //비검사 예외는 메서드 선언 옆에 throws 로 기재하지 않는다.
    }
```



## 정리

- 각 메서드가 던지는 예외를 문서화하는 데 시간을 쏟자 --> 중요하다.
  - 자바독 `@throws` 태그를 이용해서 정확하게 문서화하자
- Main 메서드는 Exception을 던져도 괜찮다.
  - 왜? JVM만을 호출하므로..
- 비검사 예외는, 메서드 선언의 throws 목록에 넣지 말자.

- 어떤 클래스에 정의된 많은 메서드가 같은 이유로, 같은 예외를 던지면?
  - 클ㄹ래스 설명에 어떤 예외가 발생할 지 추가해도 괜찮을듯.