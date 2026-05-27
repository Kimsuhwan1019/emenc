# CLAUDE.md — 프로젝트 작업 규칙 (반드시 준수)

## 프로젝트
- (주)이엠이앤씨 인테리어 회사 홈페이지
- 대학교 "서버 프로그래밍" 기말 프로젝트 (개인). 수업에서 배운 스택/패턴을 최대한 활용한다.

## 기술 스택 (고정 — 변경 금지)
- Java 21, Spring Boot 4.0.6, Gradle(Groovy), 패키징 jar
- 기본 패키지: com.emenc.interior
- 템플릿: Thymeleaf + thymeleaf-layout-dialect + thymeleaf-extras-springsecurity6
- 데이터: Spring Data JPA / Hibernate, H2 (jdbc:h2:~/local, 콘솔 /h2-console)
- 보안: Spring Security (@EnableWebSecurity + SecurityFilterChain), 비밀번호 BCrypt
- 검증: spring-boot-starter-validation, 코드 생성: Lombok, 개발: DevTools
- application.properties에 H2/JPA 설정(ddl-auto=update) 이미 있음 — 함부로 바꾸지 말 것

## 절대 금지 (Do NOT)
- React / Next.js / Vue 등 JS 프론트엔드 프레임워크 도입 금지 (서버 렌더링 유지)
- Maven, JSP 금지 (Gradle Groovy 유지)
- 빌드 과정이 필요한 CSS(Tailwind 등) 금지
- H2 외 다른 DB로 임의 교체 금지

## 화면/디자인
- 모든 화면은 Thymeleaf 템플릿(src/main/resources/templates)으로 작성
- 공통 레이아웃: templates/layout.html (상단 로고+네비), 각 페이지는 layout:fragment="content"
- CSS는 일반 CSS 파일을 src/main/resources/static/ 에 두고 사용 (빌드 불필요)
- Bootstrap은 써도 되고 안 써도 됨. 디자인 톤(색·폰트)은 추후 별도 지정 예정

## 코드 구조 규칙 (교안 패턴 그대로)
- 계층: Controller → Service → Repository → Entity
- 엔티티: JPA 애너테이션 + Lombok / 리포지터리: JpaRepository 상속 인터페이스
- 폼: 별도 Form 클래스 + @Valid + BindingResult로 검증

## 인증/회원 규칙
- 회원 엔티티 SiteUser: username, email, password(BCrypt), role(UserRole enum: ADMIN/USER)
- UserSecurityService implements UserDetailsService (loadUserByUsername 구현)
- 로그인 URL: /user/signin, 회원가입 URL: /user/signup, 로그인 성공 시 / 로 이동
- 견적문의 상세는 "작성자 본인 + 관리자(ADMIN)"만 열람 (인가 처리)

## 작업 방식
- 한 번에 다 만들지 말고 단계별로. 각 단계 끝나면 멈추고 "서버 실행해 확인하세요"라고 알릴 것
- 새 의존성 추가 시 build.gradle 수정 후 Gradle refresh 필요함을 알릴 것
- 의미 있는 단위마다 git 커밋 메시지를 제안할 것
- 코드에는 한국어 주석으로 핵심만 간단히 설명
