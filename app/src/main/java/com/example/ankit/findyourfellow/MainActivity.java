package com.example.ankit.findyourfellow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    protected Button createProfileButton;
    protected Button loginToProfileButton;

    private EditText EmailField;
    private EditText PassworField;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Find Your Friends");

        mAuth = FirebaseAuth.getInstance();

        EmailField = (EditText) findViewById(R.id.emailField);
        PassworField = (EditText) findViewById(R.id.passwordField);

        createProfileButton = (Button) findViewById(R.id.newCreateButton);
        loginToProfileButton = (Button) findViewById(R.id.loginButton);


        createProfileButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                goToCreateActivity();
            }
        });

        loginToProfileButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startLogin();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener()
        {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                if(firebaseAuth.getCurrentUser() != null)
                {

                    goToAccountActivity();
                    // startActivity(new Intent(LoginActivity.this, AccountActivity.class));
                }
            }
        };



    }

   //////////////////for testing ---removed after ////////////////////
    public void goToLocationActivity(View v)
    {
        Intent intent = new Intent(this, testLocationActivity.class);
        startActivity(intent);
    }
///////////////////////////////////end/////////////////////////////


    void goToCreateActivity()
    {
        Intent intent = new Intent(MainActivity.this, CreateProfileActivity.class);
        startActivity(intent);
    }

    void goToAccountActivity()
    {
        Intent intent = new Intent(MainActivity.this, AccountActivity.class);
        startActivity(intent);
    }

    protected void onStart()
    {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    private void startLogin()
    {
        String email = EmailField.getText().toString();
        String password = PassworField.getText().toString();

        if(!(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)))
        {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (!(task.isSuccessful()))
                        Toast.makeText(MainActivity.this, "Sign In Problem", Toast.LENGTH_LONG).show();
                    else
                    {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        if (user.isEmailVerified())
                        {
                            // user is verified, so you can finish this activity or send user to activity which you want.
                            finish();
                            Toast.makeText(MainActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            // email is not verified, so just prompt the message to the user and restart this activity.
                            FirebaseAuth.getInstance().signOut();

                            Toast.makeText(MainActivity.this, "Email not verified", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                            //restart this activity

                        }

                    }
                }
            });
        }
        else
            Toast.makeText(MainActivity.this, "Field(s) is/are empty", Toast.LENGTH_LONG).show();

    }




    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

}
