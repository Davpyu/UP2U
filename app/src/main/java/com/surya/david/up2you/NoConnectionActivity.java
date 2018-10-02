package com.surya.david.up2you;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class NoConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connection);
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean checkConnection = new ApplicationUtility().checkConnection(getApplicationContext());
        if (checkConnection){
            Intent in = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(in);
        }else{
            Toast.makeText(getApplicationContext(), "No Connection Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}
