package com.example.loginappmario_gerry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText emailEt;
    EditText passEt;
    Button loginBtn;
    Button signupBtnMain;
    private static final String HARDCODE_EMAIL = "admin@igrejas.com";
    private static final String HARDCODE_PASS = "Senha@123";
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEt = findViewById(R.id.editTextemailAddress);
        passEt = findViewById(R.id.editTextPassword);
        loginBtn = findViewById(R.id.login_btn);
        signupBtnMain = findViewById(R.id.signup_btn_main);
        // (Opcional) criar conta de exemplo na primeira execução
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        boolean seeded = prefs.getBoolean("seeded", false);
        if (!seeded) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("name", "ContaDemo");
            editor.putString("email", "demo@exemplo.com");
            editor.putString("password", "");
            editor.putBoolean("seeded", true);
            editor.apply();
        }
        // pré-preenchimento removido para uso normal (digite suas credenciais)
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fazerLogin();
            }
        });

        signupBtnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });

        // Autofill automático da senha quando o email corresponde ao admin fixo
        emailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                String inputEmail = s.toString().trim();
                if (inputEmail.isEmpty()) return;
                String currentPass = passEt.getText().toString();
                if (!currentPass.isEmpty()) return; // não sobrescrever senha já digitada

                // Se for conta fixa
                if (inputEmail.equalsIgnoreCase(HARDCODE_EMAIL)) {
                    passEt.setText(HARDCODE_PASS);
                    return;
                }

                // Verifica SharedPreferences (conta criada via Signup)
                SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                String savedEmail = prefs.getString("email", "");
                String savedPass = prefs.getString("password", "");
                if (!savedPass.isEmpty() && inputEmail.equalsIgnoreCase(savedEmail)) {
                    passEt.setText(savedPass);
                }
            }
        });

        // Autofill imediato ao abrir a activity: preenche credenciais do admin
        String startEmail = emailEt.getText().toString().trim();
        if (startEmail.isEmpty()) {
            // preencher com conta admin por conveniência
            emailEt.setText(HARDCODE_EMAIL);
            passEt.setText(HARDCODE_PASS);
        } else {
            // se já houver email preenchido, tentar preencher a senha correspondente
            if (startEmail.equalsIgnoreCase(HARDCODE_EMAIL)) {
                passEt.setText(HARDCODE_PASS);
            } else {
                SharedPreferences prefs2 = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                String savedEmail2 = prefs2.getString("email", "");
                String savedPass2 = prefs2.getString("password", "");
                if (!savedPass2.isEmpty() && startEmail.equalsIgnoreCase(savedEmail2)) {
                    passEt.setText(savedPass2);
                }
            }
        }
    }

    private void fazerLogin() {
        String email = emailEt.getText().toString().trim();
        String senha = passEt.getText().toString().trim();
        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha email e password.", Toast.LENGTH_SHORT).show();
            return;
        }

        autenticarUsuario(email, senha);
    }

    private void autenticarUsuario(String email, String senha) {
        // 1) Verificar conta fixa definida no código
        Log.d(TAG, "Tentativa de login com email=" + email);
        if (email.equals(HARDCODE_EMAIL) && senha.equals(HARDCODE_PASS)) {
            Log.d(TAG, "Autenticação por conta fixa OK");
            Toast.makeText(this, "Login efetuado com sucesso! (conta fixa)", Toast.LENGTH_SHORT).show();
            iniciarNovaActivity(HomeActivity.class);
            return;
        }

        // 2) Fallback: contas criadas via SignupActivity (SharedPreferences)
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String savedEmail = prefs.getString("email", null);
        String savedPass = prefs.getString("password", null);

        Log.d(TAG, "SavedEmail=" + savedEmail + " savedPassEmpty=" + (savedPass==null || savedPass.isEmpty()));
        if (savedEmail != null && savedPass != null && email.equals(savedEmail) && senha.equals(savedPass)) {
            Log.d(TAG, "Autenticação por SharedPreferences OK");
            Toast.makeText(this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
            iniciarNovaActivity(HomeActivity.class);
        } else {
            Log.d(TAG, "Autenticação falhou");
            Toast.makeText(this, "Email ou Senha incorretos.", Toast.LENGTH_SHORT).show();
        }
    }

    private void iniciarNovaActivity(Class<?> activityClass) {
        Intent intent = new Intent(MainActivity.this, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}