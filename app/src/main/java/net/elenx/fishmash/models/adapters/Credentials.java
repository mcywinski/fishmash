package net.elenx.fishmash.models.adapters;

// it's used by spring-android
// and cannot be local, because we will serialize it to json
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class Credentials
{
    private final String login;
    private final String password;

    public Credentials(String login, String password)
    {
        this.login = login;
        this.password = password;
    }

    public String toJson()
    {
        return "{\"user\":{\"login\":\"" + login + "\",\"password\":\"" + password + "\"}}";
    }
}
