# 필요 없는 검사 예외 사용은 피하라



## 질문



## 정리

- 검사 예외를 던지는 메서드는 스트림 안에서 직접 사용할 수 없다.
- 검사 예외를 던지는 대신 optional을 사용할 수도 있지만, 이 방법의 경우 어떤 에러 메세지를 담지 못한다는 단점이 있음.