// Federico Sanna (65614)

package com.example.esercitazionebonus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GestisciUtentiActivity extends AppCompatActivity {
    EditText cercaUtente;
    LinearLayout linearLayout;
    List<String> usernames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestisci_utenti);

        cercaUtente = findViewById(R.id.cerca_utente);
        Button home = findViewById(R.id.button_home);
        home.setOnClickListener(v -> finish());

        linearLayout = findViewById(R.id.linear_layout);
        usernames = new ArrayList<>(Database.getUsernames());
        Collections.sort(usernames);

        showUsers();

        cercaUtente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { showUsers(); }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    protected void showUsers() {
        linearLayout.removeAllViews();

        for (String username: usernames) {
            if (cercaUtente.getText().toString().length() == 0 || username.contains(cercaUtente.getText().toString())) {
                LinearLayout.LayoutParams params;
                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));

                Space space = new Space(this);
                space.setLayoutParams(params);
                linearLayout.addView(space);

                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                TextView usernameTextView = new TextView(this);
                params.weight = 1;
                usernameTextView.setLayoutParams(params);
                usernameTextView.setText(username);
                usernameTextView.setTextColor(ContextCompat.getColor(this, R.color.black));
                usernameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);

                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                Button adminButton = new Button(this);
                params.weight = 3;
                adminButton.setLayoutParams(params);
                ViewCompat.setBackgroundTintList(adminButton, ContextCompat.getColorStateList(this, R.color.blue));
                adminButton.setAllCaps(false);
                adminButton.setTextColor(ContextCompat.getColor(this, R.color.white));
                adminButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

                if (Database.isAdmin(username)) {
                    adminButton.setText(R.string.admin);
                    adminButton.setEnabled(false);
                    adminButton.setFocusable(false);
                    adminButton.setClickable(false);
                    adminButton.setOnClickListener(null);
                } else {
                    adminButton.setText(R.string.rendi_admin);
                    adminButton.setOnClickListener(v -> {
                        Database.setAdmin(username);
                        adminButton.setText(R.string.admin);
                        adminButton.setEnabled(false);
                        adminButton.setFocusable(false);
                        adminButton.setClickable(false);
                        adminButton.setOnClickListener(null);
                    });
                }

                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                LinearLayout userLinearLayout = new LinearLayout(this);
                userLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                userLinearLayout.setLayoutParams(params);

                userLinearLayout.addView(usernameTextView);
                userLinearLayout.addView(adminButton);

                linearLayout.addView(userLinearLayout);
            }
        }
    }
}