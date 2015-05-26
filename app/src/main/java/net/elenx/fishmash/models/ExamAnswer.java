package net.elenx.fishmash.models;

/**
{
  answerId: 37,
  answerText: 'pies'
}
 */
public class ExamAnswer extends FishmashModel implements JSON
{
    private long answerId;
    private String answerText;

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
