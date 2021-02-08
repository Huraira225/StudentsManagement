package com.example.databasefirebaseprojectexample.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.databasefirebaseprojectexample.R;
import com.example.databasefirebaseprojectexample.GetterSetterActivitys.ScreenItems;
import com.example.databasefirebaseprojectexample.Adapters.SliderAdapter;

import java.util.ArrayList;
import java.util.List;

public class OnBaordingScreenActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout DotsLayout;
    TextView tvSkip;
    int position = 0 ;
    Button btnGetStarted;
    Animation btnAnim;

    TextView[] mDots;
    SliderAdapter sliderAdapter;
    Button mBackBtn,mNextBtn;

    int mcurrentPage =2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onbaording_screen);

        mBackBtn =findViewById(R.id.PrevBtn);
        mNextBtn =findViewById(R.id.nextBtn);
        btnGetStarted = findViewById(R.id.btn_get_started);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
        tvSkip = findViewById(R.id.tv_skip);



        final List<ScreenItems> mList = new ArrayList<>();
        mList.add(new ScreenItems("SAVE THE TIME",R.string.First_boarding,R.drawable.first_baording_screen));
        mList.add(new ScreenItems("INSPIRATIONAL",R.string.Second_boarding, R.drawable.second_boarding_screen));
        mList.add(new ScreenItems("TEAMWORK",R.string.Third_boarding,R.drawable.third_boarding_screen));

        viewPager =findViewById(R.id.slideViewPager);
        DotsLayout=findViewById(R.id.dotsLayout);

        sliderAdapter = new SliderAdapter(this,mList);
        viewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        viewPager.addOnPageChangeListener(viewListener);

        //onclickListener

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {

                    position = viewPager.getCurrentItem();
                    if (position < mList.size()) {

                        position++;
                        viewPager.setCurrentItem(position);

                    }

                    if (position == mList.size()-1) { // when we rech to the last screen

                        // TODO : show the GETSTARTED Button and hide the indicator and the next button
                        loaddLastScreen();
                    }
                }
            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(mcurrentPage - 1);
            }
        });

        if (restorePrefData()) {

            Intent mainActivity = new Intent(getApplicationContext(), ChooseScreenActivity.class );
            startActivity(mainActivity);
            finish();


        }
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //open main activity

                Intent mainActivity = new Intent(getApplicationContext(),ChooseScreenActivity.class);
                startActivity(mainActivity);
                // also we need to save a boolean value to storage so next time when the user run the app
                // we could know that he is already checked the intro screen activity
                // i'm going to use shared preferences to that process
                savePrefsData();
                finish();



            }
        });

        // skip button click listener

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(mList.size());
            }
        });
    }



    public void addDotsIndicator(int position){
        mDots= new TextView[3];
        DotsLayout.removeAllViews();
        for (int i=0; i<mDots.length;i++){
          mDots[i] =new TextView(this);
          mDots[i].setText(Html.fromHtml("&#8226"));
          mDots[i].setTextSize(35);
          mDots[i].setTextColor(getResources().getColor(R.color.colorPrimaryDark));

          DotsLayout.addView(mDots[i]);
        }

        if(mDots.length>0){
            mDots[position].setTextColor(getResources().getColor(R.color.black));
        }


    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);

            if (mcurrentPage == i) {

                loaddLastScreen();

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };



    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
        return  isIntroActivityOpnendBefore;
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isIntroOpnend",true);
        editor.commit();
    }

    // show the GETSTARTED Button and hide the indicator and the next button
    private void loaddLastScreen() {
        mNextBtn.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tvSkip.setVisibility(View.INVISIBLE);
        DotsLayout.setVisibility(View.INVISIBLE);
        mBackBtn.setVisibility(View.INVISIBLE);
        // TODO : ADD an animation the getstarted button
        // setup animation
        btnGetStarted.setAnimation(btnAnim);
    }
}
