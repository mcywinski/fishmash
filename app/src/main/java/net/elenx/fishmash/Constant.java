package net.elenx.fishmash;

public abstract class Constant
{
    // start layer 0
    private static final String SERVER = "http://shrouded-fjord-4731.herokuapp.com/";
    private static final String TOKEN = "?api_token={api_token}";
    public static final String DATABASE_NAME = "fishmash";
    public static final int TIMEOUT_IN_SECONDS = 50;
    // end layer 0

    // start layer 1
    private static final String API = SERVER + "api/";
    public static final String REGISTER = SERVER + "users/register";
    // end layer 1

    // start layer 2
    private static final String USERS = API + "users/";
    private static final String EXAMS = API + "exams/";
    public static final String AUTHENTICATE = API + "users/authenticate";
    public static final String LISTS = API + "lists/";
    // end layer 2

    // start layer 3
    private static final String EXAMS_EXAMID = EXAMS + "{exam_id}";
    public static final String LISTS_LISTID = LISTS + "{list_id}";
    public static final String EXAMS_TOKEN = EXAMS + TOKEN;
    public static final String USERS_USERID_TOKEN = USERS + "{user_id}" + TOKEN;
    // end layer 3

    // start layer 4
    public static final String QUESTION_EXAMID_TOKEN = EXAMS_EXAMID + "/get_question" + TOKEN;
    public static final String ANSWER_EXAMID_TOKEN = EXAMS_EXAMID + "/answer" + TOKEN;
    public static final String SUMMARY_EXAMID_TOKEN = EXAMS_EXAMID + "/summary" + TOKEN;
    // end layer 4
}
