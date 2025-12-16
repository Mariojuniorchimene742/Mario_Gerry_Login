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
    EditText nameEt, emailEt, passEt;
    Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Certifique-se de que o nome do layout XML está correto (activity_signup)
        setContentView(R.layout.activity_signup);

        // 1. Conectar as variáveis aos IDs no XML
        nameEt = findViewById(R.id.nameEt);   // ID do campo Nome
        emailEt = findViewById(R.id.emailEt); // ID do campo Email
        passEt = findViewById(R.id.passEt);   // ID do campo Senha
        signupBtn = findViewById(R.id.signup_btn); // ID do botão Criar Conta (signup_btn)

        // 2. Implementar a Ação do Botão
        signupBtn.setOnClickListener(v -> {
            fazerCadastro();
        });
    }

    private void fazerCadastro() {
        String name = nameEt.getText().toString().trim();
        String email = emailEt.getText().toString().trim();
        String pass = passEt.getText().toString().trim();

        // 3. Validação Básica
        if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos para se cadastrar.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 4. Salvar os dados
        salvarCredenciais(name, email, pass);
    }

    private void salvarCredenciais(String name, String email, String pass) {
        // Usa "PREFS_USER" como nome do arquivo SharedPreferences
        SharedPreferences prefs = getSharedPreferences("PREFS_USER", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Salva as credenciais do usuário.
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("password", pass);

        editor.apply();

        Toast.makeText(this, "Cadastro efetuado! Faça login agora.", Toast.LENGTH_LONG).show();

        // 5. Redirecionar para a tela de Login (MainActivity)
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Finaliza a tela de Cadastro
    }
}