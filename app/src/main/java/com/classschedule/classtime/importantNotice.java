package com.classschedule.classtime;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class importantNotice extends Fragment {

    DatabaseReference databaseReference;
    String noticefromfirebase;
    private TextView textViewgeneralnoticefragment,textViewtitlefragment,textViewdatefragment;

    private ProgressDialog progressDialog;


    public importantNotice() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        databaseReference= FirebaseDatabase.getInstance().getReference().child("NoticeBoard").child("Important Notice");


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_important_notice, container, false);
        textViewgeneralnoticefragment=(TextView) v.findViewById(R.id.importantnoticefragmentid);
        textViewtitlefragment=(TextView) v.findViewById(R.id.importantnoticefragmentidtitle);
        textViewdatefragment=(TextView) v.findViewById(R.id.importantnoticefragmentiddate);

        progressDialog=new ProgressDialog(getActivity());

        progressDialog.setProgress(10);
        progressDialog.setMax(10);
        progressDialog.setMessage("Loading..");
        progressDialog.setIndeterminate(true);


        if(isOnline()){

            progressDialog.show();
        }
        else {

            Toast.makeText(getActivity(),"Please Check Your Internet Connection and try again.Now You see it offline.",Toast.LENGTH_LONG).show();
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                progressDialog.dismiss();

                if(!isOnline()){
                    progressDialog.dismiss();
                }

                String note=dataSnapshot.child("notMessage").getValue().toString();
                String d=dataSnapshot.child("notDate").getValue().toString();
                String tt=dataSnapshot.child("notTitle").getValue().toString();
                textViewgeneralnoticefragment.setText(note);
                textViewtitlefragment.setText(tt);
                textViewdatefragment.setText(d);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        return v;
    }




    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}
