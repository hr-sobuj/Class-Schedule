package com.classschedule.classtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class userLoginInterface extends AppCompatActivity {

    private EditText secMail,secPass;
    private Button btnSeclog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_interface);

        secMail=findViewById(R.id.secretmail);
        secPass=findViewById(R.id.secretPass);
        btnSeclog=findViewById(R.id.secretlogin);


        btnSeclog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String SecM=secMail.getText().toString()+"ClassSceduleMail";
                String SecP=secPass.getText().toString()+"ClassScedulePassword";

                //Toast.makeText(userLoginInterface.this,SecM,Toast.LENGTH_LONG).show();


                if(SecM.length()==5+16&&SecP.length()==6+20){

                    Intent intent=new Intent(userLoginInterface.this,new_app_menu.class);
                    startActivity(intent);
                    finish();
                    finishAffinity();
                }
                else {

                    Toast.makeText(userLoginInterface.this
                    ,"Wrong information",Toast.LENGTH_LONG).show();
                }



            }
        });

    }
}
