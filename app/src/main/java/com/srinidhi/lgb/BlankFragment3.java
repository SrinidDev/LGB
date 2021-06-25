package com.srinidhi.lgb;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.srinidhi.lgb.Model.Users;
import com.srinidhi.lgb.Prevalent.Prevalent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment3 extends Fragment implements AdapterView.OnItemSelectedListener {

    private Button submitbtn;
    private EditText inputjbr,inputdepart,inputvname,inputvmodal,inputvcc;
    private ProgressDialog loadingdbar;
    private ProgressDialog loadingdbar2;
    private String parentDbName = "Users";
    private int count=0;
    Spinner spinner;
    List<String> names;
    DatabaseReference dbref;
    String[] deps = { "Sales", "Packaging", "Delivery", "Accounts", "HR" };
    String depdetail;
    int pos;
    public BlankFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank_fragment3, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
         submitbtn =(Button) view.findViewById(R.id.asset_btn);
         inputjbr=(EditText) view.findViewById(R.id.Job_role);
         inputdepart=(EditText) view.findViewById(R.id.Dep);
         inputvname=(EditText) view.findViewById(R.id.Vehi);
         inputvmodal=(EditText) view.findViewById(R.id.Model_name);
         inputvcc=(EditText) view.findViewById(R.id.eng_cc);
         //inputcphone=(EditText) view.findViewById(R.id.cnfrm_phone);
         loadingdbar=new ProgressDialog(getActivity());
         spinner=view.findViewById(R.id.spinner1);
         names=new ArrayList<>();
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, deps);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);






         submitbtn.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              if (Prevalent.currentOnlineUser.getJobrole() == null) {
                                                  submitdetails();

                                              } else {
                                                  System.out.println("Its Not Approved.");
                                                  System.out.println(Prevalent.currentOnlineUser.getJobrole());
                                                  Toast.makeText(getActivity(), "Its Not Approved.", Toast.LENGTH_SHORT).show();
                                              }
                                          } } );


    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        //Toast.makeText(getActivity(), "Selected Department: "+deps[position] ,Toast.LENGTH_SHORT).show();
        depdetail=deps[position];

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO - Custom Code
    }


    private void submitdetails()
    {
        String jobrole=inputjbr.getText().toString();
        String vdepart=inputdepart.getText().toString();
        String vname=inputvname.getText().toString();
        String vmodalname=inputvmodal.getText().toString();
        String vehicc=inputvcc.getText().toString();

       // String phone=inputcphone.getText().toString();
        //dbref=FirebaseDatabase.getInstance().getReference();


        /*dbref.child("Department").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot chilSnapshot:snapshot.getChildren()){
                    String spinnername=snapshot.child("name").getValue(String.class);
                    names.add(spinnername);

                }

                final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,names);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spinner.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/


        if(TextUtils.isEmpty(jobrole))
        {
            Toast.makeText(getActivity(),"Please Enter Your Job Role",Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(vdepart))
        {
            Toast.makeText(getActivity(),"Please Enter Your Department",Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(vname))
        {
            Toast.makeText(getActivity(),"Please Enter Your Vehicle Name",Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(vmodalname))
        {
            Toast.makeText(getActivity(),"Please Enter Your Vehicle Modal Name",Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(vehicc))
        {
            Toast.makeText(getActivity(),"Please Enter Your Vehicle CC",Toast.LENGTH_SHORT).show();
        }

        //else if(TextUtils.isEmpty(phone))
        //{
         //   Toast.makeText(getActivity(),"Please Confirm Your Phone Number",Toast.LENGTH_SHORT).show();
        //}

        else
        {

            loadingdbar.setTitle("Updating Credentials");
            loadingdbar.setMessage("Please wait, while we are updating the credentials.");
            loadingdbar.setCanceledOnTouchOutside(false);
            loadingdbar.show();


            Validateacc(jobrole,vdepart,vname,vmodalname,vehicc,depdetail);
        }
    }


    private void Validateacc(final String jobrole, final String vdepart, final String vname, final String vmodalname, final String vehicc, final String depdetail)
    {
         final DatabaseReference Rootref;
         Rootref = FirebaseDatabase.getInstance().getReference();



         Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 if(dataSnapshot.child(parentDbName).child(Prevalent.currentOnlineUser.getPhone()).child(jobrole).exists())
                 {
                     loadingdbar2.setTitle("Alert!!!");
                     loadingdbar2.setMessage("You Can Update Only Once!!!!");
                     loadingdbar2.setCanceledOnTouchOutside(false);
                     loadingdbar2.show();
                     loadingdbar2.dismiss();
                     Toast.makeText(getActivity(), "This " + jobrole + " already exists.", Toast.LENGTH_SHORT).show();
                     loadingdbar.dismiss();
                     Toast.makeText(getActivity(), "Its Not Approved.", Toast.LENGTH_SHORT).show();

                     Intent intent = new Intent(getActivity(), myprofileActivity.class);
                     startActivity(intent);

                 }

                 if (!(dataSnapshot.child(parentDbName).child(Prevalent.currentOnlineUser.getPhone()).child(jobrole).exists())) {
                     Users usersData = dataSnapshot.child(parentDbName).child(Prevalent.currentOnlineUser.getPhone()).getValue(Users.class);

                         HashMap<String, Object> userdataMap = new HashMap<>();
                         userdataMap.put("jobrole", jobrole);
                         userdataMap.put("department", vdepart);
                         userdataMap.put("vehiclename", vname);
                         userdataMap.put("VehicleModalName", vmodalname);
                         userdataMap.put("VehicleCC", vehicc);
                         userdataMap.put("testdepartment",depdetail);


                         Rootref.child(parentDbName).child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userdataMap)
                                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task) {
                                         if (task.isSuccessful()) {
                                             Toast.makeText(getActivity(), "Congratulations, your account has been updated.", Toast.LENGTH_SHORT).show();
                                             loadingdbar.dismiss();
                                             Intent intent = new Intent(getActivity(), LoginActivity.class);
                                             startActivity(intent);


                                         } else {
                                             loadingdbar.dismiss();
                                             Toast.makeText(getActivity(), "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                         }
                                     }
                                 });

                     }

             }

                 @Override
                 public void onCancelled (@NonNull DatabaseError databaseError){

                 }

             });







}
}
