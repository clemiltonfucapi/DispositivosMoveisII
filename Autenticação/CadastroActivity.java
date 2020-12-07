package com.clemiltonvp.whatsappclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.clemiltonvp.whatsappclone.R;
import com.clemiltonvp.whatsappclone.config.ConfiguracaoFirebase;
import com.clemiltonvp.whatsappclone.extra.Chain;
import com.clemiltonvp.whatsappclone.extra.IsEmptyStrChain;
import com.clemiltonvp.whatsappclone.helper.Base64Custom;
import com.clemiltonvp.whatsappclone.helper.UsuarioFirebase;
import com.clemiltonvp.whatsappclone.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroActivity extends AppCompatActivity {

    private TextInputEditText campoNome, campoEmail,campoSenha;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        campoNome=findViewById(R.id.inputCadastroNome);
        campoEmail=findViewById(R.id.inputCadastroEmail);
        campoSenha=findViewById(R.id.inputCadastroSenha);
        auth = ConfiguracaoFirebase.getFirebaseAuth();
    }

    public void cadastrarUsuario(Usuario usuario){
        auth.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Sucesso ao cadastrar Usuario!",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            /* Video 14 */
                            String excecao="";
                            try{
                                throw  task.getException();
                            }catch(FirebaseAuthWeakPasswordException e){
                                excecao = "Digite uma senha mais forte";
                            }catch(FirebaseAuthInvalidCredentialsException e){
                                excecao = "Crendenciais inválidas";
                            }catch(FirebaseAuthUserCollisionException e){
                                excecao = "Esta conta já foi cadastrada";
                            }catch(Exception e){
                                excecao = "Erro ao cadastrar usuário "+e.getMessage();
                                e.printStackTrace();
                            }finally {
                                Toast.makeText(getApplicationContext(),excecao,Toast.LENGTH_SHORT).show();
                            }
                            /* ------------------------------ */ 
                            /* Video 15 */
                            ConfiguracaoFirebase.verifyLoginException(getApplicationContext(), task);

                        }
                    }
                });


    }

    public void validarUsuario(View view){

        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();
        
        if(!nome.isEmpty()){//verificando o nome
            if(!email.isEmpty()){//verificando email
                if(!senha.isEmpty()){
                    Usuario usuario = new Usuario(nome,email,senha);
                    cadastrarUsuario(usuario);
                }else{
                    Toast.makeText(getApplicationContext(),"Preencha a senha!",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Preencha a email!",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Preencha o nome!",Toast.LENGTH_SHORT).show();
        }

    }
}
