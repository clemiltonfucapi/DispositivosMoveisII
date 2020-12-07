package com.clemiltonvp.whatsappclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.clemiltonvp.whatsappclone.R;
import com.clemiltonvp.whatsappclone.config.ConfiguracaoFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText editEmail,editSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.editLoginEmail);
        editSenha = findViewById(R.id.editLoginSenha);
        
        /*Comando para fazer Logout */
        ConfiguracaoFirebase.getFirebaseAuth().signOut();

    }


    @Override
    protected void onStart() {
        super.onStart();
        if(ConfiguracaoFirebase.getFirebaseAuth().getCurrentUser()!=null){
            abrirTelaPrincipal();
        }
    }

    public void logarUsuario(View view){
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        /*
        Primeira forma
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

            }
        }); */

        /* Segunda forma - Utilizando classe ConfiguracaoFirebase */
        ConfiguracaoFirebase.signWithEmailAndPassword(getApplicationContext(), email, senha,
        new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){//consegui fazer o login
                    abrirTelaPrincipal();
                }else{//nao consegui fazer o login
                    ConfiguracaoFirebase.verifyLoginException(getApplicationContext(),task);
                }
            }
        });
    }
    public void abrirTelaCadastro(View view){
        Intent intent = new Intent(LoginActivity.this,CadastroActivity.class);
        startActivity(intent);
    }

    public void abrirTelaPrincipal(){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
