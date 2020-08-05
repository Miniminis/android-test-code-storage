# DogsApplication with MVVM Pattern
> mvvm pattern 을 이용하여 리스트 출력 및 상세보기 페이지
<br>

### MVVM Pattern 
- View : 사용자에게 보여지는 안드로이드 화면 
- Model : 데이터와 관련된 모든 것
- ViewModel : 뷰로부터 데이터 요청을 받으면 모델에 접속하여 데이터를 받아 뷰로 뿌려줌. 모델과 뷰를 연결하는 매개체 역할. 

<br>

### 순서 
1. 어플리케이션 icon-launcher 커스텀 
2. 필요한 라이브러리 의존성 추가 : build.gradle 설정 
3. 필요한 화면 fragment 생성 
    - activity_main.xml
    - fragment_list.xml
    - item_dog.xml
    - fragment_detail.xml
4. dogs_navigation.xml : 화면 간 연결을 위해 navigation 설정 
    - 화면들 간 관계설정 : action 
    - argument 통해서 데이터 넘겨주기 
    - main activity 에 navigation 설정해서 back button 상단에 만들어주기 
5. 이제 화면에 표시할 데이터를 위해 model 생성 
    - DogBreed.java : api 로부터 dog data 를 받아올 객체  
    - DogApi : retrofit http 통신을 위한 interface 
      - 각종 매서드 표시 및 endpoint 지정 
    - DogApiService
      - retrofit 객체 생성
      - retrofit interface 내부의 통신 실행 결과 받아오는 매서드 지정 
 6. ListViewModel : view 에서 model 로의 접근을 위해 viewmodel 정의 
