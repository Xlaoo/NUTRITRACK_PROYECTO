package com.example.proyecto_nutritrak;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "NutriTrakSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_APELLIDO = "apellido";
    private static final String KEY_CORREO = "correo";
    private static final String KEY_ROL = "rol";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(int userId, String nombre, String apellido, String correo, String rol) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_NOMBRE, nombre);
        editor.putString(KEY_APELLIDO, apellido);
        editor.putString(KEY_CORREO, correo);
        editor.putString(KEY_ROL, rol);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1);
    }

    public String getNombre() {
        return sharedPreferences.getString(KEY_NOMBRE, "");
    }

    public String getApellido() {
        return sharedPreferences.getString(KEY_APELLIDO, "");
    }

    public String getCorreo() {
        return sharedPreferences.getString(KEY_CORREO, "");
    }

    public String getRol() {
        return sharedPreferences.getString(KEY_ROL, "usuario");
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
