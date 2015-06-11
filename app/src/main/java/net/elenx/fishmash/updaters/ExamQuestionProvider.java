package net.elenx.fishmash.updaters;

import android.util.Log;

import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.models.ExamQuestion;
import net.elenx.fishmash.models.ExamResponse;
import net.elenx.fishmash.models.adapters.ExamAnswer;
import net.elenx.fishmash.updaters.listeners.ExamQuestionListener;
import net.elenx.fishmash.utilities.Fishmash;

import java.util.Map;

public class ExamQuestionProvider extends FishmashUpdater
{
    private static long activeQuestionId;

    private Map<String, String> parameters;
    private String answer;
    private ExamQuestionListener examQuestionListener;
    private ExamQuestion examQuestion;
    private boolean shouldSendAnswer = true;

    public ExamQuestionProvider(OptionsActivity optionsActivity, long examId) throws Exception
    {
        super(optionsActivity);

        parameters = buildParameters();
        parameters.put(Fishmash.EXAM_ID, String.valueOf(examId));
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);

        if(examQuestion == null)
        {
            examQuestionListener.examFinished();

            return;
        }

        boolean hasExamFinished = (examQuestion.getId() == 0 && examQuestion.isExamFinished());

        if(hasExamFinished)
        {
            examQuestionListener.examFinished();
        }
        else
        {
            Log.e("meaning", examQuestion.getMeaning());
            activeQuestionId = examQuestion.getId();
            examQuestionListener.prepareQuestion(examQuestion.getMeaning());
        }
    }

    @Override
    void download() throws Exception
    {
        if(shouldSendAnswer)
        {
            sendAnswer();
        }

        fetchQuestion();
    }

    private void sendAnswer() throws Exception
    {
        ExamAnswer examAnswer = new ExamAnswer(activeQuestionId, answer);
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

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public void setExamQuestionListener(ExamQuestionListener examQuestionListener)
    {
        this.examQuestionListener = examQuestionListener;
    }

    public void setShouldSendAnswer(boolean shouldSendAnswer)
    {
        this.shouldSendAnswer = shouldSendAnswer;
    }
}
