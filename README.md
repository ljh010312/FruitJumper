# Fruit Jumper

> **2D 플랫폼 게임**  

---

## 게임 컨셉 (High Concept)

- 정령 캐릭터가 다양한 장애물과 적을 피해 점프하며 이동하고,  
  필수 과일을 수집해 체크포인트까지 도달하는 것이 목표인 게임입니다.
  
- **핵심 메카닉**
  - 점프 및 방향 이동
  - 충돌 판정 (플랫폼, 적, 과일)
  - 과일 수집 및 체크포인트 통과
  - 체력 시스템
 
---

## 개발 범위 

| 요소              | 수치 또는 범위 |
|------------------|----------------|
| 플레이어 캐릭터    | 1종 (애니메이션 포함) |
| 몬스터/장애물     | 3종 (기본 몬스터, 이동 장애물, 낙하형 장애물) |
| 과일 아이템        | 4종 (사과, 바나나, 키위, 체리) |
| 스테이지          | 2종 (스크롤형, 적 배치 포함) |
| UI 구성 요소       | 점수, 체력, 과일 종류 |
| 스프라이트 애니메이션 | 4종 (기본, 이동, 점프, 피격) |

---

##  예상 게임 실행 흐름

### 1. 시작 화면
- 게임 제목, 시작 버튼 표시
- 게임 설명 간단하게 안내

### 2. 게임 진행 화면
- 캐릭터는 좌/우 터치로 방향 전환
- 화면에 몬스터와 장애물이 등장
- 과일을 점프하여 수집

### 3. 체크포인트 도달 시
- 일정 과일을 모은 경우, 체크포인트 진입 가능
- 성공 메시지 표시

### 4. 실패 조건
- 체력 0 or 낙하 
  
### 5. 결과 화면
- 실패 화면 or 성공 화면, 재시작 버튼 표시
  
---

##  게임 화면 예시
![게임 진행상황](https://github.com/user-attachments/assets/92663505-6339-47da-a599-a3bee2d19efc)


---

## 🗓️ 개발 일정

| 주차 | 기간           | 개발 내용 |
|------|----------------|-----------|
| 1주차 | 4/8 ~ 4/14     | 기본 구조 설계 |
| 2주차 | 4/15 ~ 4/21    | 플레이어/오브젝트 이동 구현 |
| 3주차 | 4/22 ~ 4/28    | 충돌 판정, 플랫폼 및 장애물 구현 |
| 4주차 | 4/29 ~ 5/5     | 과일 수집 시스템, 점수/체력 UI 구성 |
| 5주차 | 5/6 ~ 5/12     | 체크포인트 및 클리어 조건 구현, 배경 스크롤 |
| 6주차 | 5/13 ~ 5/19    | 적 패턴 및 피격 처리, 사운드 추가 |
| 7주차 | 5/20 ~ 5/26    | 전체 통합, 버그 수정, 최종 완성 |
| 8주차 | 5/27 ~ 6/2     | 발표 영상 촬영 및 README 정리, 최종 제출 |

