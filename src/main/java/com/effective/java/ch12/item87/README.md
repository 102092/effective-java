# 커스텀 직렬화 형태를 고려해보라



## 질문

- 왜 코드 87-2는 기본 직렬화 형태에 맞는 않는 클래스일까?



## 정리

- 직렬화 되는 클래스에 속하는 멤버 변수들에 대해서 (private) 주석을 달아줘야한다.
  - `@serial`



## 참고

- https://madplay.github.io/post/what-is-readobject-method-and-writeobject-method