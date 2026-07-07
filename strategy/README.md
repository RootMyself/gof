# Strategy 패턴

## 개념

알고리즘군을 각각 캡슐화해 **런타임에 교체 가능**하게 만드는 행동(Behavioral) 패턴. 핵심은 "어떻게 계산할지"를 사용하는 쪽(Context)과 분리해 별도 객체(Strategy)에 위임하는 것.

## 구조

| 역할 | 설명 | 구현 파일 |
|---|---|---|
| **Strategy** | 알고리즘 공통 인터페이스 (`sum`) | `SumStrategy.kt` |
| **ConcreteStrategy** | 실제 알고리즘 구현 | `SimpleSumStrategy.kt`, `GaussSumStrategy.kt` |
| **Context** | Strategy를 받아 실행을 위임 | `SumPrinter.kt` |

## 왜 쓰는가

- **알고리즘 교체 용이**: `if/switch` 없이 객체만 바꿔 끼우면 동작 변경
- **개방-폐쇄 원칙**: 새 알고리즘 추가 시 기존 Context 코드 수정 불필요
- **책임 분리**: Context는 실행 흐름만, Strategy는 계산 로직만 담당
- **테스트 용이**: 알고리즘 단위로 독립 테스트 가능

## 실무 사용

- **정렬/비교 전략**: `Comparator` 구현체를 갈아 끼워 정렬 기준 변경
- **결제/할인 정책**: 카드사·쿠폰별 계산 로직을 전략 객체로 분리
- **인증 방식**: OAuth, JWT, Session 등 인증 전략 교체
- **압축/직렬화**: JSON, Protobuf 등 포맷별 전략 주입
- DI 컨테이너(Spring 등)에서 인터페이스 타입으로 주입받는 방식 자체가 Strategy 패턴의 실전 형태

## 주의점

- 전략이 많아지면 클래스 수 증가 — 단순 조건 분기 몇 개면 과설계일 수 있음
- 전략 객체가 상태를 안 가지면 싱글턴/object로 재사용해 인스턴스 낭비 방지
- Context와 Strategy 간 데이터 전달 인터페이스가 비대해지지 않도록 주의
