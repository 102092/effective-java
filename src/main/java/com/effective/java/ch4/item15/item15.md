#  클래스의 멤버의 접근 권한을 최소화 하라

- 모든 클래스, 멤버의 접근성을 가능한 좁혀라

  1. package-private으로 선언하라

     - 패키지 외부에서 쓸 일이 없다면 해당 패키지 내에서만 사용되도록 설정하자.

     ```java
     public interface Shape {
     
         public void draw();
     
     }
     
     class Triangle implements Shape{
     
         public void draw(){ System.out.println("draw triangle"); }
     
     }
     
     class Circle implements Shape{
     
         public void draw(){ System.out.println("draw circle"); }
     
     }
     
     public class ShapeFactory {
     
         public Shape createTriangle(){ return new Triangle(); }
     
         public Shape createCircle(){ return new Circle(); }
     
     } 
     ```

     - 출처:https://effectiveprogramming.tistory.com/entry/package-private-class%EC%97%90-%EB%8C%80%ED%95%9C-unit-test-%EB%B0%A9%EB%B2%95
     - `Shape` : Interface, 외부 노출 API, 
     - `Triangle`, `Circle` :하위 클래스, 외부에 노출 되지 않음 즉 `package-private class`
     - `ShapeFactory` : 외부 외청에 따라, Triagle, Circle 객체를 반환. 그렇지만 Shape로 캐스팅 되어 반환되기 때문에 이용자는 구체적인 타입을 알수가 없다.

- 접근 수준
  1. private
  2. package-private
  3. protected
  4. public

- 코드 테스트를 위해 멤버의 접근범위를 적당히 조절하는 것을 괜찮다

  - ex) private --> package-private
  
- Java 9에서 모듈 추가

  - module은 package의 묶음을 의미한다.
  - Module-info.java 에 어떤 패키지를 export 공개 할 것인지 정할 수 있음.
    - 어떤 패키지 멤버가 protected or public 일 지라도, 모듈에서 해당 패키지를 공개하겠다고 위 파일에 선언하지 않았다면, 외부에서 접근 불가능하다.

## 주의 할점

- public 가변 필드를 가진 클래스는 스레드에 안전하지 않다.
- `public static final` 에 배열 필드를 두거나, 해당 필드를 반환하는 접근자 메서드를 제공해서는 안된다.
  - 왜? 클라이언트가 해당 배열을 수정할 수 있기 때문
  - 즉 불변성을 보장할 수 없어서.
  - 다시 말하하면 `public static final` 에는 반드시 불변하는 객체 혹은 primitive한 값에만 불여줄 수 있음



