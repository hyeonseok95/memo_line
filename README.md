# HSMEMO(현석메모)

## 앱 스펙
* target : 29 (Q)
* Lang : Kotlin
* Architecture Pattern : MVVM
* DB : Room
* 테스트 코드 없음 (시간 부족 + 테스트할만큼 비싼로직이 없음)

## 테스트시 주의해주시면 좋겠습니다.
* 현재 프로그래머스 앱에서 카메라를 처음 실행할 경우 카메라 Intent가 아니라 카메라 앱이 실행되는 경우가 있습니다. (앱에서 조치를 취할 방법이 없을 것 같습니다.)

## 사용 라이브러리 (구글 제외)
* Koin - DI
* Timber - Debug
* Picasso - ImageLoader