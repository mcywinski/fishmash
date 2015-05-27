package net.elenx.fishmash.updaters;

import android.util.Log;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.models.ExamAnswer;
import net.elenx.fishmash.models.ExamQuestion;
import net.elenx.fishmash.models.ExamResponse;
import net.elenx.fishmash.updaters.listeners.ExaminerListener;

import org.springframework.http.HttpEntity;

import java.util.Map;

public class Examiner extends FishmashUpdater
{
    private final long examId;
    private String answer;
    private boolean isOver = false;
    private ExaminerListener examinerListener;

    public Examiner(OptionsActivity optionsActivity, long examId)
    {
        super(optionsActivity);
        this.examId = examId;
    }

    @Override
    protected void download() throws Exception
    {
        if(isOver)
        {
            throw new Exception("exam is over");
        }

        Map<String, String> parameters = buildParameters();
        parameters.put("exam_id", String.valueOf(examId));

        sendAnswerAndGetResponse();
        fetchQuestion(parameters);
    }

    private void fetchQuestion(Map<String, String> parameters)
    {
        ExamQuestion examQuestion = fishmashRest.postForObject(Constant.QUESTION_EXAMID_TOKEN, null, ExamQuestion.class, parameters);

        Log.e("meaning", examQuestion.getMeaning());

        isOver |= examQuestion.getId() == 0 && examQuestion.isExam_finished();

        if(isOver)
        {
            examinerListener.examFinished();
        }
        else
        {
            examinerListener.prepareQuestion(examQuestion.getMeaning());
        }
    }

    private void sendAnswerAndGetResponse() throws Exception
    {
        if(answer == null)
        {
            // answer is null only one time - on the start - so just skip it
            return;
        }

        ExamAnswer examAnswer = new ExamAnswer(examId, answer);
        HttpEntity<String> httpEntity = buildEntityWith(examAnswer);

        Map<String, String> parameters = buildParameters();
        parameters.put("exam_id", String.valueOf(examId));

        ExamResponse examResponse = fishmashRest.postForObject(Constant.ANSWER_EXAMID_TOKEN, httpEntity, ExamResponse.class, parameters);

        // if(!examResponse.isSaved() || !examResponse.getMesssage().equals("Answer saved") )
        if(! (examResponse.isSaved() && examResponse.getMesssage().equals("Answer saved") ) )
        {
            throw new Exception("answer has been not saved");
        }
    }

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public void setExaminerListener(ExaminerListener examinerListener)
    {
        this.examinerListener = examinerListener;
    }
}
