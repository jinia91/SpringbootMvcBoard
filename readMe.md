---


---

<h1 id="스프링mvc-회원가입-게시판">스프링MVC 회원가입 게시판</h1>
<h2 id="개발-환경">개발 환경</h2>
<ul>
<li>IntelliJ</li>
<li>Postman</li>
<li>Github</li>
<li>Mysql Workbench</li>
</ul>
<h2 id="사용-기술">사용 기술</h2>
<h3 id="백엔드">백엔드</h3>
<p>JAVA 11</p>
<p><strong>프레임워크</strong></p>
<ul>
<li>Springboot 2.5.4</li>
<li>SpringSecurity</li>
</ul>
<p><strong>Build Tool</strong></p>
<ul>
<li>gradle<br>
+@npm</li>
</ul>
<p><strong>DB</strong></p>
<ul>
<li>Mysql</li>
<li>Mybatis</li>
</ul>
<p><strong>SERVER</strong></p>
<ul>
<li>GitRepository(IMG)</li>
<li>Gmail(SMTP)</li>
</ul>
<h3 id="프론트엔드">프론트엔드</h3>
<ul>
<li>Javascript</li>
<li>Thymeleaf</li>
<li>Bootstrap 5</li>
</ul>
<h3 id="기타-라이브러리">기타 라이브러리</h3>
<ul>
<li>Lombok</li>
<li>Toast UI Editor</li>
<li>GithubAPI</li>
</ul>
<h2 id="주요-키워드">주요 키워드</h2>
<ul>
<li>스프링부트</li>
<li>스프링 시큐리티</li>
<li>클라이언트 / 서버 사이드 이중 검증</li>
<li>Http 통신</li>
<li>페이징</li>
<li>테스트 코드</li>
</ul>
<h2 id="프로젝트-목적과-구조">프로젝트 목적과 구조</h2>
<h3 id="목적">목적</h3>
<p>그동안 공부한 자바와 스프링을 사용하여 간단한 게시판 기능과 회원 관리 모듈을 개발해봄으로서, 백엔드 개발자로서의 기본기를 다지고 WEB 개발의 전반적인 흐름을 익히기 위함입니다.</p>
<h3 id="간략-구조">간략 구조</h3>
<ul>
<li>
<p>모듈단위 구조<br>
<img src="https://github.com/jinia91/blogTest/blob/main/img/40b7847c-bcbb-420e-aec8-e1e78f384cc9.png?raw=true" alt=""></p>
</li>
<li>
<p>세부 예시<br>
<img src="https://github.com/jinia91/blogTest/blob/main/img/98fdbb1e-0143-48e8-91a1-9bf57397ba63.png?raw=true" alt="첨부 이미지"></p>
</li>
<li>
<p>뷰<br>
<img src="https://github.com/jinia91/blogTest/blob/main/img/84adea1f-e317-4e7a-afc7-d8e08cfe613c.png?raw=true" alt="첨부 이미지"></p>
</li>
<li>
<p>정적파일 별도 관리<br>
<img src="https://github.com/jinia91/blogTest/blob/main/img/fd2e9e6a-d6a7-43f6-9086-3060402200f0.png?raw=true" alt="첨부 이미지"></p>
</li>
</ul>
<h2 id="주요-기능">주요 기능</h2>
<h3 id="회원가입-폼">회원가입 폼</h3>
<p><strong>클라이언트사이드 / 서버사이드 유효성 검사</strong></p>
<p><img src="https://github.com/jinia91/blogTest/blob/main/img/5f9b0af2-9d51-41bc-a547-679516596f57.gif?raw=true" alt="클라이언트 사이드 검증 예시"></p>
<p>자바스크립트를 통해 클라이언트단에서 1차적으로 유효성 검사를 수행토록 했으며,</p>
<p>스크립트 무효화로 부적절한 데이터가 서버로 넘어올 경우를 대비하여</p>
<p>JSR-303 빈검증을 사용해 2차적으로 서버사이드에서도 유효성 검사를 수행토록 했습니다.</p>
<p><a href="https://github.com/jinia91/SpringbootMvcBoard/blob/master/board/src/main/java/com/project/board/user/dto/JoinFormDto.java">회원가입 폼 빈검증 DTO 코드</a></p>
<p><img src="https://github.com/jinia91/blogTest/blob/main/img/7eeff716-b8b5-40af-98a7-8989c330a8c7.gif?raw=true" alt="첨부 이미지"></p>
<p>또한 단순 빈검증으로는 구현하기 힘든 DB조회 중복 검증들은 Validator 를 사용하여 구현했습니다.</p>
<p><a href="https://github.com/jinia91/SpringbootMvcBoard/blob/4c30883a4b0809ba6fbe41fe5b689354edba2e86/board/src/main/java/com/project/board/user/validation/JoinFormDtoValidator.java#L23">Validator 사용 아이디 중복검사 코드</a></p>
<p><em>트러블 슈팅</em></p>
<p>빈검증을 사용하면서 검증로직들이 원하는 순서대로 수행되지 않고 모든 검증을 한번에 다 수행하는 문제를 겪었는데 Validation의 GroupSequence를 통해 이를 해결했습니다.</p>
<p><a href="https://github.com/jinia91/SpringbootMvcBoard/blob/master/board/src/main/java/com/project/board/user/validation/ValidationSequenceGroups.java">GroupSequence 애너테이션 활용</a></p>
<p><strong>메일 인증</strong><br>
<img src="https://github.com/jinia91/blogTest/blob/main/img/c534be70-3bab-46b6-be40-978bc33ee7d1.gif?raw=true" alt="첨부 이미지"></p>
<p>회원가입시 Gmail SMTP를 통해 8자리 난수를 전송토록했으며 이때 난수생성은 시드의 보안성을 위해 SecureRandom을 사용하였습니다.</p>

