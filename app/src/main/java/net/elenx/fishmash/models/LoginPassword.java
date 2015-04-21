package net.elenx.fishmash.models;

public class LoginPassword
{
    private String login;
    private String password;

    public LoginPassword(String login, String password)
    {
        this.login = login;
        this.password = password;
    }

    public String toJson()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"user\": {\"login\": \"");
        stringBuilder.append(login);
        stringBuilder.append("\", \"password\": \"");
        stringBuilder.append(password);
        stringBuilder.append("\"}}");

        return stringBuilder.toString();
    }
}
