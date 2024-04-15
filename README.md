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
- 나의 거리에서 200m 반경 안에 있는 사람들만 추천에 뜰 수 있어야한다.
- 대학생 인증을 하지 않은 사용자는 채팅 서비스를 이용할 수 없다.
- 채팅방에서 서로 왔다갔다 10번 대화를 하게 되면 등록한 번호가 노출이 되어야한다.
- 채팅 중 불쾌한 말을 하는 사용자에게 신고를 할 수 있어야한다.
- 신고를 3번 이상 받은 경우 해당 사용자는 서비스를 이용할 수 없아야한다.
- 관리자는 사용자가 보낸 학생증 사진을 확인 후 승인을 해줘야한다.

## 참고사항 및 조건
- 위치 기반 서비스 : 이 서비스는 위치 기반 기술을 활용하여 사용자의 현재 위치에서 200m 이내에 있는 다른 사용자를 추천합니다. 위치 데이터는 실시간으로 처리되어야 하며, 사용자의 위치 정보는 해당 서비스 이용 목적에만 사용되어야한다.
- 대학생 인증 프로세스 : 사용자가 대학생임을 인증하는 과정은 간편하면서도 철저해야 한다.
- 유저 인터페이스 및 경험 : 대학생 사용자를 타겟으로 하기 때문에 인터페이스는 직관적이고 사용하기 쉬워야하고, 모바일 중심의 설계로 접근성을 높이고, 젊은 사용자들에게 친숙한 디자인 요소를 사용해야한다.
- 사용자 행동 관리 : 부적절한 행동을 하는 사용자에 대한 신고 시스템을 구축하여, 서비스의 건전성을 유지해야하고, 신고 접수 후 신속한 검토 및 조치가 이루어질 수 있도록 관리 시스템을 갖추어야한다.
- 규제 및 법적 준수 : 위치 기반 서비스와 개인정보 처리에 관한 법적 규제를 준수해야 하므로 관련 법률과 규정을 확인하고, 필요한 경우 법적 자문을 받아 서비스 운영을 보장해야한다.

## 화면 구성 📺
|  가입 페이지  |  응원 메시지 목록 페이지   |
| :-------------------------------------------: | :------------: |
|  <img width="390" src="https://github.com/team-distance/distance-be/assets/56196986/177f716f-0eed-41eb-9ef7-b74f5e233570"/> |  <img width="390" src="https://github.com/project-GAZA/GAZA-server/assets/56196986/6a7e6e5f-3c36-4dd3-ba6c-67a6ee7c051f">|  
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
