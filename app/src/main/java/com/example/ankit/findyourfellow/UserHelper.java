package com.example.ankit.findyourfellow;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Created by Ankit on 2017-03-13.
 */

public class UserHelper {

    private String firstName;
    private String lastName;
    private String age;
    private String email;
    private String password;
    private String phoneNumber;

    UserHelper(String uFName, String uLName, String uAge, String uEmail, String uPassword, String uPhoneNumber) {
        firstName = uFName;
        lastName = uLName;
        age = uAge;
        email = uEmail;
        password = uPassword;
        phoneNumber = uPhoneNumber;
    }




}
