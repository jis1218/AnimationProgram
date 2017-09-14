# Animator를 이용한 버튼 회전 구현하기

### 버튼을 눌렀을 때 중앙에 모여있던 4개의 버튼이 각각 1, 2, 3, 4분면을 형성하고 형성하는 과정에서 빙글빙글 도는 프로그램을 구현하였다.
### View Animation과 Property Animation 중에서 Property Animation 클래스인 ObjectAnimator 클래스를 사용하였다. 그 이유는 View Animation을 사용할 경우 anim xml 파일에 만들어줘야 하는 파일이 많기 때문이다.

## 배운점

#### 1. Interpolator
##### 애니메이션의 속도를 제어해 줄 수 있는 기능인데 이 프로그램의 경우 AccelerateDecelerateInterpolator 클래스를 이용하여 처음과 마지막은 느리게, 중간은 빠르게 애니메이션이 작동하도록 구현하였다.
```java
AccelerateDecelerateInterpolator aDIntPol = new AccelerateDecelerateInterpolator();
aDIntPol.getInterpolation(0.1f); // 이 함수는 잘 모르겠음
forBtn1Rotate.setInterpolator(aDIntPol);
```

#### 2. Emulator와 실제 단말기에서의 구동
##### Emulator에서 돌리면 구현한대로 되던 것이 단말기에서는 제대로 작동하지 않았다.
##### 첫번째로 1, 2, 3, 4분면에 사각형이 제대로 들어가지 않았다.
##### 두번째로 동시에 이뤄져야 할 이벤트가 간격을 두고 일어났다. 그것도 아주 긴 간격으로...


##### 단말기에서 제대로 작동하지 않은 코드
```java
final int MOVEVALUE = 150;

switch (button.getId()) {
                case R.id.btn1:
                    x = (-1) * MOVEVALUE;
                    y = (-1) * MOVEVALUE;
                    break;
                  }

                  ObjectAnimator forBtn1X = ObjectAnimator.ofFloat(button, "translationX", x);
                  ObjectAnimator forBtn1Y = ObjectAnimator.ofFloat(button, "translationY", y);

                  forBtn1X.setDuration(1000);
                  forBtn1Y.setDuration(1000);

                  if(index%2==0){
                      forBtn1X.setStartDelay(0);
                      forBtn1Y.setStartDelay(0);
                  }else {
                      forBtn1X.setStartDelay(7000);
                      forBtn1Y.setStartDelay(7000);
                  }
                  ObjectAnimator forBtn1Rotate = ObjectAnimator.ofFloat(button, "rotation", z);
                  AccelerateDecelerateInterpolator aDIntPol = new AccelerateDecelerateInterpolator();

                  aDIntPol.getInterpolation(0.1f);
                  forBtn1Rotate.setInterpolator(aDIntPol);
                  forBtn1Rotate.setDuration(10000);

                  AnimatorSet setForBtn1 = new AnimatorSet();
                  setForBtn1.playTogether(forBtn1X, forBtn1Y, forBtn1Rotate);
                  setForBtn1.start();
```

##### 고친 코드
```java
final int MOVEVALUE = btn1.getWidth()/2;
switch (button.getId()) {
                case R.id.btn1:
                    x = (-1) * MOVEVALUE;
                    y = (-1) * MOVEVALUE;
                    break;
                  }
                  ObjectAnimator forBtn1X = ObjectAnimator.ofFloat(button, "translationX", x);
                ObjectAnimator forBtn1Y = ObjectAnimator.ofFloat(button, "translationY", y);

                AnimatorSet setForXY = new AnimatorSet();
                setForXY.playTogether(forBtn1X, forBtn1Y);
                setForXY.setDuration(1000);

                if(index%2==0){
                    setForXY.setStartDelay(0);
                }else {
                    setForXY.setStartDelay(7000);
                }
                ObjectAnimator forBtn1Rotate = ObjectAnimator.ofFloat(button, "rotation", z);
                AccelerateDecelerateInterpolator aDIntPol = new AccelerateDecelerateInterpolator();

                aDIntPol.getInterpolation(0.9f);
                forBtn1Rotate.setInterpolator(aDIntPol);
                forBtn1Rotate.setDuration(10000);

        //        AnimatorSet setForBtn1 = new AnimatorSet();
        //        setForBtn1.playTogether(forBtn1X, forBtn1Y, forBtn1Rotate);
                setForXY.start();
                forBtn1Rotate.start();
            }
        }
```

##### 그 이유를 찾아보면
##### 첫번째 오류 - 움직여야 하는 길이를 상수값을 주었다. 하지만 에뮬레이터와 단말기의 화면 구성이 다르다보니 에뮬레이터에서는 잘 나눠지던게 단말기에서는 약간 모자르게 나눠졌다.
##### 두번째 오류 - X축으로 움직이는 것, Y축으로 움직이는 것, 회전하는 것 모두를 한 Set에 넣다 보니 단말기가 코드를 감당하지 못한 것 같다. 한 메서드에 넣으니 처리하기가 버거웠던 것 같다. 그래서 X, Y만 한 Set으로 만들어 실행하고 회전하는 것 따로 실행하니 잘 돌아갔다. Duration도 다르고 해서 그런 것 같다.
