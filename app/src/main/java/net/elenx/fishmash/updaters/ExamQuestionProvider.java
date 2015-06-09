package net.elenx.fishmash.updaters;

import android.util.Log;

import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.models.ExamQuestion;
import net.elenx.fishmash.models.ExamResponse;
import net.elenx.fishmash.models.adapters.ExamAnswer;
import net.elenx.fishmash.updaters.listeners.ExamQuestionListener;
import net.elenx.fishmash.utilities.Fishmash;

import java.util.Map;

public class ExamQuestionProvider extends FishmashUpdater implements Cloneable
{
    private long examId;
    private Map<String, String> parameters;
    private String answer;
    private ExamQuestionListener examQuestionListener;
    private ExamQuestion examQuestion;

    public ExamQuestionProvider(OptionsActivity optionsActivity, long examId) throws Exception
    {
        super(optionsActivity);
        this.examId = examId;

        parameters = buildParameters();
        parameters.put("exam_id", String.valueOf(examId));
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);

        boolean hasExamFinished = examQuestion.getId() == 0 && examQuestion.isExamFinished();

        if(hasExamFinished)
        {
            examQuestionListener.examFinished();
        }
        else
        {
            Log.e("meaning", examQuestion.getMeaning());
            examQuestionListener.prepareQuestion(examQuestion.getMeaning());
        }
    }

    @Override
    void download() throws Exception
    {
        if(answer != null)
        {
            sendAnswer();
        }

        fetchQuestion();
    }

    private void sendAnswer() throws Exception
    {
        ExamAnswer examAnswer = new ExamAnswer(examId, answer);
        ExamResponse examResponse = REST_INTERCEPTOR.postForObject(Fishmash.ANSWER_EXAMID_TOKEN, examAnswer, ExamResponse.class, parameters);

        boolean responseHasBeenSaved = examResponse.isSaved() && examResponse.getMessage().equals("Answer saved");

        if(!responseHasBeenSaved)
        {
            throw new Exception("answer has been NOT saved");
        }
    }

    private void fetchQuestion()
    {
        examQuestion = REST_INTERCEPTOR.postForObject(Fishmash.QUESTION_EXAMID_TOKEN, null, ExamQuestion.class, parameters);
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public void setExamQuestionListener(ExamQuestionListener examQuestionListener)
    {
        this.examQuestionListener = examQuestionListener;
    }
}
