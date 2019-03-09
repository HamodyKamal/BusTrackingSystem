/**
Errors So Far:
 Sign in - up:- When Sign in or up with empty fields (App Crashed).
 */

package com.example.regestration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button nDriver;
    private Button mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //// REG start /////
        nDriver = (Button) findViewById(R.id.driver);

        nDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DriverLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mMap = (Button) findViewById(R.id.map);

        mMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserView.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        //// REG ends /////

    }

}
