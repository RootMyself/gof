# Template Method 패턴

## 개념

알고리즘의 **골격(뼈대)** 은 상위 클래스에 고정하고, 일부 단계만 하위 클래스에서 재정의하게 하는 행동(Behavioral) 패턴. 실행 순서는 상위 클래스가 통제하고, 각 단계의 구체적 구현만 하위 클래스에 위임한다 (Hollywood 원칙: "Don't call us, we'll call you").

## 구조

| 역할 | 설명 | 구현 파일 |
|---|---|---|
| **AbstractClass** | 템플릿 메소드(`display`)로 알고리즘 순서 고정, 하위 단계는 abstract로 선언 | `DisplayArticleTemplate.kt` |
| **ConcreteClass** | 각 단계(`title`, `content`, `footer`)를 실제로 구현 | `SimpleDisplayArticle.kt`, `CaptionDisplayArticle.kt` |
| (데이터) | 패턴 참여자는 아니고 출력 대상 데이터 보유 | `Article.kt` |

## 왜 쓰는가

- **알고리즘 구조 재사용**: 순서/흐름은 한 곳(`display()`)에만 정의, 중복 제거
- **일부만 변경 허용**: 서브클래스는 정해진 단계만 오버라이드, 전체 흐름은 못 건드림
- **역제어(IoC)**: 상위 클래스가 하위 클래스 메소드를 호출하는 구조 — 프레임워크 설계의 기본 형태
- **점진적 확장**: 새 표시 방식 추가 시 `DisplayArticleTemplate`을 상속한 클래스만 추가하면 됨

## 실무 사용

- **프레임워크 라이프사이클**: `Activity.onCreate → onStart → onResume` 같은 콜백 체계
- **테스트 프레임워크**: JUnit의 `setUp → test → tearDown` 실행 순서
- **데이터 처리 파이프라인**: 공통 흐름(읽기→검증→변환→저장) 고정, 단계별 로직만 교체
- **HttpServlet**: `service()`가 골격, `doGet`/`doPost`는 하위 클래스가 구현

## 주의점

- 단계 수가 많아지면 상속 계층이 비대해짐 — 조합(Strategy)으로 대체 검토 필요
- 하위 클래스가 반드시 구현해야 하는 단계와 선택적으로 재정의하는 훅(hook)을 구분해서 설계할 것 (본 예제는 훅 없이 전부 abstract)
- 템플릿 메소드는 `final`(코틀린은 기본 `open` 아님)로 막아야 하위 클래스가 흐름 자체를 깨지 못함
