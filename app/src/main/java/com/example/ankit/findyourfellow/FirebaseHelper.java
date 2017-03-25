package com.example.ankit.findyourfellow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executor;

/**
 * Created by Ankit on 2017-03-12.
 */

public class FirebaseHelper {
/*
    Context c;

    private Firebase mRootRef;
    private FirebaseAuth mAuth;

    //private static int courseID = 0; //static ID increments with every new Course created

    FirebaseHelper(Context context)
    {
        c = context;
        mAuth = FirebaseAuth.getInstance();

        mRootRef = new Firebase("https://findyourfellow.firebaseio.com/Users");
    }
    //returns a Course instant with random assignment values
    public void UserSignOut()
    {
       mAuth.signOut();
    }

    public void createNewUser(String userFName, String userLName, String userAge, String userEmail, String userPassword, String userPhoneNumber)
    {
        final String fName = userFName;
        final String lName = userLName;
        final String age = userAge;
        final String email = userEmail;
        final String password = userPassword;
        final String phoneNumber = userPhoneNumber;


        if (!(TextUtils.isEmpty(fName) || TextUtils.isEmpty(lName) || TextUtils.isEmpty(age) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||  TextUtils.isEmpty(phoneNumber)))
            mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>()
                {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (!(task.isSuccessful()))
                        {
                            Toast.makeText(c, "Unsuccessful", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            String newUser = mAuth.getCurrentUser().getUid().toString();

                            Firebase childRef = mRootRef.child(newUser);

                            Firebase newRef = new Firebase("https://findyourfellow.firebaseio.com/Users/" + newUser +"/");

                            Firebase firstRef = newRef.child("FirstName");

                            firstRef.setValue(fName);

                            Firebase lastRef = newRef.child("LastName");

                            lastRef.setValue(lName);

                            Firebase ageRef = newRef.child("Age");

                            ageRef.setValue(age);

                            Firebase emailRef = newRef.child("Email");

                            emailRef.setValue(email);

                            Firebase passwordRef = newRef.child("Password");

                            passwordRef.setValue(password);

                            Firebase phoneRef = newRef.child("PhoneNumber");

                            phoneRef.setValue(phoneNumber);

                        }
                    }
                });
        else
        {
        Toast.makeText(c, "Field(s) cannot be left empty", Toast.LENGTH_LONG).show();
        }
    }

    public UserHelper getCurrentUser()
    {
        String currentUser = mAuth.getCurrentUser().getUid().toString();

        UserHelper userHelper;

        Firebase userRef = new Firebase("https://findyourfellow.firebaseio.com/Users/" + currentUser +"/");

        userRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Map<String, String> map = dataSnapshot.getValue(Map.class);

                final UserHelper myUserHelper = new UserHelper(map.get("FirstName"), map.get("LastName"), map.get("Age"), map.get("Email"), map.get("Password"), map.get("PhoneNUmber"));

                //userHelper = new UserHelper(myUserHelper);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });






        return UserHelper(map.get("FirstName"), map.get("LastName"), map.get("Age"), map.get("Email"), map.get("Password"), map.get("PhoneNUmber"));
    }
    */
}