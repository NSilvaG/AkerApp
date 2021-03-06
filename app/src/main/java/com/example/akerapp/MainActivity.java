package com.example.akerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button btnChangeEmail, btnChangePassword, btnSendResetEmail, btnRemoveUser,
            changeEmail, changePassword, sendEmail, remove, signOut;
    private EditText oldEmail, newEmail, password, newPassword;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.welcome));

        auth = FirebaseAuth.getInstance();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user == null){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        btnChangeEmail = (Button) findViewById(R.id.change_email_button);
        btnChangePassword = (Button) findViewById(R.id.change_password_button);
        btnSendResetEmail = (Button) findViewById(R.id.sending_pass_reset_button);
        btnRemoveUser = (Button) findViewById(R.id.remove_user_button);
        changeEmail = (Button) findViewById(R.id.changeEmail);
        changePassword = (Button) findViewById(R.id.changePass);
        sendEmail = (Button) findViewById(R.id.send);
        remove = (Button) findViewById(R.id.remove);
        signOut = (Button) findViewById(R.id.sign_out);

        oldEmail = (EditText) findViewById(R.id.old_email);
        newEmail = (EditText) findViewById(R.id.new_email);
        password = (EditText) findViewById(R.id.password);
        newPassword = (EditText) findViewById(R.id.newPassword);

        oldEmail.setVisibility(View.VISIBLE);
        newEmail.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        newPassword.setVisibility(View.VISIBLE);
        changeEmail.setVisibility(View.VISIBLE);
        changePassword.setVisibility(View.VISIBLE);
        sendEmail.setVisibility(View.VISIBLE);
        remove.setVisibility(View.VISIBLE);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progressBar != null){
            progressBar.setVisibility(View.GONE);
        }

        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.VISIBLE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.VISIBLE);
                changePassword.setVisibility(View.GONE);
                sendEmail.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);
            }
        });

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                if (user != null && !newEmail.getText().toString().trim().equals("")){
                    user.updateEmail(newEmail.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "El correo electr??nico ha sido actualizado. Por favor inicie sesi??n con el nuevo correo electr??nico", Toast.LENGTH_SHORT).show();
                                signOut();
                                progressBar.setVisibility(View.GONE);
                            }else{
                                Toast.makeText(MainActivity.this, "No se pudo actualizar el correo electr??nico", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }else if(newEmail.getText().toString().trim().equals("")){
                    newEmail.setError("Ingrese su correo electr??nico");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.VISIBLE);
                changeEmail.setVisibility(View.GONE);
                changePassword.setVisibility(View.VISIBLE);
                sendEmail.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                if (user != null && !newPassword.getText().toString().trim().equals("")){
                    if (newPassword.getText().toString().trim().length() < 6){
                        newPassword.setError("Contrase??a demasiado corta, ingrese un m??nimo de 6 caracteres");
                        progressBar.setVisibility(View.GONE);
                    }else{
                        user.updatePassword(newPassword.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(MainActivity.this, "La contrase??a ha sido actualizada. Por favor inicie sesi??n con la nueva contrase??a", Toast.LENGTH_SHORT).show();
                                    signOut();
                                    progressBar.setVisibility(View.GONE);
                                }else {
                                    Toast.makeText(MainActivity.this, "Error al actualizar la contrase??a", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });

                    }
                }else if (newPassword.getText().toString().trim().equals("")){
                    newPassword.setError("Ingrese su contrase??a");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

         btnSendResetEmail.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 oldEmail.setVisibility(View.VISIBLE);
                 newEmail.setVisibility(View.GONE);
                 password.setVisibility(View.GONE);
                 newPassword.setVisibility(View.GONE);
                 changeEmail.setVisibility(View.GONE);
                 changePassword.setVisibility(View.GONE);
                 sendEmail.setVisibility(View.VISIBLE);
                 remove.setVisibility(View.GONE);
             }
         });

         sendEmail.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 progressBar.setVisibility(View.VISIBLE);

                 if (!oldEmail.getText().toString().trim().equals("")){
                     auth.sendPasswordResetEmail(oldEmail.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if (task.isSuccessful()){
                                 Toast.makeText(MainActivity.this, "Se env??o un correo electr??nico para restablecimiento de su contrase??a", Toast.LENGTH_SHORT).show();
                                 progressBar.setVisibility(View.GONE);
                             }else{
                                 Toast.makeText(MainActivity.this, "Error al enviar correo electr??nico de restablecimiento", Toast.LENGTH_SHORT).show();
                                 progressBar.setVisibility(View.GONE);
                             }
                         }
                     });
                 }else{
                     oldEmail.setError("Ingrese su correo electr??nico");
                     progressBar.setVisibility(View.GONE);
                 }
             }
         });

         btnRemoveUser.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 progressBar.setVisibility(View.VISIBLE);

                 if (user != null){
                     user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if (task.isSuccessful()){
                                 Toast.makeText(MainActivity.this, "Tu cuenta ha sido eliminada", Toast.LENGTH_SHORT).show();
                                 startActivity(new Intent(MainActivity.this, SignupActivity.class));
                                 finish();
                                 progressBar.setVisibility(View.GONE);
                             }else{
                                 Toast.makeText(MainActivity.this, "No se ha eliminado la cuenta", Toast.LENGTH_SHORT).show();
                                 progressBar.setVisibility(View.GONE);
                             }
                         }
                     });

                 }
             }
         });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    public void signOut(){
        auth.signOut();
    }

    @Override
    protected void onResume(){
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart(){
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if (authListener != null){
            auth.removeAuthStateListener(authListener);
        }
    }
}