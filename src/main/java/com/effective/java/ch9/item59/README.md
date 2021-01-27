# 라이브러리를 익히고 사용하라

## 질문



## 정리

- 표준 라이브러를 사용하라!
- `Random` 보다는 `ThreadLocalRandom` 을 사용하라
  - 포크 조인 풀, 병렬 스트림에서는 `SplittableRandom` 을 사용하라
- 적어도 java.lang, java.util, java.io와 그 하위 패키지에 대해서는 익숙해지자 (자바 프로그래머라면!)
- 서드파티 라이브러리
  - 구글 Guava