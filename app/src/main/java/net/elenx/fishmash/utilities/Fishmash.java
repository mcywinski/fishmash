package net.elenx.fishmash.utilities;

// todo language resolving with api
// todo layout_exam counter
public abstract class Fishmash
{
    // start layer 0
    public static final String DATABASE_NAME = "fishmash";
    public static final int TIMEOUT_IN_SECONDS = 50;
    public static final String TO = "to ";
    public static final String EXAM_ID = "exam_id";
    public static final String USER_ID = "user_id";
    public static final String API_TOKEN = "api_token";
    public static final String WORD_LIST_ID = "word_list_id";
    private static final String LIST_ID = "list_id";
    private static final String SERVER = "http://shrouded-fjord-4731.herokuapp.com/";
    // end layer 0

    // start layer 1
    public static final String REGISTER = SERVER + "users/register";
    private static final String TOKEN = "?api_token={" + API_TOKEN + "}";
    private static final String API = SERVER + "api/";
    // end layer 1

    // start layer 2
    public static final String LANGUAGES = API + "languages" + TOKEN;
    private static final String LISTS = API + "lists/";
    private static final String USERS = API + "users/";
    private static final String EXAMS = API + "exams/";
    // end layer 2

    // start layer 3
    public static final String AUTHENTICATE = USERS + "authenticate";
    public static final String SET_PASSWORD = USERS + "set_password" + TOKEN;
    public static final String LISTS_TOKEN = LISTS + TOKEN;
    public static final String LISTS_LISTID = LISTS + "{" + LIST_ID + "}";
    public static final String EXAMS_TOKEN = EXAMS + TOKEN;
    public static final String USERS_USERID_TOKEN = USERS + "{" + USER_ID + "}" + TOKEN;
    private static final String EXAMS_EXAMID = EXAMS + "{" + EXAM_ID +"}";
    // end layer 3

    // start layer 4
    public static final String QUESTION_EXAMID_TOKEN = EXAMS_EXAMID + "/get_question" + TOKEN;
    public static final String ANSWER_EXAMID_TOKEN = EXAMS_EXAMID + "/answer" + TOKEN;
    public static final String SUMMARY_EXAMID_TOKEN = EXAMS_EXAMID + "/summary" + TOKEN;
    public static final String EXAMS_EXAMID_START_TOKEN = EXAMS_EXAMID + "/start" + TOKEN;
    // end layer 4
}
