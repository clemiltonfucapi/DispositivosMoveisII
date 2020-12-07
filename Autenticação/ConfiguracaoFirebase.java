package com.clemiltonvp.whatsappclone.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {
    private static DatabaseReference database;
    private static FirebaseAuth auth;

    public static DatabaseReference getFirebaseDatabase(){
        if(ConfiguracaoFirebase.database==null){

            ConfiguracaoFirebase.database = FirebaseDatabase.getInstance().getReference();
        }
        return database;
    }

    public static FirebaseAuth getFirebaseAuth(){
        if(auth==null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }
    
    /* Video 15*/
    public static void signWithEmailAndPassword(Context context,String email,String senha,
                                                OnCompleteListener<AuthResult> listener){
        getFirebaseAuth();
        if(!email.isEmpty() && !senha.isEmpty()){
            auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(listener);
        }else{
            Toast.makeText(context,"Preencha os campos necessários!",Toast.LENGTH_SHORT).show();
        }
    }


    /* Video 15*/
    
    public static void verifyLoginException(Context context, Task<AuthResult> task){
        String excecao = "";
        try{
            throw task.getException();
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
            Toast.makeText(context,excecao,Toast.LENGTH_SHORT).show();
        }
    }



}
