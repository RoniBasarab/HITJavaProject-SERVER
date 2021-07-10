package main.java.com.hit.services;
import com.google.gson.Gson;
import main.java.com.hit.dao.DaoFileImpl;
import main.java.com.hit.dao.IDao;
import main.java.com.hit.dao.exceptions.CouldNotFindException;
import main.java.com.hit.dao.exceptions.EmptyJobListException;
import main.java.com.hit.dao.exceptions.ParameterNullException;
import main.java.com.hit.datamodels.Job;
import main.java.com.hit.stringmatching.interfaces.IStringMatcher;
import java.util.List;
import static main.java.com.hit.server.Server.file;


public class JobFinderService
{
    public static IStringMatcher Stringmatcher; // a single instance of string matching algorithm is required

    public List<Job> getByCity(String sCity) throws CouldNotFindException, ParameterNullException
    {
        return file.getJobByCity(sCity);
    }

    public List<Job> getBySalary (String sSalary) throws CouldNotFindException, ParameterNullException
    {
        return file.getJobBySalary(sSalary);
    }

    public Job getById(String sId) throws CouldNotFindException, ParameterNullException
    {
        return file.getJobById(sId);
    }
    public void AddToList(Job JobToAdd) throws ParameterNullException
    {
        file.AddJobToFile(JobToAdd);
    }
    public void AddSeveralToList(Job[] JobToAdd)
    {
        file.AddJobsToFile(JobToAdd);
    }

    public void removeFromList(String sId) throws CouldNotFindException, ParameterNullException
    {
        file.removeJobFromList(sId);
    }
    public void updateFromList(Job JobToEdit) throws CouldNotFindException, ParameterNullException
    {
        file.updateJobFromList(JobToEdit);
    }
    public List<Job> getAll() throws EmptyJobListException
    {
        return file.getAllJobs();
    }

    public JobFinderService(IStringMatcher Stringmatcher)
    {
        JobFinderService.Stringmatcher = Stringmatcher;

    }
}
