package com.example.martsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private EditText etFirstName, etLastName, etEmailAddress, etValidateEmailAddress, etPassword, etValidatePassword, etNumber;
    private AppCompatButton btnCreateAccount;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])" +    //at least 0ne digit
                    "(?=.*[a-z])" +             //at least one lower case letter
                    "(?=.*[A-Z])" +             //at least one upper case letter
                    "(?=.*[@#$%^&+=])" +        //at least one special character
                    "(?=\\S+$)" +               //no white spaces
                    ".{6,}" +                   //at least six characters in total
                    "$");                       //this is just the end of the string

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    public void init() {
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmailAddress = findViewById(R.id.etEmailAddress);
        etValidateEmailAddress = findViewById(R.id.etValidateEmailAddress);
        etPassword = findViewById(R.id.etPassword);
        etValidatePassword = findViewById(R.id.etValidatePassword);
        etNumber = findViewById(R.id.etNumber);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
    }

    private boolean validateEmail() {
        String emailAddress = etEmailAddress.getText().toString().trim();
        String validateEmail = etValidateEmailAddress.getText().toString().trim();
        if (emailAddress.isEmpty()) {
            etEmailAddress.setError(getString(R.string.can_not_be_empty));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            etEmailAddress.setError(getString(R.string.enter_an_appropriate_email));
            return false;
        } else if (!emailAddress.equals(validateEmail)) {
            etValidateEmailAddress.setError(getString(R.string.emails_does_not_match));
            return false;
        } else {
            etValidateEmailAddress.setError(null);
            etEmailAddress.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String password = etPassword.getText().toString().trim();
        String validatePassword = etValidatePassword.getText().toString().trim();
        if (password.isEmpty()) {
            etPassword.setError(getString(R.string.can_not_be_empty));
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            etPassword.setError(getString(R.string.enter_an_appropriate_password));
            return false;
        } else if (!password.equals(validatePassword)) {
            etValidatePassword.setError(getString(R.string.passwords_does_not_match));
            return false;
        } else {
            etValidatePassword.setError(null);
            etPassword.setError(null);
            return true;
        }
    }

    private boolean validate() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String number = etNumber.getText().toString().trim();
        if (firstName.isEmpty()) {
            etFirstName.setError(getString(R.string.enter_your_first_name));
            return false;
        } else if (lastName.isEmpty()) {
            etLastName.setError(getString(R.string.enter_your_last_name));
            return false;
        } else if (!validateEmail())
            return false;
        else if (!validatePassword())
            return false;
        else if (number.isEmpty()) {
            etNumber.setError(getString(R.string.enter_your_number));
            return false;
        } else {
            HashMap<String, String> accountsData = new HashMap<String, String>();
            accountsData.put("firstName", etFirstName.getText().toString().trim());
            accountsData.put("lastName", etLastName.getText().toString().trim());
            accountsData.put("emailAddress", etEmailAddress.getText().toString().trim());
            accountsData.put("password", etPassword.getText().toString().trim());
            accountsData.put("phoneNumber", etNumber.getText().toString().trim());
            FirebaseDatabase.getInstance().getReference().child("Accounts").push().setValue(accountsData)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(SignUpActivity.this, "On complete Listener fired", Toast.LENGTH_LONG).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(SignUpActivity.this, getString(R.string.account_created), Toast.LENGTH_LONG).show();

                }
            });
            Intent sellerDashboardIntent = new Intent(SignUpActivity.this, SellerActivity.class);
            startActivity(sellerDashboardIntent);
            //SignUpActivity.this.finish();
            return true;
        }
    }
}