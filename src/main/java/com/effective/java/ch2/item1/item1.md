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



## 장점 3 : 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다.

- java.util.Collections의 유틸리티 클래스에서 45개의 구현체를 정적 팩터리 메서드로 제공하고 있음.

- 이는 45개의 클래스를 만들지 않기 때문에, 훨씬 API 가 작게 보이고, 사용자가 알아야할 부분도 적어졌다고 볼 수 있음.

- 자바 8 이전에는 인터페이스에서 정적 메서드를 선언할 수 없어서, 동반 클래스를 만들어서 내부에 인터페이스를 반환하는 정적 메서드를 정의했다.

  ```java
  public interface Animal {
  
      String Howling();
  }
  
  public class Dog {
  
      private Dog() { }
  
      public static Animal howling() {
          return new Animal() {
              @Override
              public String Howling() {
                  return "bark! bark";
              }
          };
      }
  }
  
  ```

  

## 장점 4 : 입력 매개 변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.

```java
    public static <E extends Enum<E>> EnumSet<E> noneOf(Class<E> elementType) {
        Enum<?>[] universe = getUniverse(elementType);
        if (universe == null)
            throw new ClassCastException(elementType + " not an enum");

        if (universe.length <= 64) //매개변수로 받은 것의 길이가 64개 이하면, 
            return new RegularEnumSet<>(elementType, universe);
        else //그 이상이면
            return new JumboEnumSet<>(elementType, universe);
    }
```

- 클라이언트(사용자)는 위의 팩터리 메서드가 건네주는 객체가 어느 클래스의 인스턴스인지 알 필요 없음.
- 단지 EnumSet<> 의 하위 타입이라는 것만 충족하면 됨.
- 클라이언트는 최소한의 정보를 통해서 해당 API를 이용할 수 있는 것.



## 장점 5 : 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.

- 목적은 유연을 위해서

- 대표적인 예로, 프레임워크인  JDBC(Java Database Connectity) 가 있음.

  - Conntion : 서비스 인터페이스 (어떻게 연결하게 도와줄 것인가에 대한 명세서)
  - DriverManager.registerDriver : 제공자 등록 API
  - DriverManager.getConnection : 서비스 접근 API
  - Driver : 서비스 제공자 인터페이스 역할

- 서비스 제공자 프레임워크는 3개의 핵심 컴포넌트로 이뤄짐.

  1. 서비스 인터페이스(service interface) : 구현체의 동작을 정의함.
  2. 제공자 등록 API(provider registration API) : 제공자가 구현체를 등록할 때 사용함.
  3. 서비스 접근 API(service access API) : 클라이언트가 서비스의 인스턴스를 얻을 때 사용하는 API

  - 제공자는 mysql등의 DATABASE 쪽인 것 같고, 클라이언트가 사용자, 서비스 인터페이스가 어떻게 작동할 지에 대한 명세서 같은 느낌.

- 클라이언트는 서비스 접근 API를 사용할 때, 원하는 구현체 조건(mysql, oracle..)등을 명시할 수 있음.

  - 이렇게 유연하게 구현체를 제공해줄 수 있다는 것
  - 이것이 프레임워크의 근간 --> **유연한 정적 팩터리**

- 3개의 핵심 컴포넌트 + 1개 더있음.
  - 서비스 제공자 인터페이스(service provider interface)
    - 이 컴포넌트는 서비스 인터페이스의 인스턴스를 생성하는 팩터리 객체를 설명해줌.

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FeRIU2A%2FbtqByGLH1XV%2FapMOWz2gKTm0r5WfiIYBSk%2Fimg.png)

>  참고 : https://javabom.tistory.com/1

- java.sql.Driver라는 인터페이스는, 서비스를 제공하는 입장에서 정의된 인터페이스
- com.mysql.cj.jdbc.Driver는 서비스 제공자 인터페이스를 기반으로 구현되었다.
- Driver를 통해 어떤 서비스 제공자의 인터페이스를 사용할 것인지 결정하여 생성하고,
- 이를 매니저에 등록한다 (제공자 등록 API)
- 그러면 드라이버 매니저를 통해서 커넥션을 가져오고 (서비스 접근 API), 이제 해당 서비스에 접근할 수 있는 커넥션이 만들어졌다.
- 그러한 커넥션을 기반으로 statement 뭐가 선언을 하고(여기에 쿼리를 쓸꺼다)
- 쿼리를 실행하여, 그 결과를 ResultSet에 받는 과정인듯.





## 단점 1 : 하위클래스를 만들 수 없다.

- 상속을 하려면, public 이나 proteced 생성자가 필요하니까 상속할 수 없음.



## 단점 2 : 정적 팩터리 메서드는 찾기 힘들다.

- 코드의 향연에서, API 설명이 없으면 어떻게 작동하는지 알기 힘듬.
- 문서화를 잘해줘야한다.



## 명명 방식

- from : 매개변수를 받아서, 해당 타입이 인스턴스를 반환할 때
  - `Date d = Date.from(...);`
- of : 여러 개의 매개변수를 받아, 적합한 타입의 인스턴스를 반환 할 때
  - `Set<Rank> faceCards = EnumSet.of(JACK, QUEEN, KING)`
- valueOf : from, of의 더 자세한 버젼

- Instance or getInstance : 매개변수로 명시한 인스턴스를 반환하지만, **같은 인스턴스 임이 보장되진 않는다.**
- create or newInstance : 위와 비슷하지만, **매번 새로운 인스턴스를 생성하여 반환됨이 보장된다.**
- getType : getInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할때 사용, 여기서 `Type` 부분은 반환될 객체 타입으로 명시해줘야함.
  - `FileStore fs = Files.getFileStore(path)` .. FIleStore로 명시하였음.
- newType : newInstance와 같으니, 생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할때 사용, 여기서 `Type` 부분은 반환될 객체 타입으로 명시해줘야함.
  - `BufferedReader br = Files.newBufferdReader(path)`
- type : getType, newType의 간결한 버젼
  - `List<Complaint> litany = Collections.list(legacyLitany)`

## 정리

- 정적 팩터리 메서드는 생성자에 비해서 장단점이 있음.
- 그렇지만 정적 팩터리 메서드를 사용하는 게 유리한 경우가 많으니, 무작정 생성자를 사용하는 것은 지양하자.

