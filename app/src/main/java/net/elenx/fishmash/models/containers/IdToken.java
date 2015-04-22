package net.elenx.fishmash.models.containers;

public class IdToken
{
    private final long user_id;
    private final String token;

    public IdToken(long user_id, String token)
    {
        this.user_id = user_id;
        this.token = token;
    }

    public String toJson()
    {
        return "\"id\": \"" + user_id + "\", \"api_token\": " + token + "\"";
    }
}
