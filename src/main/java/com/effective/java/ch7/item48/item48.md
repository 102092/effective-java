# 스트림 병렬화는 주의해서 적용하라



## 질문

## 정리

- 동시성 프로그래밍에서는 안전성, 응답 가능 상태가 중요
- 스트림은 ArrayList, HashMap, HashSet, ConcurrentHashMap의 인스턴스 이거나 int 범위, long 범위 일때 병렬화 효과가 가장 좋음
  - 왜? 이 자료구조들은 참조 지역성이 뛰어남
  - 참조 지역성이란 이웃한 원소의 참조가 실제 메모리에 연속해서 저장되어 있다는 뜻.
- 스트림 종단연산 중 병렬화에 가장 적합한 것은 축소 (reduction)
- 병렬화는 프로그램 오작동, 성능 하락의 원인 중 하나
- 병렬화로 수정한 코드는 성능지표를 반드시 확인 후, 이 전 코드보다 성능이 좋아졌을 경우에만 사용하라



