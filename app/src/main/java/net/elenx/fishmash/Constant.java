package net.elenx.fishmash;

public abstract class Constant
{
    public static final String DATABASE_NAME = "fishmash";
    public static final int TIMEOUT_IN_SECONDS = 50;
    public static final String SERVER = "http://shrouded-fjord-4731.herokuapp.com/";
    private static final String TOKEN = "?api_token={api_token}";

    public static final String REGISTER = SERVER + "users/register";
    private static final String API = SERVER + "api/";

    public static final String AUTHENTICATE = API + "users/authenticate";
    public static final String LISTS = API + "lists/";
    private static final String USERS = API + "users/";
    private static final String EXAMS = API + "exams/";

    private static final String EXAMS_EXAMID = EXAMS + "{exam_id}";
    public static final String LISTS_LISTID = LISTS + "{list_id}";
    public static final String EXAMS_TOKEN = EXAMS + TOKEN;
    public static final String USERS_USERID_TOKEN = USERS + "{user_id}" + TOKEN;

    public static final String QUESTION_EXAMID_TOKEN = EXAMS_EXAMID + "/get_question" + TOKEN;
    public static final String ANSWER_EXAMID_TOKEN = EXAMS_EXAMID + "/answer" + TOKEN;
    public static final String SUMMARY_EXAMID_TOKEN = EXAMS_EXAMID + "/summary" + TOKEN;
}
