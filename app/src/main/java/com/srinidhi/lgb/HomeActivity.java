package com.srinidhi.lgb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {
    CardView mytravel;
    CardView myprofile;
    CardView calender;
    CardView mywork;
    ImageView Logoutbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mytravel=findViewById(R.id.mytravel);
        myprofile=findViewById(R.id.myprofile);
        calender=findViewById(R.id.calender);
        mywork=findViewById(R.id.mywork);
        Logoutbtn=findViewById(R.id.lgout_logo);

        Logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
                startActivity(new Intent(HomeActivity.this,MainActivity.class));

            }
        });


        mytravel.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(HomeActivity.this,mytravelActivity.class));

        }
    });


        myprofile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,myprofileActivity.class));

            }
        });


        calender.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,calenderActivity.class));

            }
        });

        mywork.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,myworkActivity.class));

            }
        });
    }
}
