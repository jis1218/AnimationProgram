package com.example.animproject;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{

    Button btn1, btn2, btn3, btn4, btnRotate;

    final int ROTATIONVALUE = 7200;

    int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);

        btnRotate = (Button) findViewById(R.id.btnRotate);

        btnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starter(btn1);
                starter(btn2);
                starter(btn3);
                starter(btn4);

                index++;
            }
        });
    }

    public void starter(Button button){
        int x=0;
        int y=0;
        int z=0;
        if(index%2==0) {
            final int MOVEVALUE = btn1.getWidth()/2;
            z = ROTATIONVALUE;
            switch (button.getId()) {
                case R.id.btn1:
                    x = (-1) * MOVEVALUE;
                    y = (-1) * MOVEVALUE;
                    break;
                case R.id.btn2:
                    x = MOVEVALUE;
                    y = (-1) * MOVEVALUE;
                    break;
                case R.id.btn3:
                    x = (-1) * MOVEVALUE;
                    y = MOVEVALUE;
                    break;
                case R.id.btn4:
                    x = MOVEVALUE;
                    y = MOVEVALUE;
                    break;
            }
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
