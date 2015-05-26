package net.elenx.fishmash;

public abstract class Constant
{
    public static final String DATABASE_NAME = "fishmash";

    public static final String SERVER = "http://shrouded-fjord-4731.herokuapp.com/";

    public static final String REGISTER = SERVER + "users/register";
    public static final String API = SERVER + "api/";

    public static final String AUTHENTICATE = API + "users/authenticate";
    public static final String LISTS = API + "lists/";
    public static final String USERS = API + "users/";
    public static final String EXAMS = API + "exams/";
}
