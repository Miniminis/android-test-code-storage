package com.example.androidtestproject



/*

NAVIGATION
- intent :
    - explicit intents : launch specific activity
        - 주로 manifest 에서 특정한 activity 를 지정 : <intent-filter> <action> <category>
    - implicit intents : specify what you want done and system chooses activity
        - 주로 앱 내부에서 사진, 공유 등 하고 싶은 동작을 정의하면 그에 맞는 앱들을 시스템이 제공해준다.

- navdrawer :
    - necessary : material design graddle setting

LAYOUT
- layout이 깊어지면 깊어질수록 프로그램이 view를 그리는데에 더 많은 시간이 소요됨 >> 결국 성능 저하의 문제로
- flat-layout 형태로 화면을 그려야 기기 및 프로세서에 가해지는 부담이 덜해진다.
- constraint-layout
    - constraints
    - ratio
    - chaining : 그룹화. 요소들을 chain 으로 묶어서 그룹으로 취급함



안드로이드 다양한 기기에 대한 대응
- 다양한 기기에 대한 대응은 안드로이드 개발자들에게 피할 수 없는 과제이다. 구글 안드로이드 팀은 이러한 안드로이드 개발자들의 노고를 덜어주기 위해 다양한 노력을 해왔다.
- Android Jetpack (2018 release):
	- AppCompatActivity : 다양한 기기에서 appbar 가 동일하게 보이도록 해준다.
- img files :
	- vector drawables : 기기 화면의 크기에 상관없이 이미지가 깨지지 않고 선명하게 보여짐
	- png files : 차지하는 메모리가 더 크고 이는 느린 다운로드 속도, 큰 앱의 크기 등 사용자에게 안좋은 영향을 끼침 (OOM)
	- 안드로이드 공식 강의에서는 아래와 같은 방법을 추천함. 앱의 크기도 작아지고 다운로드 속도도 빨라질 것이다.
		- app 단위의 build.gradle 파일:  vectorDrawables.useSupportLibrary = true
		- layout app:srcCompat="@drawable/empty_dice"
		- layout 최 상단에 namespace 추가 : xmlns:app="http://schemas.android.com/apk/res-auto"


lifecycle
* activity 에서 다른 activity 로 이동할 때, 목적지의 activity 가 resume 될 때까지 이전의 엑티비티는 pause 상태임
* 목적지의 엑티비티가 resume 되고 나서야 이전의 엑티비티는 destroy 된다.

initialized
- on create - created -
on start - started : activity visible
- on resume - resumed : activity focus
- on pause - on stop : activity visible
- on destroy  - destroyed

*/