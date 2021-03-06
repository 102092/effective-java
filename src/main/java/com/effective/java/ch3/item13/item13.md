# Clone 재정의는 주의해서 진행해라

- `Cloneable`, 복제해도 되는 클래스임을 명시하는 인터페이스

- clone 메서드의 일반 규약은 생각보다 허술삼.

  1. `x.clone() != x` 
  2. `x.clone().getClasss() == x.getClass()`
  3. `x.clone().equals(x)`

  - 이 식들이 **일반적으로 참이지만, 필수는 아님.**

- 불변 클래스는 clone 메서드를 제공하지 않는게 좋다
  - 왜? 
  - 쓸데 없는 복사를 지양해야함.

- 일반적으로 권장되는 clone() 메서드

  ```java
  public class App implements Cloneable {
  
    private int a;
    private int b;
  
  
    public App(int a, int b) {
      this.a = a;
      this.b = b;
    }
  
    @Override
    public App clone() throws CloneNotSupportedException {
      return (App) super.clone();
    }
  ```

- clone() 메서드는 사실상 생성자와 같은 효과를 내야한다.

- clone() 메서드를 통해 생성된 객체의 멤버변수를 수정했을 때, 원본 객체에 아무런 영향을 주지 않는 동시에 복제된 객체의 불변식을 보장해야한다.

- 배열을 복제할 때 배열의 clone() 메서드를 사용하라 (권장사항)
  
- clone() 메서드를 제대로 사용하고 있는 **유일한 예**
  
- 해쉬테이블용 clone 메서드

  - 각 배열 버킷 내부에 있는 연결리스트도 새롭게 copy (deep copy) 해줘야, 복제 객체를 수정시에 원치 않은 결과 도출을 막을 수 있다.

- 재귀 호출을 사용할 때는 항상 stackOverFlow를 조심해야한다.

  - 차라리 for문을 사용하자.

- 상속용 클래스는 Cloneable을 구현해서는 안된다.

- Cloneable을 구현한 스레드 안전 클래스를 작성할 때는 clone 메서드 역시 적절히 동기화 해줘야한다.



## 정리

- Clonealbe을 구현하는 모든 클래스는 clone을 재정의해야하는 데, 접근 제한자는 public, 반환 타입은 클래스로 정의.
- 이 때clone() 메서드는 deep copy를 의미한다.
- clone() 메서드를 잘 구현할 수 없다면, **복사 생성자와 복사 팩터리** 를 이용하자



## 핵심

- 새로운 인터페이스를 만들 때는 절대로 Cloneable을 확장하지 말아라
  - 새로운 클래스도 이를 구현하지 말자
- final 클래스라면 Cloneable을 구현해도 괜찮음.
- 그렇지만 기본적으로 복제기능은 생성자, 팩터리를 이용하라
- 단 배열만 clone 메서드를 사용할 수 있는 가장 깔끔한 방법이다.