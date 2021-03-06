# readObject 메서드는 방어적으로 작성하라



## 질문

- readObject의 역할
  - 실질적으로 또 다른 public 생성자의 역할
  - 매개변수를 바이트 스트림을 받는 생성자.



## 정리

- 즉 문제는 임의 생성한 바이트 스트리을 readObject로 건네면, 정상적으로는 생성될 수 없는 인스턴스가 생성될 수 있음.
- 그러므로 readObject를 호출할 때 반드시, defaultReadObject를 호출한 다음 역직렬화를 하여, 해당 객체가 유효한지 검사해야함.

- 객체를 역직렬화 할 때는 클라이언트가 소유해서는 안되는 객체 참조를 갖는 필드를 모두 반드시 방어적 복사해야한다.
- readObject 내부에서 항상 방어적 복사를 잊으면 안될듯.
- 직접적이든, 간접적이든 재정의할 수 있는 메서드를 readObject 내부에서 호출하지 말자