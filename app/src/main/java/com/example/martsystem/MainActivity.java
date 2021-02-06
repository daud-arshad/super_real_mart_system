package com.example.martsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText etEmailAddress, etPassword;
    private Button btnSignIn;
    private TextView tvSignUp;
    private ArrayList<Account> emailArrayList = new ArrayList<Account>();
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
        //setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEmail();
                validatePassword();
                accountDataFetching();
                for (Account account : emailArrayList) {
                    if (etEmailAddress.getText().toString().trim().equals(account.getEmailAddress()) && etPassword.getText().toString().trim().equals(account.getPassword())) {
                        etEmailAddress.setError(null);
                        etPassword.setError(null);
                        Intent signInIntent = new Intent(MainActivity.this, SellerActivity.class);
                        startActivity(signInIntent);
                    } else {
                        Toast.makeText(MainActivity.this, getString(R.string.invalid_email_or_password), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });
    }

    private void init() {
        etEmailAddress = findViewById(R.id.etEmailAddress);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvSignUp = findViewById(R.id.tvSignUp);
    }

    private boolean validateEmail() {
        String emailAddress = etEmailAddress.getText().toString().trim();
        if (emailAddress.isEmpty()) {
            etEmailAddress.setError(getString(R.string.can_not_be_empty));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            etEmailAddress.setError(getString(R.string.enter_an_appropriate_email));
            return false;
        } else {
            etEmailAddress.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String password = etPassword.getText().toString().trim();
        if (password.isEmpty()) {
            etPassword.setError(getString(R.string.can_not_be_empty));
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            etPassword.setError(getString(R.string.enter_an_appropriate_password));
            return false;
        } else {
            etPassword.setError(null);
            return true;
        }
    }

    private void visibilityPassword() {

    }

    private void accountDataFetching() {
        DatabaseReference accountsRef = FirebaseDatabase.getInstance()
                .getReference().child("Accounts");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Account> list = new ArrayList<Account>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Account account = ds.getValue(Account.class);
                    list.add(account);
                    //Log.d("TAG", account.getFirstName());
                    //etEmailAddress.setText(account.getFirstName());
                    emailArrayList.add(account);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        accountsRef.addListenerForSingleValueEvent(valueEventListener);
    }
}