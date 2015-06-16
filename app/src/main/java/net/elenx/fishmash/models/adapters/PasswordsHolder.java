package net.elenx.fishmash.models.adapters;

import com.google.gson.annotations.SerializedName;

// variables are used by spring-android
@SuppressWarnings("unused")
public class PasswordsHolder
{
    @SerializedName("password_old")
    private final String oldPassword;

    @SerializedName("password")
    private final String newPassword;

    @SerializedName("password_confirmation")
    private final String passwordConfirmation;

    public PasswordsHolder(String oldPassword, String newPassword, String passwordConfirmation)
    {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.passwordConfirmation = passwordConfirmation;
    }
}
