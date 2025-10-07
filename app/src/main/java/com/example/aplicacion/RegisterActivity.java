package com.example.aplicacion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aplicacion.utils.ValidationUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Activity for user registration with email and password.
 * Handles new user account creation and stores user data in Firebase Realtime Database.
 */
public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;

    private TextView alreadyHaveAccount;
    private EditText inputEmail, inputPassword, inputConfirmPassword;
    private Button btnRegister;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance(BuildConfig.FIREBASE_DATABASE_URL).getReference();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // Para encontrar widgets y botones por id
        alreadyHaveAccount=findViewById(R.id.alreadyHaveAccount);
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        inputConfirmPassword=findViewById(R.id.inputPassword2);
        btnRegister=findViewById(R.id.btnRegister);

        progressDialog=new ProgressDialog(this);

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuthentication();
            }
        });

    }

    /**
     * Validates user input and performs authentication to create a new account.
     * Validates email format, password length, and password confirmation match.
     * Uses ValidationUtils for consistent validation across the application.
     */
    private void performAuthentication() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();

        // Validate email using ValidationUtils
        if (!ValidationUtils.isValidEmail(email)) {
            inputEmail.setError("Introduzca un email correcto");
            inputEmail.requestFocus();
        } 
        // Validate password using ValidationUtils
        else if (!ValidationUtils.isValidPassword(password)) {
            inputPassword.setError("La contraseña debe tener entre 6 y 20 caracteres");
            inputPassword.requestFocus();
        } 
        // Validate password match using ValidationUtils
        else if (!ValidationUtils.passwordsMatch(password, confirmPassword)) {
            inputConfirmPassword.setError("Las contraseñas no coinciden");
            inputConfirmPassword.requestFocus();
        } 
        else {
            progressDialog.setMessage("Registro en progreso...");
            progressDialog.setTitle("Registro");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Map<String,Object> map = new HashMap<>();
                        map.put("nombre","nombre");
                        map.put("apellidos","apellidos");
                        map.put("correo",email);
                        map.put("edad","edad");

                        String id = firebaseAuth.getCurrentUser().getUid();
                        //databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task2) {
                                if(task2.isSuccessful()){
                                    progressDialog.dismiss();
                                    sendUserToNextActivity();
                                    Toast.makeText(RegisterActivity.this,"Registro completado",Toast.LENGTH_SHORT).show();
                                }else{
                                    progressDialog.dismiss();
                                    Toast.makeText(RegisterActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }


            });
        }

    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(RegisterActivity.this,MainActivityBottomNav.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}