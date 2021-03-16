# 박싱된 기본 타입 보다는 기본 타입을 사용하라



## 질문

> 박싱된 기본 타입은 값 + 식별성(identity)를 가진다..

- 여기서 말하는 identity는 해당 인스턴스가 올라가있는 메모리 주소값..? 이라고 이해하면 될까요?





## 정리

- 자바의 데이터 타입은 두가지
  1. int, double, boolean, **같은 기본 타입**
  2. String, List 같은 **참조 타입**
- 각각의 기본타입에는 대응하는 **참조 타입**이 있으며 이를 <u>박싱된 기본 타입</u>이라함.
- 기본 타입과 박싱된 기본 타입의 차이점 3가지
  1. 기본 타입은 값만 가지고 있으나, 박싱된 기본 타입은 값 + 식별성(인스턴스의 메모리 주소값..?을 가지고 있다.
  2. 기본 타입의 값은 언제나 유효하나, 박싱된 기본 타입 값은 null을 가질 수 있다.
  3. 기본 타입이, 박싱된 기본 타입보다 시간 메모리 사용면에서 훨씬 효율적이다.
- 박싱된 기본타입을 비교할 때 `==` 연산자를 사용하면 오류가 발생한다.
  - 기본타입과 박싱된 기본타입을 혼용한 연산에서는 박싱된 기본타입의 **박싱이 자동으로 풀린다**
  - 즉 무조건 기본 타입으로 비교하게 만들어준다.
    - 박싱된 기본타입을 언박싱하는 과정속에서 해당 값이 null이라면 nullpoint exception이 발생하게 된다.
- 박싱된 기본 타입은 언제 사용해야할까?
  - 컬렉션의 원소, 키, 값!
  - 왜? 컬렉션은 기본 타입을 담을 수 없다!

- **매개변수 타입**이나 **매개변수 메서드의 타입 매개변수**로는 박싱된 기본타입을 써야한다.