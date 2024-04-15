# 대학축제 새로운 만남을 이어주는 서비스 Distance README

## 팀원 구성
<div align="center">

<div align="center">

| **이준석** |
| :------: | 
| [<img src="https://github.com/CafeCheckin/CafeCheckin/assets/56196986/422a81d3-b0b7-4b85-af31-a42a3c23c771" height=150 width=150> <br/> @JunRock](https://github.com/JunRock) |

</div>
</div>
<br>

## 개발 환경
- BACKEND : Spring Framework, Java17, Mysql, Spring Data Jpa, AWS ec2, Docker, Docker-compose, CI/CD, Nginx, STOMP   <br>
- 버전 및 이슈관리 : Github, Github actions   <br>

## 주제
- 대학 축제 떄 새로운 만남을 원하는 사람들을 위해 이어주는 서비스
  
## 요구사항
- 대학생을 페르소나로 하기 때문에 사용자는 대학생 인증을 받아야한다.
- 익명을 보장하는 서비스이기 때문에 사용자는 프로필 설정을 통해 본인을 나타낼 수 있어야한다.
- 사용자는 3개의 채팅방만 소유할 수 있어야한다.
- 채팅 요청을 받은 사용자가 3개 이상의 채팅방을 소유하고 있는 경우 요청을 한 사용자는 요청한 사용자의 요청리스트에 들어가야한다.
- 채팅 요청을 받은 사용자는 요청함에 가서 수락 혹은 거절을 할 수 있어야한다.
- 나의 거리에서 200m반경 안에 있는 사람들만 추천에 뜰 수 있어야한다.
- 대학생 인증을 하지 않은 사용자는 채팅 서비스를 이용할 수 없다.
- 채팅방에서 서로 왔다갔다 10번 대화를 하게 되면 등록한 번호가 노출이 되어야한다.
- 채팅 중 불쾌한 말을 하는 사용자에게 신고를 할 수 있어야한다.
- 신고를 3번 이상 받은 경우 해당 사용자는 서비스를 이용할 수 없아야한다.
- 관리자는 사용자가 보낸 학생증 사진을 확인 후 승인을 해줘야한다.

## 참고사항 및 조건
- 
- 

## 화면 구성 📺
|  메인 페이지  |  응원 메시지 목록 페이지   |
| :-------------------------------------------: | :------------: |
|  <img width="500" src="https://github.com/project-GAZA/GAZA-server/assets/56196986/28f11e9d-d354-469f-8303-3d89b7731940"/> |  <img width="500" src="https://github.com/project-GAZA/GAZA-server/assets/56196986/6a7e6e5f-3c36-4dd3-ba6c-67a6ee7c051f">|  
| 응원메시지 카테고리 선택 페이지   |  응원 메시지 작성 페이지   |  
| <img width="500" src="https://github.com/project-GAZA/GAZA-server/assets/56196986/2ba63dfa-553e-46d7-87b7-1813ed74aba5"/>   |  <img width="500" src="https://github.com/project-GAZA/GAZA-server/assets/56196986/0750b7d6-03d5-42bd-a074-c2a45b9d12f2"/>     |
|  후원 페이지   |  
| <img width="500" src="https://github.com/project-GAZA/GAZA-server/assets/56196986/44176d77-21ea-4b69-ad82-750a4a864256"/>     |

---

## DB구조도
<img width="866" alt="image" src="https://github.com/project-GAZA/GAZA-server/assets/56196986/99048974-4315-4fd1-abf2-b8ee1ae6283f">

---

## System Architecture
![image](https://github.com/CafeCheckin/CafeCheckin/assets/56196986/9bca1262-770e-4f70-ac78-47b53a27997b)

---

## 기능정리
1. 메시지 작성
2. 동일한 IP당 하나의 메시지에 하나의 좋아요만 가능
3. 토스를 통한 후원 기능
4. 페이징 처리
