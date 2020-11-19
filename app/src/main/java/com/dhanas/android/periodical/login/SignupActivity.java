package com.dhanas.android.periodical.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dhanas.android.periodical.R;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private static View view;
    private static EditText fullName, emailId, mobileNumber, location,
            password, confirmPassword;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions;

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
        location = (EditText) findViewById(R.id.location);
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
                checkValidation();
                break;

            case R.id.already_user:

                // Replace login fragment
//                new LoginActivity().replaceLoginFragment();
                break;
        }

    }

    // Check Validation Method
    private void checkValidation() {

        // Get all edittext texts
        String getFullName = fullName.getText().toString();
        String getEmailId = emailId.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
        String getLocation = location.getText().toString();
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getLocation.equals("") || getLocation.length() == 0
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

        else
        Toast.makeText(this, "Do SignUp.", Toast.LENGTH_SHORT).show();

    }
}