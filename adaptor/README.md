# Adapter Pattern

서로 호환되지 않는 인터페이스를 가진 클래스들을 함께 동작하게 해주는 구조 패턴. 기존 클래스(Adaptee)의 코드를 수정하지 않고, 클라이언트가 기대하는 인터페이스(Target)로 변환해주는 래퍼(Adapter)를 만든다.

## 구성 요소

| 역할 | 클래스 | 설명 |
|---|---|---|
| Target | `PaymentGateway` | 클라이언트(`OrderService`)가 기대하는 인터페이스 |
| Adaptee | `TossPaymentClient` | 기존/외부 클래스. 인터페이스가 Target과 다름 (수정 불가) |
| Adapter | `TossPaymentAdaptor` | Adaptee를 감싸서 Target 인터페이스로 변환 |
| Client | `OrderService` | Target 인터페이스만 알고 사용 |

## 구조

```
OrderService  --->  PaymentGateway (interface)
                          ^
                          |
                  TossPaymentAdaptor
                          |
                          v
                   TossPaymentClient (기존 외부 SDK)
```

## 코드

**Target** — 클라이언트가 사용할 인터페이스

```kotlin
interface PaymentGateway {
    fun pay(orderId: String, amount: Long): PaymentResult
}
```

**Adaptee** — 수정할 수 없는 외부 PG사 SDK. 파라미터/리턴 타입이 Target과 다르다.

```kotlin
class TossPaymentClient {
    fun requestPayment(orderNo: String, price: Int): TossResponse {
        return TossResponse(resultCode = "0000", tid = "toss_abc123")
    }
}
```

**Adapter** — Adaptee를 합성(composition)하여 Target 인터페이스로 변환

```kotlin
class TossPaymentAdaptor(
    private val tossClient: TossPaymentClient
) : PaymentGateway {

    override fun pay(orderId: String, amount: Long): PaymentResult {
        val response = tossClient.requestPayment(orderId, amount.toInt())
        return PaymentResult(
            success = response.resultCode == "0000",
            transactionId = response.tid
        )
    }
}
```

**Client** — Target 인터페이스만 알고, Adaptee(`TossPaymentClient`)의 존재는 모른다.

```kotlin
class OrderService(private val paymentGateway: PaymentGateway) {
    fun processOrder(orderId: String, amount: Long) {
        val result = paymentGateway.pay(orderId, amount)
        if (result.success) println("결제 성공: ${result.transactionId}")
    }
}
```

## 이 예제에서 Adapter가 변환하는 것

- **메서드 시그니처**: `pay(orderId, amount)` ↔ `requestPayment(orderNo, price)`
- **타입**: `Long amount` → `Int price`
- **응답 형태**: `TossResponse(resultCode, tid)` → `PaymentResult(success, transactionId)` (성공 여부 판단 로직도 Adapter가 캡슐화)

## 장점

- 기존 코드(Adaptee) 수정 없이 재사용 가능
- 클라이언트와 외부 라이브러리 간 결합도 낮춤 (OCP 준수)
- PG사를 교체해도 새 Adapter만 추가하면 됨 (`OrderService`, `PaymentGateway`는 그대로)

## 주의점

- Adapter 안에 변환 로직이 몰리므로, 변환 규칙이 복잡해지면 Adapter 자체가 비대해질 수 있음
- 인터페이스 하나만 감싸는 게 아니라 여러 메서드를 감싸야 하면 Facade 패턴과 혼동하기 쉬움 (Adapter는 "인터페이스 변환"이 목적, Facade는 "복잡한 서브시스템 단순화"가 목적)
