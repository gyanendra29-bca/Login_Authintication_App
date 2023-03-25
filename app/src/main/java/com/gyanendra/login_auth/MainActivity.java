package com.gyanendra.login_auth;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText email,pass;
    Button btnlogin;
    FirebaseAuth mAuth;
    TextView createnewaccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.inputEmail);
        pass=findViewById(R.id.inputPassword);
        btnlogin=findViewById(R.id.btnlogin);
        final TextView textView=findViewById(R.id.forgotpassword);

        createnewaccount=findViewById(R.id.createnewaccount);

        createnewaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this ,resgister.class));
            }
        });
           textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,resgister.class);
                startActivity(intent);
            }

        });
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),home.class));
            finish();
       }
           btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String semail =email.getText().toString();
                    final String passwor = pass.getText().toString();

                    mAuth.signInWithEmailAndPassword(semail,passwor).addOnCompleteListener(task -> {
                       if (task.isSuccessful()){
                           Toast.makeText(MainActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(getApplicationContext(),home.class));
                       }
                       else {
                           Toast.makeText(MainActivity.this, "Something went to Wrong", Toast.LENGTH_SHORT).show();
                       }
                    });
                }
            });

    }

}
