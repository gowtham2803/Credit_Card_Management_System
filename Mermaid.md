## 🏗️ System Architecture (Enhanced)

```mermaid
graph TD

%% Layers
subgraph Client
A[User / Browser]
end

subgraph Web Layer
B[Servlet Layer]
C1[PurchaseServlet]
C2[TransactionServlet]
C3[EmiServlet]
C4[BillingServlet]
C5[QRServlet]
C6[LogoutServlet]
end

subgraph Service Layer
D1[TransactionService]
D2[EmiService]
end

subgraph Database
E[(MySQL Database)]
end

%% Flow
A --> B
B --> C1
B --> C2
B --> C3
B --> C4
B --> C5
B --> C6

C1 --> D1
C2 --> D1
C3 --> D2

D1 --> E
D2 --> E

%% Styling
style A fill:#1f77b4,color:#fff
style B fill:#2ca02c,color:#fff
style D1 fill:#ff7f0e,color:#fff
style D2 fill:#ff7f0e,color:#fff
style E fill:#9467bd,color:#fff
```

---

## 🔄 Core Transaction Flow (Enhanced)

```mermaid
sequenceDiagram
    autonumber

    participant U as User
    participant B as Browser
    participant PS as PurchaseServlet
    participant TS as TransactionService
    participant ES as EmiServlet
    participant EMS as EmiService
    participant DB as Database

    U->>B: Make Purchase
    B->>PS: POST /purchase
    PS->>TS: purchase(userId, amount)
    TS->>DB: Save transaction
    DB-->>TS: Success
    TS-->>PS: true
    PS-->>B: Purchase Successful

    U->>B: Convert to EMI
    B->>ES: POST /emi
    ES->>EMS: convert(txId, months, amount)
    EMS->>DB: Update EMI data
    DB-->>EMS: Success
    EMS-->>ES: true
    ES-->>B: EMI Converted Successfully
```

---

## 🔄 QR Generation Flow (Enhanced)

```mermaid
sequenceDiagram
    autonumber

    participant U as User
    participant B as Browser
    participant QS as QRServlet
    participant QR as QRUtil

    U->>B: Request QR (txnId)
    B->>QS: GET /generateQR
    QS->>QR: generateQRCode(qrText)
    QR-->>QS: PNG Stream
    QS-->>B: Display QR Code
```

---

