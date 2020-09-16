# 생성자 대신 정적 팩터리 메서드를 고려하라
- 생성자 대신에 무조건 정적 팩터리 메서드를 사용하라는 뜻은 아니고, 한번 **고려** 해보라는 의미.
- `static facotry method`란 해당 클래스의 인스턴스를 반환하는 정적 메서드를 의미.

```java
public static Boolean valueOf(boolean b) {
    return b ? Boolean.TRUE : Boolean.FALSE;
}
```

## 장점 1 : 이름을 가질 수 있음
- 여기서 이름이란? 메서드 이름을 말함.
- 단순히 생성자를 통한 객체 생성은, 어떤 객체가 반환되는 지 잘 설명되지 않는다.
- 반면 정적 팩터리는 어떤 객체가 반환될 지, 메서드 이름을 통해 추론 할 수 있도록 만들어 준다.
    - 생성자 : `BigInteger(int, int, Random)` 
    - 정적 팩터리 메서드 : `BigInteger.probablePrime`
        - 이 둘 중에 **값이 소수인 BigInteger**을 반환 한다는 의미를 어떤 것이 더 설명을 잘 하고 있을까?
- 또한, 똑같은 파라미터를 받는 생성자 두 개를 만들 수 없음.
    - 그러므로 이러한 제약을 피하기 위해서는 정적 팩터리 메서드를 사용해보고, 차이를 잘 드러내는 메서드 이름을 짓도록 하자
    
## 장점 2 : 호출 할 때 마다, 인스턴스를 새로 생성하지 않아도 된다
- JVM 동작 과정을 생각해보자.
  1. 기본적인 컴파일 과정
  ![image](https://user-images.githubusercontent.com/22140570/93220401-6daee900-f7a7-11ea-82df-0bea37ef07d3.png)
   - 소스 코드 -> javac를 통해 -> java Bytecode로 변경되고 -> 이를 JVM에 올리는 방식임.
   
  2. JVM 내부 구조
  ![image](https://user-images.githubusercontent.com/22140570/93220657-c5e5eb00-f7a7-11ea-8bf6-8c5fe6bddf12.png)
  - `class loader subsystem` 을 통해, Java Bytecode를 할당된 메모리 공간에 올림.
  - 이때 나뉘어진 공간들은 특정 목적에 의해서 나뉘어져 있음.
  
  3. 정적 필드, 메서드를 담당하는 공간
  ![image](https://user-images.githubusercontent.com/22140570/93220837-fcbc0100-f7a7-11ea-853b-ee8a6cd630da.png)
  - 2번에서 `Method Area` 는 정적 필드, 메서드를 담당함.
  - 즉 클래스 로더가 Java Bytecode를 읽어오면, 해당 클래스 정보를 파싱해서(분석해서), 정적 필드, 메서드들을 `Method Area`에 저장한다.
  - 그래서 해당 클래스 **인스턴스 생성 없이, 바로 사용할 수 있게 된다.**
   
- 불변 클래스(immutable Class)
    - 변하지 않음이 보장된 클래스를 말하는 듯함.
    ```java
        @DisplayName("Boolean은 불변 클래스임을 확인한다.")
        @Test
        void test() {
            Boolean b1 = Boolean.valueOf(true);
            Boolean b2 = Boolean.valueOf("true");
            Boolean b3 = new Boolean(true);
    
            assertThat(b1).isSameAs(b2); //같은 인스턴스 이고,
            assertThat(b1).isNotSameAs(b3); //다른 인스턴스임을 확인할 수 있음.
        }
   ``` 
  - `Boolean.valueOf()` 는 객체를 생성하지 않음.
    - 아마도, 미리 생성해놓은 인스턴스를 캐싱하여 해당 메서드가 실행될 때 마다 재활용하는 듯.
    - `Flyweight pattern` 과 관련 있음.
  - 정적 팩터리 방식은 클래스의 어느 인스턴스가 살아 있게 할 수 있을 지 통제할 수 있음.
    - 이러한 클래스를 인스턴스 통제 (instance-controlled) 클래스라 한다.
## 질문 : 인스턴스를 통제 하는 이유는 무엇인가?
    - 통제함으로써 얻는 이익이 있을 것.
    - 예를 들면, 해당 클래스가 싱글톤 패턴임(singleton pattern)을 보장한다 던가
    - 또는 인스턴스화를 불가하게 만들 수도 있음 (아마도 필요하니까 이렇게 했을듯)
    