package net.elenx.fishmash.models.containers;

public class UserIdToken
{
    private long user_id;
    private String token;

    public UserIdToken(long user_id, String token)
    {
        this.user_id = user_id;
        this.token = token;
    }

    public String toJson()
    {
        return "\"id\": \"" + user_id + "\", \"token\": " + token + "\"";
    }
}
