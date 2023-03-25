package com.gyanendra.login_auth;

import static com.gyanendra.login_auth.R.layout.activity_resgister;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class resgister extends AppCompatActivity {

    TextView alreadyhaveaccount;
    EditText inputemail,inputpassword,inputconfirmpassward;
    String emailpattern="[a-zA-z0-9._']+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_resgister);
        inputemail=findViewById(R.id.inputEmail);
        inputpassword=findViewById(R.id.inputPassword);
        inputconfirmpassward=findViewById(R.id.inputconfirmPasswor1d);
        final Button btnregister=findViewById(R.id.btnregister);

        alreadyhaveaccount=findViewById(R.id.alreadyhaveaccount);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        alreadyhaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(resgister.this ,MainActivity.class));
            }
        });

          btnregister.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  performAuth();

              }
          });

    }

    private void performAuth() {
        String email=inputemail.getText().toString();
        String password=inputpassword.getText().toString();
        String confirmpassword=inputconfirmpassward.getText().toString();

        if (!email.matches(emailpattern))
        {
            inputemail.setError("enter the correct email");
        } else if (password.isEmpty() || password.length()<6) {
            inputpassword.setError("you have enterd wrong password");

        } else if (!password.equals(confirmpassword)) {
            inputconfirmpassward.setError("passward not matched");

        }else{
        progressDialog.setMessage("please wait while Registration..");
        progressDialog.setTitle("Registrstion");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                 if (task.isSuccessful())
                 {
                     progressDialog.dismiss();
                     sendUserToNextActivity();
                     Toast.makeText(resgister.this,"Registration successful",Toast.LENGTH_SHORT);
                 }else {
                     progressDialog.dismiss();
                     Toast.makeText(resgister.this,""+task.getException(),Toast.LENGTH_SHORT);
                 }
            }
        });


        }
    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(resgister.this,home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}