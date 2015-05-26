package net.elenx.fishmash.updaters;

import android.util.Log;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.models.ExamAnswer;
import net.elenx.fishmash.models.ExamQuestion;
import net.elenx.fishmash.models.ExamResponse;

import org.springframework.http.HttpEntity;

import java.util.Map;

public class Examiner extends FishmashUpdater
{
    private long examId;
    private ExamQuestion examQuestion;
    private ExamResponse examResponse;
    private String answer;

    public Examiner(OptionsActivity optionsActivity, long examId)
    {
        super(optionsActivity);
        this.examId = examId;
    }

    @Override
    protected void download() throws Exception
    {
        Map<String, String> parameters = buildParameters();
        parameters.put("exam_id", String.valueOf(examId));

        examQuestion = fishmashRest.postForObject(Constant.QUESTION_EXAMID_TOKEN, null, ExamQuestion.class, parameters);

        Log.e("meaning", examQuestion.getMeaning());

        if(examQuestion.getId() == 0 && examQuestion.isExam_finished())
        {

        }
    }

    public boolean sendAnswer()
    {
        if(answer == null)
        {
            // answer is null only one time - on the start - so just skip it
            return true;
        }

        ExamAnswer examAnswer = new ExamAnswer(examId, answer);
        HttpEntity<String> httpEntity = buildEntityWith(examAnswer);

        examResponse = fishmashRest.postForObject(Constant.ANSWER_EXAMID_TOKEN, httpEntity, ExamResponse.class);

        return true;
    }

    public ExamQuestion getExamQuestion()
    {
        return examQuestion;
    }

    public ExamResponse getExamResponse()
    {
        return examResponse;
    }

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }
}
