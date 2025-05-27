# Benford Analysis API

A Ktor-based REST API that analyzes the distribution of leading digits in numerical data using **Benford's Law** and performs a **Chi-square test** to assess conformity.

---

##  Features

- Extracts numbers from free-form text input
- Calculates first-digit distribution
- Applies Benford’s Law to compute expected digit frequencies
- Performs a Chi-square test with configurable significance level
- Returns:
    - Expected vs. actual digit distributions
    - Chi-square statistic
    - p-value
    - Pass/fail flag
- Input string length is configurable and currently set to 10,000


---
## 🧾 Assumptions

- The system extracts **numeric values** by removing currency symbols and punctuation, and collapsing numbers into digit sequences:
  - Example: `"$123.45"` becomes `12345`
- Both **positive and negative numbers** are accepted — negative signs are ignored for digit analysis.
- Only values with **at least one non-zero digit** are considered (e.g., `0.00`, `0`, or `"£0.99"` → discarded).
- **Leading zeros** invalidate the number:
  - Example: `"0123"` → ignored
- **Embedded numbers** in words or identifiers are detected:
  - Example: `"Invoice$123.45Refund€56.78"` → `12345`, `5678`
- The extractor:
  - Ignores symbols like `$`, `€`, `£`, and `,`
  - Keeps only digits
  - Ignores textual labels (e.g., `"RefID:ABC123"` extracts `123`)

| Input String                              | Extracted Values      |
|-------------------------------------------|-----------------------|
| `$123.45, €56.78, £0.99`                  | `[12345, 5678]`       |
| `-$1,234.56 -€8,765.43`                   | `[123456, 876543]`    |
| `0123 001 099 £0.99`                      | `[]`                  |
| `100 200.50 3000`                         | `[100, 20050, 3000]`  |
| `0 0.50 3000`                             | `[3000]`              |
| `-100 -200.50 -3000`                      | `[100, 20050, 3000]`  |
| `Invoice$123.45Refund€56.78Balance9999`   | `[12345, 5678, 9999]` |
| `RefID:ABC123, Balance: 42, Total: $0.00` | `[123, 42]`           |


- The significanceLevel in the request must be a float between 0 and 1 (e.g., 0.05 for 5%).
## Build Instructions

### Prerequisites

- Java 21
- Maven 3.9+
- Docker (optional for containerization)

### Build the JAR

```bash
mvn clean package
```

### Run locally
```bash
 java -jar target/benford-analyze.jar config/config.yml
```
The API will start on http://localhost:8080.

### Docker
Build Docker image
```bash
docker build -f .docker/Dockerfile -t benford-api .
```
Run container
```bash
docker run -p 8080:8080 benford-api
```
### API Usage
POST /analyze
Request Body
```json
{
  "input": "Population data: 123, 2345, 34567, 456789",
  "significanceLevel": 0.05
}
```
Response
```json
{
  "expectedDistribution": { "1": 0.301, "2": 0.176, ... },
  "actualDistribution": { "1": 2, "2": 1, ... },
  "chiSquareValue": 5.678,
  "pValue": 0.058,
  "passed": true,
  "significanceLevel": 0.05
}
```