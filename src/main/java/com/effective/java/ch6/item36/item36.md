# 비트 필드 대신 EnumSet을 사용하라



## 질문

- 비트란?
  - bit, binary digit, 0과 1

- 자바에서 비트 필드는 어떻게 사용되는가?
- 비트 필드를 사용하면 비트별 연산을 사용해 합집합, 교집합 같은 집합 연산을 효율적으로 수행할 수 있다  -> 어떻게? 예시 있으면 좋을듯.

- int vs long 비트 차이
  - 아마도 언어에 따라 다르지 않을까 싶음.
  - java에서는 int 32bit, long 64bit
  - 참고 : https://imasoftwareengineer.tistory.com/48



## 정리

- 비트필드를 직접 사용하는 것 보다는 EnumSet을 사용하는 게 낫다.
  - 코드 이해 (명료함) + 성능까지 보장되므로..

- EnumSet의 단점은 불변으로 못 만드다는 점.
  - 그래서 Collections.unmodifiableSet으로 감싸주면 됨.