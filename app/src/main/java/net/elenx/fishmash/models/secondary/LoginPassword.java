package net.elenx.fishmash.models.secondary;

public class LoginPassword implements JSON
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
