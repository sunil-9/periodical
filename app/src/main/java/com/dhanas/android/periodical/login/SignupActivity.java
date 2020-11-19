package com.dhanas.android.periodical.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dhanas.android.periodical.MainActivityApp;
import com.dhanas.android.periodical.R;
import com.dhanas.android.periodical.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private static View view;
    private static EditText fullName, emailId, mobileNumber,
            password, confirmPassword;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initViews();
        setListeners();
    }
    // Initialize all views
    private void initViews() {
        fullName = (EditText) findViewById(R.id.fullName);
        emailId = (EditText) findViewById(R.id.userEmailId);
        mobileNumber = (EditText) findViewById(R.id.mobileNumber);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        signUpButton = (Button) findViewById(R.id.signUpBtn);
        login = (TextView) findViewById(R.id.already_user);
        terms_conditions = (CheckBox) findViewById(R.id.terms_conditions);

        // Setting text selector over textviews

        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.textview);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            login.setTextColor(csl);
            terms_conditions.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:

                // Call checkValidation method
                signup();
                break;

            case R.id.already_user:

                // Replace login fragment
//                new LoginActivity().replaceLoginFragment();
                break;
        }

    }

    // Check Validation Method
    private void signup() {

        // Get all edittext texts
        String getFullName = fullName.getText().toString();
        String getEmailId = emailId.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0)
            Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show();
            // Check if email id valid or not
        else if (!m.find())
            Toast.makeText(this, "Your Email Id is Invalid.", Toast.LENGTH_SHORT).show();
        else if (!getConfirmPassword.equals(getPassword))

        Toast.makeText(this, "Both password doesn't match.", Toast.LENGTH_SHORT).show();

        else if (!terms_conditions.isChecked())

        Toast.makeText(this, "Please select Terms and Conditions.", Toast.LENGTH_SHORT).show();

        else{
            mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(getEmailId, getPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "createUserWithEmail:success");
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                                String userId = mDatabase.push().getKey();
                                Users user = new Users(getFullName,getEmailId,getMobileNumber);
                                mDatabase.child(userId).setValue(user);


                                startActivity(new Intent(SignupActivity.this, MainActivityApp.class));
                                Toast.makeText(SignupActivity.this, "Authentication success.",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignupActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }

    }
}