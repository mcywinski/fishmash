package net.elenx.fishmash.models.adapters;

import com.google.gson.annotations.SerializedName;

// it's used by spring-android
// and cannot be local, because we will serialize it to json
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class ExamAnswer
{
    @SerializedName("answer_id")
    private final long answerId;

    @SerializedName("answer_text")
    private final String answerText;

    public ExamAnswer(long answerId, String answerText)
    {
        this.answerId = answerId;
        this.answerText = answerText;
    }
}
