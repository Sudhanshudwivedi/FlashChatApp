package com.example.sudhanshu.flash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText user, email, pass, cpass;
    String user1, email1, pass1, cpass1;
    Button btn;
    TextView login;
    FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        user = (EditText) findViewById(R.id.fullName);
        email = (EditText) findViewById(R.id.userEmailId);
        pass = (EditText) findViewById(R.id.password);
        cpass = (EditText) findViewById(R.id.confirmPassword);
        btn = (Button) findViewById(R.id.signUpBtn);
        login = (TextView) findViewById(R.id.already_user);
        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,StartActivity.class));
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }

    public void register() {
        initialize();
    }

    public void initialize() {
        user1 = user.getText().toString().trim();
        email1 = email.getText().toString().trim();
        pass1 = pass.getText().toString().trim();
        cpass1 = cpass.getText().toString().trim();
        if (!validate()) {
            Toast.makeText(this, "Signup has failed!!!!!", Toast.LENGTH_SHORT).show();
        } else {
            onsignupsucess(user1);
        }
    }

    public boolean validate()
    {
        boolean valid=true;
        if(user1.isEmpty()||user1.length()>32)
        {
            user.setError("Please enter Valid Username!!");
            valid=false;
        }
        if(email1.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email1).matches())
        {
            email.setError("Please enter valid email Address!!");
            valid=false;
        }
        if(pass1.length()<8)
        {
            pass.setError("Password must be of minimum 8 characters!!");
            valid=false;
        }
        if(!cpass1.matches(pass1))
        {
            cpass.setError("Confirm Password must match with Password!!!!");
            valid=false;
        }

        return valid;
    }

    public void onsignupsucess(final String user1) {


        mProgress.setTitle("Registering User");
        mProgress.setMessage("Please wait while we create your account !");
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();
        mAuth.createUserWithEmailAndPassword(email1, pass1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser current_user=FirebaseAuth.getInstance().getCurrentUser();
                    String uid=current_user.getUid();
                    mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                    HashMap<String,String> userMap=new HashMap<>();
                    userMap.put("name",user1);
                    userMap.put("status","Hi there I'm using Flash Chat App");
                    userMap.put("image","default");
                    userMap.put("thumb_image","default");
                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(RegisterActivity.this, "Registration Sucessful", Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(RegisterActivity.this, StartActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);

                            mProgress.dismiss();

                        }
                    });




                } else {
                    mProgress.dismiss();

                    Toast.makeText(RegisterActivity.this, "Unsucessful", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    public void LoginPage(View view) {
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
    }
}


