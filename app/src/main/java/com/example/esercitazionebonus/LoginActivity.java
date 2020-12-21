// Federico Sanna (65614)

package com.example.esercitazionebonus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    private EditText username, password;
    private Button credenzialiNonValide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            Calendar birthDate = Calendar.getInstance();
            Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse("01/01/2000");

            if (date != null) {
                birthDate.setTime(date);
                User admin = new User(true, "admin", "admin", "Roma", birthDate);
                Database.putUser(admin);
            }
        } catch (ParseException e) { e.printStackTrace(); }

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        Button accedi = findViewById(R.id.button_accedi);
        TextView nuovoUtenteRegistrati = findViewById(R.id.link_nuovo_utente_registrati);
        credenzialiNonValide = findViewById(R.id.error_credenziali_non_valide);

        accedi.setOnClickListener(v -> {
            if (isValidLogin()) {
                Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                homeIntent.putExtra("username", username.getText().toString());
                startActivity(homeIntent);
            }
        });

        nuovoUtenteRegistrati.setOnClickListener(v -> {
            Intent registrazioneActivity = new Intent(LoginActivity.this, RegistrazioneActivity.class);
            startActivity(registrazioneActivity);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        username.setText(null);
        username.setError(null);
        password.setText(null);
        password.setError(null);
        credenzialiNonValide.setVisibility(View.GONE);
    }

    protected boolean isValidLogin() {
        boolean validLogin = true;

        if (username.getText().toString().length() == 0) {
            username.setError("Inserisci l'username");
            validLogin = false;
        } else username.setError(null);

        if (password.getText().toString().length() == 0) {
            password.setError("Inserisci la password");
            validLogin = false;
        } else password.setError(null);

        if (validLogin)
            if (Database.containsUsername(username.getText().toString()) && password.getText().toString().equals(Database.getPassword(username.getText().toString())))
                credenzialiNonValide.setVisibility(View.GONE);
            else {
                credenzialiNonValide.setVisibility(View.VISIBLE);
                validLogin = false;
            }
        else credenzialiNonValide.setVisibility(View.GONE);

        return validLogin;
    }
}