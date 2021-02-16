# 복구할 수 있는 상황에는 검사 예외를, 프로그래밍 오류에는 런타임 예외를 사용하라



## 질문

- 검사 예외란 정확히 무엇을 의미하는 걸까?
  - 아마도 checked exception을 의미할듯.
  - 즉 IOException... 예외 처리를 필수적으로 해야하는 예외들
  - Unchecked exception은 예외처리가 필수가 아니며, 컴파일 타임에 체크되지 않고 런타임에 체크 되는 것들을 의미.
    - RuntimeException, NullPointerException..



## 정리

- 런타임 예외와, 에러는 프로그램에서 잡을 필요가 없거나, 잡지 말아야한다.
- 에러는 JVM 자원 부족, 불변식 깨짐등 더 이상 수행을 계속할 수 없을 때 발생
  - 시스템 레벨에서 발생..



## 참고

- https://sjh836.tistory.com/122