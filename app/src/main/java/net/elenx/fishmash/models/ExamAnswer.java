package net.elenx.fishmash.models;

import net.elenx.fishmash.models.secondary.JSON;

/**
{
  answerId: 37,
  answerText: 'pies'
}
 */
public class ExamAnswer extends FishmashModel implements JSON
{
    private final long answerId;
    private final String answerText;

    public ExamAnswer(long answerId, String answerText)
    {
        this.answerId = answerId;
        this.answerText = answerText;
    }

    public String toJson()
    {
        return "{ answer_id: " + answerId + ", answer_text: '" + answerText + "' }";
    }
}
