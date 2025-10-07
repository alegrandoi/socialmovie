package com.example.aplicacion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Activity for password reset functionality.
 * Allows users to request a password reset email from Firebase Authentication.
 */
public class ResetPasswordActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private Button buttonResetPassword, buttonBack;
    private String emailForReset = "";
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer_pass);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        editTextEmail = findViewById(R.id.inputEmailRestablecer);
        buttonResetPassword = findViewById(R.id.btnRestablecer);
        buttonBack = findViewById(R.id.btnAtras);

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailForReset = editTextEmail.getText().toString();
                if(!emailForReset.isEmpty()){
                    progressDialog.setMessage("Espere un momento...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    resetPassword();
                }else{
                    Toast.makeText(ResetPasswordActivity.this,"Debe ingresar email",Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResetPasswordActivity.this, MainActivity.class));
            }
        });
    }

    /**
     * Sends password reset email to the provided email address.
     * Uses Firebase Authentication to send the reset link.
     */
    private void resetPassword() {
        firebaseAuth.setLanguageCode("es");
        firebaseAuth.sendPasswordResetEmail(emailForReset).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ResetPasswordActivity.this,"Se ha enviado un correo para restablecer su contraseña",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ResetPasswordActivity.this,"No se pudo enviar un correo para restablecer su contraseña",Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }
}