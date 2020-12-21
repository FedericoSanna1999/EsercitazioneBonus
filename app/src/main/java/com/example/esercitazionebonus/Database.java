// Federico Sanna (65614)

package com.example.esercitazionebonus;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class Database {
    private final static HashMap<String, User> database = new LinkedHashMap<>();

    public static void putUser(User user) { database.put(user.getUsername(), user); }

    public static boolean containsUsername(String username) { return database.containsKey(username); }

    public static User getUser(String username) { return database.get(username); }

    public static String getPassword(String username) {
        User user = database.get(username);

        if (user != null)
            return user.getPassword();

        return null;
    }

    public static void replacePassword(String username, String password) {
        User user = database.remove(username);

        if (user != null) {
            user.setPassword(password);
            database.put(username, user);
        }
    }

    public static Set<String> getUsernames() { return database.keySet(); }

    public static boolean isAdmin(String username) {
        User user = database.get(username);

        return user != null && user.isAdmin();
    }

    public static void setAdmin(String username) {
        User user = database.remove(username);

        if (user != null) {
            user.setAdmin(true);
            database.put(username, user);
        }
    }

}
