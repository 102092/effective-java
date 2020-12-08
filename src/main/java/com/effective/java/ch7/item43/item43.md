## 람다보다는 메서드 참조를 사용하라

## 질문



## 정리

- 메서드 참조 : `sevice.execute(GoshThisClassNameIsHumongous::action)`
- 람다 : `service.execute(() -> action());`

- 어떨때는 람다가 메서드 참조보다 간결,명확할 때도 있다 -> 그럴땐 람다를 쓰자.