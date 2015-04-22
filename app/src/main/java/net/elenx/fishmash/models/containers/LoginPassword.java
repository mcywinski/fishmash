package net.elenx.fishmash.models.containers;

public class LoginPassword
{
    private final String login;
    private final String password;

    public LoginPassword(String login, String password)
    {
        this.login = login;
        this.password = password;
    }

    public String toJson()
    {
        return "{\"user\": {\"login\": \"" + login + "\", \"password\": \"" + password + "\"}}";
    }
}
