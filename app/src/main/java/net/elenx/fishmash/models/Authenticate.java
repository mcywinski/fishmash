package net.elenx.fishmash.models;

public class Authenticate
{
    private long id;
    private String token;
    private long user_id;
    private String created_at;
    private String updated_at;

    public long getId()
    {
        return id;
    }

    public String getToken()
    {
        return token;
    }

    public long getUser_id()
    {
        return user_id;
    }

    public String getCreated_at()
    {
        return created_at;
    }

    public String getUpdated_at()
    {
        return updated_at;
    }
}
