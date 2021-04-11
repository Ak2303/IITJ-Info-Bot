package com.example.iitjinfobot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    
    private TextView userInfo;
    private Button signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        String personName = getIntent().getExtras().get("name").toString();
        String personGivenName = getIntent().getExtras().get("given_name").toString();
        String personFamilyName = getIntent().getExtras().get("family_name").toString();
        String personEmail = getIntent().getExtras().get("email").toString();
        String personId = getIntent().getExtras().get("id").toString();

        userInfo = findViewById(R.id.user_info);
        signOut = findViewById(R.id.sign_out);

        userInfo.setText(
                "Name: " + personName + "\n" +
                        "Given name: " + personGivenName + "\n" +
                        "Family name: " + personFamilyName + "\n" +
                        "Email: " + personEmail + "\n" +
                        "Id: " + personId
        );

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(loginIntent);
                                    finish();
                                }
                                else{
                                    String error = task.getException().getMessage();
                                    Toast.makeText(MainActivity.this, "Error: "+error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}