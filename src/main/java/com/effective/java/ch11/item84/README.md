# 프로그램 동작을 스레드 스케줄러에 기대지 말라



## 질문



## 정리

- 잘 작성된 프로그램은, 운영체제 스케쥴링 정책과는 무관하게 공정하게 작업들을 실행시킬 수 있도록 도와줘야함.
  - 이렇게 하기 위한 가장 좋은 방법은, 실행 가능한 스레드 평균수를 프로세스 수보다 많아지지 않도록 하는 것.
- 스레드 수를 적게 유지하기 위한 방법은..?
  - 스레드가 어떤 작업을 완료한 뒤, 다음 일거리가 생길 때 까지 대기하도록 만드는 것.. -> 아마도 threadpool로 관리하는 것을 의미하지 않을까 생각함.
- 