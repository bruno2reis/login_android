package com.example.bruno.login.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bruno.login.DAO.ConfiguracaoFireBase;
import com.example.bruno.login.Entidades.Usuarios;
import com.example.bruno.login.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btnLogar;
    private EditText editEmail;
    private EditText editSenha;
    private FirebaseAuth autenticacao;
    private Usuarios usuarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editSenha = (EditText) findViewById(R.id.editSenha);
        btnLogar = (Button) findViewById(R.id.btnLogar);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editEmail.getText().toString().equals("") && !editSenha.getText().toString().equals("")){
                    usuarios = new Usuarios();
                    usuarios.setEmail(editEmail.getText().toString());
                    usuarios.setSenha(editSenha.getText().toString());
                    validarLogin();
                }else{
                    Toast.makeText(MainActivity.this, "Preencha os campos de e-mail e senha!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void validarLogin(){
        autenticacao = ConfiguracaoFireBase.getFirbaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuarios.getEmail(), usuarios.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirTelaPrincipal();
                    Toast.makeText(MainActivity.this, "Login efetuado com sucesso.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Usuario ou senha invalida.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void abrirTelaPrincipal(){
        Intent intentAbrirTelaPrincipal = new Intent(MainActivity.this, PrincipalActivity.class);
        startActivity(intentAbrirTelaPrincipal);
    }
}
