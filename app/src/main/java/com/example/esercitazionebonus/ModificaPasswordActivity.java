// Federico Sanna (65614)

package com.example.esercitazionebonus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ModificaPasswordActivity extends AppCompatActivity {
    TextView username, password, nuovaPassword, confermaPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_password);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        nuovaPassword = findViewById(R.id.nuova_password);
        confermaPassword = findViewById(R.id.conferma_password);
        Button aggiornaPassword = findViewById(R.id.button_aggiorna_password);
        Button home = findViewById(R.id.button_home);

        User user = Database.getUser(getIntent().getStringExtra("username"));

        if (user != null) {
            username.setText(user.getUsername());
            password.setText(user.getPassword());
        } else finish();

        aggiornaPassword.setOnClickListener(v -> { if (isValidUpdate()) finish(); });

        home.setOnClickListener(v -> finish());
    }

    protected boolean isValidUpdate() {
        boolean validUpdate = true;

        if (nuovaPassword.getText().length() == 0 || nuovaPassword.getText().toString().equals(password.getText().toString())) {
            nuovaPassword.setError("Inserisci una nuova password diversa dalla password attuale");
            validUpdate = false;
        } else password.setError(null);

        if (confermaPassword.getText().toString().length() == 0 || !confermaPassword.getText().toString().equals(nuovaPassword.getText().toString())) {
            confermaPassword.setError("Inserisci la nuova password per confermare");
            validUpdate = false;
        } else confermaPassword.setError(null);

        if (validUpdate)
            Database.replacePassword(username.getText().toString(), nuovaPassword.getText().toString());

        return validUpdate;
    }
}