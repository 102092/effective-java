

# private 생성자나 열거 타입으로 싱글턴임을 보증하라



# 자바의 Serializable란?

- 자바 시스템 내부에서 사용되는 객체 또는 데이터를 외부의 자바 시스템에서도 사용할 수 있도록 바이트(byte) 형태로 테이터 변환하는 기술

  - 바이트로 변환된 데이터를 다시 객체로 변환하는 기술(**역직렬화**)를 통칭하는 말을 의미한다. 

- 동작 원리는 ?

  - 런타임 메모리 영역(힙) 에 상주하고 있는 객체 데이터 -> 바이트 형태 
  - 바이트 형태 -> 객체 데이터 -> 힙에 올림.

- 참고) DB의 Serializable과의 차이점은? 

  - DB의 Serializable은 여러개의 트랜잭션이 동일한 데이터를 접근할 때 트랜잭션의 결과가 마치 하나씩 처리된 것처럼 나오는 격리 수준을 의미
  - 일렬로 하나씩 처리된 것처럼 나오는 현상의 의미이다.
  -  단점은 동시에 여러 트랜잭션을 처리할 수 없으니 성능 이슈가 발생할 수 있으며 쓰기가 불가능하다.

  

## 직렬화 방법 : Serializable을 구현하고 ObjectOutputStream을 사용한다. 

```java
        /*
         * 자바 직렬화 : ObjectOutputStream을 이용한다
         */
        Account account = Account.of("jack", "010-1234-5678", 28);
        byte[] serializedAccount;

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(account);
                serializedAccount = baos.toByteArray();

                // 바이트로 변환된 (직렬화된) 결과를 String으로 인코딩                
                System.out.println(Base64.getEncoder().encodeToString(serializedAccount));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
```



## 직렬화 결과 

![image](https://user-images.githubusercontent.com/55608425/93557643-79242f00-f9b6-11ea-848d-291542c83d89.png)



## 역직렬화 방법 

### 역직렬화 조건
- 직렬화 대상이 된 객체의 클래스가 클래스 패스에 존재해야 하며 import 되어 있어야 한다. 
- 자바 직렬화 대상 객체는 동일한 **serialVersionUID**를 가지고 있어야 한다. 
  - <u>정리하자면 동일한 객체 클래스가 있어야하고, 그 클래스의 버젼이 직렬화 할 때와 같아야 한다..</u>

```java
// seialVersionUID
private static final long serialVersionUID = 1L;
```

```java
        /*
         * 자바 역직렬화 : ObjectInputStream을 이용한다.
         */
        try (ByteArrayInputStream bais = new ByteArrayInputStream(serializedAccount)) {
            try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                Object objectAccount = ois.readObject();
                Account accountFromSerialized = (Account) objectAccount;
                System.out.println(accountFromSerialized);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
```

### 역직렬화 결과 

![image](https://user-images.githubusercontent.com/55608425/93559148-03ba5d80-f9ba-11ea-9e49-4b20d194184e.png)



## 자바 직렬화 방법을 왜 사용하는가? 

- 시스템이 종료되더라도 없어지지 않는 장점이 있고 네트워크로 주고 받을 수도 있다. 
- 우리가 사용하는 스프링에서는 자바 직렬화를 지원하고 있다.
  -  `@RestController` 사용 시 자바 객체를 JSON 형태로 변환해주는데 이 때 해당 객체를 JSON으로 바꾸는 과정에도 직렬화가 일어난다. 



### 자바 직렬화의 장점

- 자바 직렬화는 자바 시스템에서 개발에 최적화되어 있다. 
  - 복잡한 데이터 구조의 클래스라도 직렬화가 쉽게 가능하다. 

### 자바 직렬화의 단점 

- 역직렬화 시 객체 데이터의 구조가 바뀌면 직렬화가 실패할 수 있다. -> 직접 serialVersionUID 값을 관리해주어야 클래스 변경 시 혼란을 줄일 수 있다. 
- 만약 serialVersionUID가 없다면 내부적으로 클래스의 구조정보로 해쉬값이 만들어져서 serialVersionUID로 지정된다. 
- 사용자가 직접 지정하지 않고 클래스 구조가 바뀌면 serialVersionUID가 바뀐다. 
- 직접 serialVersionUID를 관리하더라도 객체 필드의 타입이 런타임 메모리 영역에만 상주하고 있는 객체 데이터를 영속화(Persistence)할 때 필요하다. 




# 싱글턴 직렬화를 위한 Transient와 readResolve() 구현 

## Transient란 
- 일시적인 이라는 뜻.
- 해당 데이터(객체 등)을 직렬화할 때 제외하고 싶은 경우 선언하는 키워드이다. 
- 패스워드와 같은 보안정보가 직렬화되는 것을 막을 때, 선언한다.

### Transient 실습 

- name 필드에 transient 추가 

```java
@Getter
public class Account implements Serializable {

    private transient String name;
    private String phoneNumber;
    private int age;

```

- Account 객체 직렬화 및 역직렬화 진행 

```java
public static void main(String[] args) {

        /*
         * 자바 직렬화 : ObjectOutputStream을 이용한다
         */
        Account account = Account.of("jack", "010-7720-7954", 28);
        byte[] serializedAccount = new byte[1000];

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(account);
                serializedAccount = baos.toByteArray();

                // 바이트로 변환된 (직렬화된) 결과를 String으로 인코딩
                System.out.println(Base64.getEncoder().encodeToString(serializedAccount));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * 자바 역직렬화 : ObjectInputStream을 이용한다.
         */
        try (ByteArrayInputStream bais = new ByteArrayInputStream(serializedAccount)) {
            try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                Object objectAccount = ois.readObject();
                Account accountFromSerialized = (Account) objectAccount;
                System.out.println(accountFromSerialized);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
```

## 역직렬화 결과 
- transient 필드가 null로 역직렬화된다. 

![image](https://user-images.githubusercontent.com/55608425/93560291-c99e8b00-f9bc-11ea-8b45-80b5e367fd39.png)



## readResolve()

- 싱글턴으로 구현한 객체를 역직렬화할 때 다른 인스턴스가 반환되는 것을 막는 기능이다. 
- 만약 readResolve()를 싱글턴 객체에 구현하지 않으면 싱글턴이라도 역직렬화할 때 다른 인스턴스가 반환된다. 



### readResolve() 실습
- 싱글턴 객체 생성 

```java
public class JackSingleton implements Serializable {
    private static final JackSingleton INSTANCE = new JackSingleton();

    public JackSingleton() {
    }
    
    public static JackSingleton getInstance() {
        return INSTANCE;
    }
}
```

- Serializer 클래스 구현 

```java
package com.titanic.javatest.serializable;

import java.io.*;
import java.util.Base64;

public class Serializer {

    public String serialize(Object object) {
        /*
         * 자바 직렬화 : ObjectOutputStream을 이용한다
         */
        byte[] serialized;

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(object);
                serialized = baos.toByteArray();

                // 바이트로 변환된 (직렬화된) 결과를 String으로 인코딩
                return Base64.getEncoder().encodeToString(serialized);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public Object deserialize(String serialized) {
        /*
         * 자바 역직렬화 : ObjectInputStream을 이용한다.
         */
        byte[] serializedSingleton = Base64.getDecoder().decode(serialized);
        try (ByteArrayInputStream bais = new ByteArrayInputStream(serializedSingleton)) {
            try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                Object objectAccount = ois.readObject();
                return objectAccount;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}

```

- readResolveTest 구현

```java
package com.titanic.javatest.serializable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class readResolveTest {

    private JackSingleton jackSingleton;
    private Serializer serializer;

    @BeforeEach
    void setUp() {
        this.jackSingleton = JackSingleton.getInstance();
        this.serializer = new Serializer();
    }

    @Test
    void 일반직렬화를_테스트한다() throws Exception {
        // when
        String serialized = serializer.serialize(jackSingleton);
        JackSingleton deserialized = (JackSingleton) serializer.deserialize(serialized);

        // then
        assertThat(deserialized).isSameAs(jackSingleton);
        assertThat(deserialized).isEqualTo(jackSingleton);
    }

    @Test
    void readResolve를_테스트한다() throws Exception {
        // when
        String serialized = serializer.serialize(jackSingleton);
        JackSingleton deserialized = (JackSingleton) serializer.deserialize(serialized);

        // then
        assertThat(deserialized).isSameAs(jackSingleton);
        assertThat(deserialized).isEqualTo(jackSingleton);
    }
}
```

- readResove() 구현한 싱글톤 객체

```java
package com.titanic.javatest.serializable;

import java.io.Serializable;

public class JackSingleton implements Serializable {
    private static final JackSingleton INSTANCE = new JackSingleton();

    public JackSingleton() {
    }

    public static JackSingleton getInstance() {
        return INSTANCE;
    }

    private Object readResolve() {
        return INSTANCE;
    }
}

```

### readResolve()를 구현하지 않는 결과

- 싱글턴 객체를 역직렬화하면 싱글턴 인스턴스와 다르다고 나온다.
![image](https://user-images.githubusercontent.com/55608425/93565513-76313a80-f9c6-11ea-964e-9b4ce347655f.png)

- readResolve()를 구현한 결과 : 테스트를 통과한다 
![image](https://user-images.githubusercontent.com/55608425/93565832-f5bf0980-f9c6-11ea-83b7-148b71e8fae8.png)






# 리플랙션 (Reflection)

- 반사, 투영이라는 사전적인 의미를 가지고 있는데 객체(인스턴스)를 통해 클래스의 정보를 분석해 내는 프로그램 기법을 의미한다. 

- 리플랙션은 자바 프로그램이 자기 자신(객체)이나 프로그램 내 속성을 조사,분석하는데 도와주는 기술이다. 

- 예를 들어, 자바 클래스가 클래스 내 멤버 변수에 대한 정보와 해당 멤버 변수를 display(표현?)하게 도와준다. 

- 실용적인 내용으로는 이미 로딩이 완료된 클래스에서 또 다른 클래스를 동적으로 로딩(Dynamic Loading)하여 생성자, 멤버 필드, 멤버 메서드 등을 사용할 수 있게 도와준다. 

  

## 왜 사용할까? 
- 동적로딩에 대한 기술을 지원하니 자바 프로그램을 유연하게 운영할 수 있다. (다형성 등) 



## 주의할 점 
- 외부에 공개되지 않는 private 멤버도 접근과 조작도 가능하니 주의해야 한다. 
- private 멤버는 Field.setAccessible() 메서드를 true로 지정하면 접근이 가능하다고 한다. 

