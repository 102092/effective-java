# 직렬화된 인스턴스 대신 직렬화 프록시 사용을 검토하라



## 질문



## 정리

- 프록시는 가짜 객체를 의미하는듯.
- 많이 와닿진 않는다. 직렬화를 내가 많이 사용할 것 같진 않음.
- 확장할 수 없는 클래스라면, 직렬화 프록시 패턴을 이용하는게 좋겠음.
  - 이러면 직렬화를 사용함으로써 발생되는 디메리트를 감소시킬 수 있음.
  - 다만 속도적인 측면에서는 좋지 않음.