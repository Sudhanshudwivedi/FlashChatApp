package com.example.sudhanshu.flash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.sax.StartElementListener;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

//import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StartActivity extends AppCompatActivity {

    private Button lgnacnt;
    private EditText lgnEmail;
    private EditText lgnPassword;
    private Button lgnButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private ProgressDialog pd;
    private RadioButton chk1,chk2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
        if (!isTaskRoot())
        {
            final Intent intent = getIntent();
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }
        firebaseAuth = FirebaseAuth.getInstance();
        lgnEmail = (EditText) findViewById(R.id.login_emailid);
        lgnPassword = (EditText) findViewById(R.id.login_password);
        lgnButton = (Button) findViewById(R.id.loginBtn);

        TextView text = (TextView) findViewById(R.id.createAccount);
        pd = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        /*
        chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){

                    // show password
                    lgnPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    Log.i("checker", "true");
                }

                else{
                    Log.i("checker", "false");

                    // hide password
                    lgnPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

            }
        });
        */



        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, RegisterActivity.class));
            }
        });

        lgnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signin();
            }
        });
    }

    private void Signin() {
        final String email, pass;

        email = lgnEmail.getText().toString();
        pass = lgnPassword.getText().toString();

        pd.setTitle("Logging In");
        pd.setMessage("Please wait while we check your credentials");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(StartActivity.this, "Enter Email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(StartActivity.this, "Enter Password", Toast.LENGTH_LONG).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    pd.dismiss();
                        Intent intent = (new Intent(StartActivity.this, MainActivity.class));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }



                 else {
                    pd.hide();
                    Toast.makeText(StartActivity.this, "Invalid Email/Password", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }
}