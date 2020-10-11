# 생성자에 매개변수가 많으면 빌더를 고려하라



## 1. 점층적 생성자 패턴

- 필수 매개변수를 받는 생성자 1개, 그리고 선택매개변수를 하나씩 늘여가며 생성자를 만드는 패턴
- 문제점은  매개변수 개수가 많아지면 클라이언트 코드를 작성하거나 읽기 어려워짐.



## 2. 자바빈즈 패턴(JavaBeans Pattern)

- **매개변수가 없는 생성자**로 객체를 만든 후, setter 메서드들을 호출해 원하는 매개변수의 값을 설정하는 방식.

- Lombok에서도 볼 수 있는 `@setter`. 코드는 길어지지만 인스턴스를 만들기 쉽고, 이전 점층적 생성자 패턴보다 읽기 쉬운 코드가 되었다.

- 그러나, 객체 하나를 만드려면 여러개의 setter 메서드를 호출해야하고, 
- 객체가 완전히 생성되기 전까지는 일관성(consistency)이 무너진 상태에 놓이게 된다. 
  - 해당 객체가 일관적인 값을 갖는다고 보장할 수 없음.
  - 일관성이 무너지는 문제로 클래스를 불변으로 만들 수 없으며, 쓰레드 안전성을 얻으려면 프로그래머가 추가작업이 필요하다(오류 발생 가능성이 높아진다)



## 3. 빌더 패턴(Builder Pattern)

- 클라이언트는 필요한 객체를 직접 만드는 대신, 필수 매개변수만으로 생성자(or 정적팩토리)를 호출해 빌더 객체를 얻는다.
- 그 다음에 setter를 이용해 선택 매개변수들을 설정한다. 마지막으로 매개변수가 없는 build 메서드를 호출해 드디어 우리에게 필요한 (불변) 객체를 얻는다.

- 빌더는 생성할 클래스 안에 정적 멤버 클래스로 만들어 두는 게 보통이다.

- setter 메서드들은 빌더 자신을 반환하기 때문에 연쇄적으로 호출 할 수 있다. 
  - 이런 방식을 플루언트 API, **메서드 연쇄(Method Chaning)** 이라 한다.

  ```java
  NutritionFactsBuilder cola = new NutritionFactsBuilder.Builder(240, 8)
                  .calories(100)
                  .sodium(35)
                  .carbohydrate(27)
                  .build();8)
  ```

- 해당 코드는 쓰기 쉽고, 읽기 쉽다. 빌더 패턴은 명명된 선택전 매개변수(파이썬, 스칼라에 있는)를 흉내 낸 것이다
- 잘못된 매개변수를 일찍 발견하려면 *빌더의 생성자와 메서드에서 입력 매개변수를 검사*하고, build 메서드가 호출하는 *생성자에서 여러 매개변수에 거린 불변식(invariant)을 검사*하자.

- 공격에 대비해 불변식을 보장하려면 빌더로부터 매개변수를 복사한 후 해당 *객체 필드들도 검사*해야한다.



### 빌더 패턴과 계층적 클래스

- 추상 클래스는 추상 빌더를, 구체 클래스(concrete class)는 구체 빌더를 갖게 한다.

```java
public abstract class Pizza {
    public enum Topping {
        HAM, MUSHROOM, ONION, PEPPER, SAUSAGE
    }

    final Set<Topping> toppings;

		// 재귀적인 타입 변수
    abstract static class Builder<T extends Builder<T>> {
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        public T addTopping(Topping topping) {
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }

        abstract Pizza build();

        // 하위 클래스는 이 메서드를 재정의(overriding)하여 this를 반환
        protected abstract T self();
    }

    Pizza(Builder<?> builder) {
        toppings = builder.toppings.clone();
    }
} 
```

- Pizza.Builder 클래스는 **재귀적 타입한정을 이용하는 제네릭 타입**이다. 
  - 여기에 추상 메서드인 self()를 더해 하위 클래스에서는 형변환하지 않고, 메서드 연쇄가 가능하다.



## 정리

- 생성자, 정적팩토리가 처리해야 할 매개변수가 많다면 빌더 패턴을 선택하자.

------



## Q&A

- abstract static class Builder<T extends Builder<T>> 에서 보이는 recursive type parameter에 대해서 간단하게 설명해주시면 감사하겠습니다. 빌더 패턴이 상속 관계에 얽혀있을 때 구현하기 위해서는 기본적인 개념이나 문법을 이해해야 할 듯 하네요.

- A: Recursive type parameter 즉, 재귀적 타입 한정이라고 부릅니다. 재귀적 타입 한정을 이용하는 제네릭 타입을 사용하면 추상메서드 self를 지원하여 하위클래스에서도 형변환 하지 않고도 상위타입에서 구현한 메서드를 연쇄적으로 호출할 수 있다고 합니다.