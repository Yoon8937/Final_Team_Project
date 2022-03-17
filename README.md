# 🚫 불법 주정차 시민신고 자동처리 시스템 🚫 
# 🖥 DEMO 💻 
<!-- 처음부터 끝까지 어떻게 작동하는지로 설명하듯 사람들이 쉽게 알아볼수있게 전개하기 -->
### 앱 이용방법
<img src="https://user-images.githubusercontent.com/91523484/158516191-98cc8cc2-bd05-4681-a0db-48c2dacecd67.gif">  <br/> <br/>
### 클라이언트(안드로이드) <br/> 회원가입 및 로그인 / 사진촬영 및 신고 / 신고결과
<img src="https://user-images.githubusercontent.com/91523484/158616134-8d9ad925-912b-4d12-91ae-4f509a4e0447.gif" width="250" height="500" >  <img src="https://user-images.githubusercontent.com/91523484/158503282-6df79dc3-1347-4dc9-965e-023f4ca3a6ea.gif" width="250" height="500"> <img src="https://user-images.githubusercontent.com/91523484/158505456-c8010f51-988a-48eb-b58d-f96c33a26104.gif" width="250" height="500" > <br/> <br/>
### 관리자(장고) <br/> 로그인 및 시각화 / 갤러리 로그???? 
<img src="https://user-images.githubusercontent.com/91523484/158720741-3483ad43-ac2e-4928-b7c9-bbbfd43a2e75.gif" width="412" height="280"> <img src="https://user-images.githubusercontent.com/91523484/158722695-ea016d1e-6846-448c-823b-292d72db0b50.gif" width="412" height="250" >
### 지도 분포 / 신고로그
<img src="https://user-images.githubusercontent.com/91523484/158725636-9da3153b-62ad-4fb1-af31-4e5b5afc8850.gif" width="500" height="300"> <img src="https://user-images.githubusercontent.com/91523484/158724817-d9b4f632-6c41-4eab-9a62-e5ebbbf661f1.gif" width="500" height="300"> <br/> <br/> <br/>

# 📖 프로젝트 개요
### 불법 주·정차 문제는 현재 해마다 늘어나고 있지만, 시청에서 실행할 수 있는 단속에는 그 한계가 명확히 보인다. 이를 시청에서 관리할 수 있는 시공간적 자료의 부재와 인적자원 부족의 문제로 판단하였다. 두 문제점을 보완하기 위해 불법 주·정차로 불편을 겪는 일반인들도 적극적으로 이용 가능하며, 시스템을 관리하는 시청이나 공익 기관에서 접수된 신고를 빠르고 쉽게 처리할 수 있고, 처리된 결과를 통해 시공간적 자료를 확보할 수 있는 시스템을 구축하기 위해 프로젝트를 기획하였습니다. <br/> <br/>
#  👨‍🔧 주요담당 및 역활 👨‍💻
### 최윤규
  * Android framework
    * Django model에 적용 가능한 REST API 설정 설계 - POST, GET
    * Django framework와 Retrofit2 라이브러리를 통한 통신 구현 
    * Wireshark를 통한 디버깅(팻킷분석)
    * User Interface 설계 및 구현
      * Android Camera 구현
      * 로그인/회원가입 구현
      * Button 메뉴 구현
      * 로딩 화면 구현
      * 이미지 업로드 화질 개선 
      
  * AWS(Amazon Web Service)
    * AWS EC2로 인스턴스 생성 및 원격 SSH 연결 (MobaXterm)
    * AWS Lambda serverless 트리거 - 이벤트 연동 -S3(트리거), RDS(이벤트)
    * AWS Cloudwatch로 AWS Lambda 관리 감독 – 예상 비용 검토
    * AWS IAM, Billing에서 사용자 역할 및 계정 예산 관리

  * Django framework
      * CSRF Token을 이요한 Android framework 연동 및 세션 유지 
      * Postman을 이용한 디버깅
      * Django 개발 환경 설정 
<br/> <br/>

# 🔨 About Project
* 개발환경 : Windows10, Linux
* 사용기술 : Android Framework, REST API, AWS EC2, AWS S3, AWS RDS, AWS lambda, AWS Cloudwatch, Django Framework, NGINX 
* 개발도구 : Android Studio, MobaXterm, MySQL workbench, Postman, github, Wireshark, Pycharm
* 개발언어 : Java, Python, SQL
* 사용장비 : NVIDIA T4 Tensor core GPU

      
      



# 시스템 구조
<img src="https://user-images.githubusercontent.com/91523484/158737848-c0960568-2c38-49a2-9ca6-806159e9bbd0.png"> 


<!-- 
<hr/> 바 만드는것 
<img src="" width="" height=""> 
align='left' 한칸 옆으로 띄우는거  -->
