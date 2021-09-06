package com.classschedule.classtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class settimeactivity extends AppCompatActivity {

    private Button btnlogin;
    private DrawerLayout mNavDrawer;
    private Button loginuser,signupuser;
    private EditText email_login,password_login;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settimeactivity);


        mAuth = FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading..");

        btnlogin=(Button) findViewById(R.id.adminloginic);

        email_login=(EditText) findViewById(R.id.loginmail);
        password_login=(EditText) findViewById(R.id.loginPass);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOnline()){
                    loginmethod();
                }

                else {

                    Toast.makeText(settimeactivity.this,"No Internet Connection!",Toast.LENGTH_LONG).show();

                }



               /* Intent intent=new Intent(settimeactivity.this,adminactivity.class);
                startActivity(intent);*/
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.back_layout,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.backId){
            Intent intent=new Intent(settimeactivity.this,new_app_menu.class);
            startActivity(intent);

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){

        Intent intent=new Intent(settimeactivity.this,new_app_menu.class);
        startActivity(intent);
        finish();

    }

    public void loginmethod(){


        final String email=email_login.getText().toString().trim();
        final String password=password_login.getText().toString().trim();
        /* final String passs2=repass1.getText().toString().trim();*/


        if(email.isEmpty()){
            email_login.setError("Write a Valid Email Address.");
            email_login.requestFocus();
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_login.setError("Email Is Incorrect.");
            email_login.requestFocus();
            return;
        }
        else if(password.isEmpty()){

            password_login.setError("Enter Your Password");
            password_login.requestFocus();
            return;
        }
        else if(password.length()<6){

            password_login.setError("Password Incorrect.");
            password_login.requestFocus();
            return;
        }
        else {
            progressDialog.show();
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    /*Toast.makeText(login.this,"Log in successfull.",Toast.LENGTH_LONG).show();*/
                    Intent intent=new Intent(settimeactivity.this,adminactivity.class);
                    startActivity(intent);

                }
                else {
                    progressDialog.dismiss();

                    Toast.makeText(settimeactivity.this,"Log in Failed.",Toast.LENGTH_LONG).show();

                }

            }
        });


    }
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

}
