package com.playstore.gokul.mysquad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class signin extends AppCompatActivity {
    EditText phn;
    Button sign;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_signin);

        phn=findViewById(R.id.otptext);
        sign=findViewById(R.id.signinbut);
        img=findViewById(R.id.signimg);

        sign.setAlpha(0);

        phn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phn.animate().translationY(-650).setDuration(500).setStartDelay(100).start();
                sign.animate().alpha(1).translationY(-650).setDuration(500).setStartDelay(300).start();
                img.animate().alpha(0);


            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                String number = phn.getText().toString().trim();
                if (number.isEmpty() || number.length() < 10) {
                    phn.setError("Valid number is required");
                    phn.requestFocus();
                    return;
                }

                String phonenumber = "+" + "91" + number;
                Log.d ("InstaStudio","Phn:"+phonenumber);

                Intent intent = new Intent(signin.this,otp.class);
                intent.putExtra("phonenumber", phonenumber);
                startActivity(intent);

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
    }
}