# wanted-pre-onboarding-backend
원티드 프리온보딩 인턴쉽 선발 과제(wanted-pre-onboarding-backend)


## 박세진
### 애플리케이션의 실행 방법


1. Java Development Kit (JDK) 17 이상 설치
2. Git 설치 (옵션)
```
   git clone https://github.com/codesejin/wanted-pre-onboarding-backend.git
   cd assignment
```
3. `/src/main/resources`로 이동
3. application.properties에서 mysql 설정을 자신의 로컬 호스트상에서 root변수로 수정
```
server.port=8081
jwt.secret=mySuperSecretKey123ThisIsAStrongerSecretKeyWithMoreCharacters
# API URL PREFIX
api-version=v1
api-prefix=/api/${api-version}
# DataSource
spring.datasource.url=jdbc:mysql://localhost:3306/wanted?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8
spring.datasource.username=[root계정]
spring.datasource.password=[root비밀번호]
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true

# SWAGGER
spring.mvc.pathmatch.matching-strategy=ant_path_matcher
```
5. 실행한 뒤 postman으로 api 테스트


### 데이터베이스 테이블 구조
![image](https://github.com/codesejin/wanted-pre-onboarding-backend/assets/101460733/cbd05555-dc5e-4dc2-a6c0-f283ac1b8ff7)


### 구현한 API의 동작을 촬영한 데모 영상 링크
[데모 영상 링크](https://youtu.be/cfRX7s6VsXA)


### 구현 방법 및 이유에 대한 간략한 설명
- Java & Spring, Spring Boot, JPA, MySQL을 이용해서 백엔드 서버 및 DB 구현
- Spring Security와 Jwt를 이용해서 사용자의 인증 및 인가 구현
- 회원가입 시 Spring Security를 이용해서 pw를 암호화 후 DB 저장
- 로그인 시 jwt토큰 생성 후 response 헤더에 Authorization 키값으로 반환
- 나머지 API는 CRUD로 구현했고, 특정 사용자만이 할 수 있는 곳에서는 request 헤더에 토큰 필요

### API 명세(request/response 포함)
[Postman API 명세 링크](https://documenter.getpostman.com/view/19993324/2s9Y5R1Rh2#intro)
![image](https://github.com/codesejin/wanted-pre-onboarding-backend/assets/101460733/cd687b55-65e4-4c61-b051-e999275ad9fd)

