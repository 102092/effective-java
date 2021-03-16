# Serializable을 구현할지는 신중히 결정하라



## 질문

- p457... 인스턴스 필드 값 중 불변식을 보장해야 할 게 있다면, 반드시 하위 클래스에서 finalize 메서드를 재정의하지 못하게 해야한다..는 어떤 걸 의미하는 걸까?
- readObjectNoData 메서드의 역할은 어떤 것일까?

## 정리

- Serializable을 구현하면, 릴리즈 한 뒤에 수정하기가 힘들다.
  - 즉 하나의 공개된 API가 되므로, 해당 클래스를 수정하기가 힘들다 (캡슐화가 깨진다..)
- 직렬 버젼 UID (serial version UID)
  - 고유 식별 번호
  - 명시 하지 않았을 경우, 컴파일러가 알아서 생성해서 넣어줌.
- 직렬화를 하면, 생성자를 통해 객체를 생성하지 않고도 객체를 생성할 수 있게 해준다.
  - 위험
  - 왜? 혹시 모를 공격자가 해당 클래스 내부를 들여다 볼 수 있게 되고...