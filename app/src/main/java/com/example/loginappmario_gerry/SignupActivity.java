package com.example.loginappmario_gerry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    // Variáveis para os componentes da UI
    EditText nameEt, churchEt, phoneEt, emailEt, passEt;
    Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameEt = findViewById(R.id.nameEt);
        churchEt = findViewById(R.id.editTextChurch);
        phoneEt = findViewById(R.id.editTextPhone);
        emailEt = findViewById(R.id.editTextEmail_signup);
        passEt = findViewById(R.id.editTextPassword_signup);
        signupBtn = findViewById(R.id.signup_btn);

        signupBtn.setOnClickListener(v -> fazerCadastro());
    }

    private void fazerCadastro() {
        String name = nameEt.getText().toString().trim();
        String church = churchEt.getText().toString().trim();
        String phone = phoneEt.getText().toString().trim();
        String email = emailEt.getText().toString().trim();
        String pass = passEt.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Preencha Nome, Email e Password.", Toast.LENGTH_SHORT).show();
            return;
        }

        salvarCredenciais(name, church, phone, email, pass);
    }

    private void salvarCredenciais(String name, String church, String phone, String email, String pass) {
        // Usar as mesmas SharedPreferences que o MainActivity espera
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("name", name);
        editor.putString("church", church);
        editor.putString("phone", phone);
        editor.putString("email", email);
        editor.putString("password", pass);

        editor.apply();

        Toast.makeText(this, "Cadastro efetuado! Faça login agora.", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}