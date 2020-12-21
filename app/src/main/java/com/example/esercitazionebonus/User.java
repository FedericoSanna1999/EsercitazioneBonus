// Federico Sanna (65614)

package com.example.esercitazionebonus;

import java.util.Calendar;

public class User {
    private boolean admin;
    private String username;
    private String password;
    private String hometown;
    private Calendar birthDate;

    public User(boolean admin, String username, String password, String hometown, Calendar birthDate) {
        this.admin = admin;
        this.username = username;
        this.password = password;
        this.hometown = hometown;
        this.birthDate = birthDate;
    }

    public boolean isAdmin() { return admin; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getHometown() { return hometown; }
    public Calendar getBirthDate() { return birthDate; }

    public void setAdmin(boolean admin) { this.admin = admin; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setHometown(String hometown) { this.hometown = hometown; }
    public void setBirthDate(Calendar birthDate) { this.birthDate = birthDate; }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object == null || getClass() != object.getClass())
            return false;

        User user = (User) object;

        return username.equals(user.username);
    }

    @Override
    public int hashCode() { return username.hashCode(); }
}
