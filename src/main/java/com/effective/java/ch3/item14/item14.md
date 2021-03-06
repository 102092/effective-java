# Comparable을 구현할지 고려하라

- `compareTo` 를 구현했다는 것은 해당 클래스의 인스턴스들에게 **순서**가 있다는 것을 의미한다.
  - 즉 인스턴스 간에 동치성을 비교할 수도 있고, 더불어 인스턴스 간 순서도 비교할 수 있다.
- 알파벳, 숫자, 연대 같이 순서가 명확한 클래스를 구현한다면, 반드시 `Comparable` 인터페이스를 구현하자
- 문제점은 기존 클래스(compareTo가 구현된)를 확장한 구체 클래스에서 새로운 컴포넌트가 추가될 경우, 상위 클래스에서 정의된 comparaTo를 만족하지 못하게 됨.
  - 상속을 통한 확장 보다는, 컴포지션(조합)을 사용해서 위의 문제점을 *우회 하자*
- **컬렉션들은 동치성을 비교할 때, equals대신 compareTo를 사용한다**
- Comparable을 구현하지 않는 필드나 표준이 아닌 순서로 비교해야 한다면 Comparator를 대신 사용한다.
- compareTo 메서드는 각 필드가 동치인지를 비교하는 게 아니라, 그 순서를 비교한다.
- compareTo에서 >, < 연산자를 통해 비교하지 말라
- Java 8 : Comparator 인터페이스 등장(약간의 성능저하...)



## compareTo 규약

- A 객체(대상 객체), B객체(비교 객체)가 있음.
  - A < B => 음의 정수
  - A = B => 0
  - A > B => 양의 정수
- A, B가 비교할 수 없는 인스턴스 관계라면 `ClassCastException` 이 발생함.

- 반사성, 대칭성, 추이성을 충족해야한다 *(Equals 규약...)*
- compareTo로 줄지은 인스턴스들의 순서와 equals의 결과가 같아야한다.
  - `(a.compareTo(b) == 0) == (x.equals(y))`

