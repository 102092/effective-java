# 멤버 클래스는 되도록 static으로 만들라

- 중첩 클래스 nested class는 자신을 감싼 바깥 클래스에서만 쓰여아한다.
- 중첩 클래스 종류
   1. 정적 멤버 클래스
   2. 비정적 멤버 클래스
   3. 익명 클래스
   4. 지역 클래스
   
## 정적 멤버 클래스
```java
public class Calculator {

    public enum Operator {
        PLUS("+", (x, y) -> x + y),
        MINUS("-", (x, y) -> x - y);

        private final String token;
        private final Strategy strategy;

        Operator(String token, Strategy strategy) {
            this.token = token;
            this.strategy = strategy;
        }

        public double operate(double x, double y) {
            return this.strategy.operate(x, y);
        }

        private interface Strategy {
            double operate(double x, double y);
        }
    }

}
```
- Operator enum 클래스는 Calculator의 정적 멤버 클래스
- Calculator.Operator.PLUS 같이 이용가능하다.
- Operator에서 Caculator의 private 변수에 접근 가능하다.

## 비정적 멤버 클래스
```java
public class NestedNonStaticExample {

    private final String name;

    public NestedNonStaticExample(String name) {
        this.name = name;
    }

    public String getName() {
        NonStaticClass nonStaticClass = new NonStaticClass("nonStatic : ");
        return nonStaticClass.getNameWithOuter();
    }

    private class NonStaticClass {
        private final String nonStaticName;

        public NonStaticClass(String nonStaticName) {
            this.nonStaticName = nonStaticName;
        }

        public String getNameWithOuter() {
            // this를 사용해서 바깥 인스턴스를 부르고 있다.
            return nonStaticName + NestedNonStaticExample.this.getName();
        }
    }
}
```
- 비정적 멤버 클래스의 인스턴스는 바깥 클래스 인스턴스와 암묵적으로 연결됨.
- 비정적 멤버 클래스는 바깥 인스턴스 없이, 생성할 수 없음.
- 어댑터 정의할 때 자주 쓰임.
  - 뷰로 사용됨 
  - 비정적 멤버 클래스를 통해 어떤 인스턴스를 감싸서, 마치 다른 클래스의 인스턴스 처럼 보이도록 만들어줌.
- 결론적으로, 어떤 클래스 내부에 있는 클래스가 바깥 인스턴스에 접근할 일이 없다면 반드시 **static**을 붙여 정적 멤버 클래스로 만들어주자.
  - static 생략 시, 바깥 참조를 위한 숨은 외부 참조를 갖게 되고 GC가 바깥 인스턴스를 수거하지 못하게 되어 메모리 누수가 생길 수 있음.

## 익명 클래스
```java
public class AnonymousExample {
    private double x;
    private double y;


    public double operate() {
        Operator operator = new Operator() { //익명 클래스
            @Override
            public double plus() {
                System.out.printf("%f + %f = %f\n", x, y, x + y);
                return x + y;
            }

            @Override
            public double minus() {
                System.out.printf("%f - %f = %f\n", x, y, x - y);
                return x - y;
            }
        };
        
        return operator.plus();
    }
}

interface Operator {
    double plus();

    double minus();
}
```
- 익명 클래스는 바깥 클래스(AnonymousExample) 의 멤버가 아님
  - 왜냐면 사용하는 시점에 인스턴스화 되기 때문.
- 익명클래스는 정적 팩터리 메서드를 구현할 때 많이 쓰임.
- 익명 클래스는 상수 이외, 정적 변수를 가질 수 없음.

## 지역 클래스
```java
public class LocalExample {
    private int number;

    public LocalExample(int number) {
        this.number = number;
    }

    public void foo() {
        class LocalClass { // 지역 클래스
            private String name;

            public LocalClass(String name) {
                this.name = name;
            }

            public void print() {
                System.out.println(number + name);
            }

        }

        LocalClass localClass = new LocalClass("local");

        localClass.print();
    }
}
```
- 정적 멤버 가질 수 없음.
- 이름도 있고, 반복 사용 가능.

## 정리
- 중첩 클래스는 4가지 종류가 있다
  - 정적, 비정적, 익명, 지역 클래스
- 정적, 비정적 구분은 멤버 클래스 바깥의 인스턴스를 참조한다면 --> 비정적 클래스로,
  - 그렇지 않다면 정적으로 만들자.
  - 멤버 클래스 바깥의 인스턴스를 참조하고 있는데 정적 클래스로 만들면 메모리 누수 문제가 발생할수 있음.
- 익명, 지역 클래스 구분은 한 메서드 내에서만 사용되며, 해당 인스턴스가 생성되는 지점이 하나고, 이러한 타입에 사용될 수 있는 클래스 인터페이스가 있다면--> 익명
  - 그렇지 않으면, 
