# 백엔드 프로젝트 1

### 주제
- spring을 이용한 MVC 패턴으로 애완용품 쇼핑샵 홈페이지 만들기
- - - -
### 사용기술
- Front : HTML / CSS / JavaScript / jQuery / Ajax
- Back : JAVA / servlet / jsp / jstl
- Tools : sts3 / bootstrap
- - - -
### 담당업무
- 조장
- 관리자페이지 : 회원관리 / 게시판관리 / 정산
- - - -
### 기술서
- 원하는 기간을 선택하고 해당 버튼을 누를시 원하는 기간으로 조회가 됩니다.
<img src="/skill/skill1.png" alt="skillScreen1" style="height: 400px; width:700px;"/>

- jsp 상의 input 태그의 날짜 값을 받아서 옵니다. <br>
"시작 날짜" ~ "마감 날짜" 를 기준으로 하여 양쪽이 다 공란인 경우는 모든 날짜의 데이터가 출력되도록 하였습니다.<br>
한 쪽만 비어있는 경우는 다른 칸에 입력한 날짜 기준으로 전 또는 후의 기간들을 모두 조회되도록 하였습니다.<br>
그래서 조건문 if와 else if를 통해서 경우에 따라 받아야하는 값의 타입을 다르게 받아서 request로 넘어갈 수 있도록 하였습니다.<br>
또한 jsp 에서도 받은 값들을 받아서 쓸 수 있도록 jstl의 조건기능들을 활용하여 만들어 주었습니다.
<img src="/skill/skill2.png" alt="skillScreen2" style="height: 400px; width:100%;"/>
