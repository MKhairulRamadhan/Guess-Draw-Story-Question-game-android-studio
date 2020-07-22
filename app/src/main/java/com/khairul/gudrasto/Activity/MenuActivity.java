package com.khairul.gudrasto.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.khairul.gudrasto.Adapter.PagerAdapter;
import com.khairul.gudrasto.Helper.SharePrefManager;
import com.khairul.gudrasto.Model.PagerModel;
import com.khairul.gudrasto.R;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    private SharePrefManager sharePrefManager;

    private ViewPager viewPager;
    private PagerAdapter adapter;
    private List<PagerModel> models;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private TabLayout tabIndicator;

    private ImageButton logout, progres;
    private ImageView btn_next, btn_prev;

    private TextView tv_top;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        sharePrefManager = new SharePrefManager(this);
        String name = sharePrefManager.getSPName();

        models = new ArrayList<>();
        models.add(new PagerModel(1,R.drawable.menu_1, "Tebak \nSuara Hewan"));
        models.add(new PagerModel(2,R.drawable.menu_2, "Ayoo\nMewarnai"));
        models.add(new PagerModel(3,R.drawable.menu_3, "Soal &\nCerita"));

        tabIndicator = findViewById(R.id.tabLayout);

        logout = findViewById(R.id.logout);
        progres = findViewById(R.id.progress);
        btn_next = findViewById(R.id.btn_next);
        btn_prev = findViewById(R.id.btn_prev);
        tv_top = findViewById(R.id.tv_top);
        tv_top.setText("Hi "+name+"\nWhat games would you like to play?");

        adapter = new PagerAdapter(models, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(15, 20, 15, 0);

        //setup tab layout with pager view
        tabIndicator.setupWithViewPager(viewPager);


//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

    }

    public void logout(View view){
        Toast.makeText(this, "You Logout..!", Toast.LENGTH_SHORT).show();
        sharePrefManager.saveSPBoolean(SharePrefManager.SP_LOGIN_DONE, false);
        startActivity(new Intent(MenuActivity.this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    public void progress(View view){
        Intent moveToProgress = new Intent(MenuActivity.this, ProgresActivity.class);
        startActivity(moveToProgress);
    }

    public void next(View view){
        position = viewPager.getCurrentItem();
        if (position < models.size()){
            position++;
            viewPager.setCurrentItem(position);
        }
    }

    public void prev(View view){
        position = viewPager.getCurrentItem();
        if (position >= 0){
            position--;
            viewPager.setCurrentItem(position);
        }
    }

}
