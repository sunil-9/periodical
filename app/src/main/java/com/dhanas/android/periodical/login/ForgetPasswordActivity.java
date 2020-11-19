package com.dhanas.android.periodical.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dhanas.android.periodical.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetPasswordActivity extends AppCompatActivity {
    private EditText email_address;
    private Button send;
    private Button back_button;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        email_address =findViewById(R.id.registered_emailid);
        send =findViewById(R.id.forgot_button);
        back_button =findViewById(R.id.backToLoginBtn);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgetPasswordActivity.this,LoginActivity.class));
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getEmailId = email_address.getText().toString();


                // Check patter for email id
                Pattern p = Pattern.compile(Utils.regEx);

                Matcher m = p.matcher(getEmailId);

                if (!m.find())
                    Toast.makeText(ForgetPasswordActivity.this, "your Email Id is Invalid.", Toast.LENGTH_SHORT).show();

                    // Else do login and do your stuff
                else {
//            Toast.makeText(this, "Do Login.", Toast.LENGTH_SHORT).show();
                    mAuth = FirebaseAuth.getInstance();


                    mAuth = FirebaseAuth.getInstance();
                    mAuth.sendPasswordResetEmail(getEmailId);
                }
            }
        });
    }
}