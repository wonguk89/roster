# *임직원 스케쥴 관리 프로그램 (2024.01.16 ~ 2024.02.04 )*


<p align="center">
<img src="https://github.com/wonguk89/roster/assets/112766179/eef4d506-4c22-4b3a-8b2e-ff0b9bd59013" width="900px" height="300px">
</p>

___
&nbsp;
[FrontEnd 보러가기](https://github.com/wonguk89/roster_front)
>스케쥴 생성 조건


- 임직원들은 월에 8 회의 휴무를 기본으로갖는다


- 임직원들은 월에 3회 원하는 날짜에 휴무를 신청할수있다


- 임직원이 가진 스킬에 따라  스케쥴은 오픈조, 미들조, 마감조 로 나뉜다
 
    예시1) 신입직원은 미들조만 가능하다


    예시2) 오픈조가 가능한직원은 오픈조와 미들조가 가능하다.


    예시3) 오픈조와 마감조가 가능한 직원은 오픈조, 미들조, 마감조가 모두 가능하다


- 화요일,수요일에는 5명의 직원이 그외의 요일에는 6명의 직원이 근무한다

- 가게 자체 휴일을 지정 할수있다(지정된 휴일에는 근무자가 생성되면 안된다)

- 위의 조건을 모두 충족한 상태에서 한달치 스케쥴이 랜덤 생성된다

   `직원의 휴무일 간격이 어느정도 일정 해야한다`

&nbsp;

>Step 1

- 생성할 스케쥴의 연도와 월을 선택한다(선택된 연도와 월은 Vuex로 전역에서 계속사용한다)
- 년도와 월이 선택되었는지 validation 후 다음단계로 넘어간다
- 년도와 월을 선택한 후에는 목록버튼을 이용해 이미저장되어있는 스케쥴을 볼수있다


&nbsp;

>Step 2

- 저장된 직원의 정보가 조회된다
- 직원정보 추가 수정 삭제가 가능하다(해당직원이 지정한달에 가져야할 휴무갯수를 함께 저장한다)
- 수정중인 사항이 저장되기전이라면 다음단계 전 validation 후 넘어간다

&nbsp;
>Step 3

- 직원이름과 직원이 가진 스킬(오픈조,미들조,마감조 가능여부) 가 조회된다
- 스킬은 체크박스로 수정가능하다
- 수정중인 사항이 저장되기전이라면 다음단계 전 validation 후 넘어간다

&nbsp;
>Step 4

- 선택된 달의 가게휴무일이 조회된다
- 가게휴무일에 대한 추가 수정 삭제가 가능하다
- 수정중인 사항이 저장되기전이라면 다음단계 전 validation 후 넘어간다

&nbsp;
>Step 5

- 직원이 신청한 휴무일을 조회한다
- 직원이 신청한 휴무일을 추가 수정 삭제가 가능하다
- 수정중인 사항이 저장되기전이라면 다음단계 전 validation 후 넘어간다

&nbsp;
>Step 6
- 전 단계에서 DB에 저장된 데이터로 한달치 랜덤 스케쥴을 생성한다
- 상기 스케쥴 생성조건에 맞춰 DB의 저장된 데이터를 이용해 스케쥴을 생성한다
- 생성된 스케쥴을 달력 형태로 화면에 보여준다
- 생성된 스케쥴을 저장할수있다
- 저장된 스케쥴은 Step1에서 목록버튼으로 조회가능하다

&nbsp;




