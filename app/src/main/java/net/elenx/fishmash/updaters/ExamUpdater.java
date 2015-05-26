package net.elenx.fishmash.updaters;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.daos.AuthenticateDAO;
import net.elenx.fishmash.daos.ExamDAO;
import net.elenx.fishmash.models.Authenticate;
import net.elenx.fishmash.models.Exam;

import java.util.Arrays;
import java.util.List;

public class ExamUpdater extends FishmashUpdater
{
    private List<Exam> examList;

    public ExamUpdater(OptionsActivity optionsActivity)
    {
        super(optionsActivity);
    }

    @Override
    protected void download() throws Exception
    {
        String url = Constant.EXAMS + buildApiToken();
        Exam[] exams = fishmashRest.getForObject(url, Exam[].class);

        examList = Arrays.asList(exams);
    }

    @Override
    protected void save() throws Exception
    {
        if(examList == null)
        {
            throw new Exception("there is no exam list");
        }

        ExamDAO examDAO = new ExamDAO(optionsActivity);
        examDAO.truncate();
        examDAO.insert(examList);
    }

    private String buildApiToken() throws Exception
    {
        AuthenticateDAO authenticateDAO = new AuthenticateDAO(optionsActivity);

        if(authenticateDAO.count() <= 0)
        {
            throw new Exception("user is not logged in");
        }

        Authenticate authenticate = authenticateDAO.selectAll().get(0);

        return "?api_token=" + authenticate.getToken();
    }
}
