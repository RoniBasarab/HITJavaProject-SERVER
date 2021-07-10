package main.java.com.hit.dao;
import main.java.com.hit.dao.exceptions.CouldNotFindException;
import main.java.com.hit.dao.exceptions.EmptyJobListException;
import main.java.com.hit.dao.exceptions.ParameterNullException;
import main.java.com.hit.datamodels.Job;
import java.util.List;

public interface IDao
{
    public List<Job> getJobByCity(String sCity) throws CouldNotFindException, ParameterNullException;
    public List<Job> getJobBySalary (String sSalary) throws CouldNotFindException, ParameterNullException;
    public Job getJobById(String sId) throws CouldNotFindException, ParameterNullException;
    public void AddJobToFile(Job JobToAdd) throws ParameterNullException;
    public void AddJobsToFile(Job[] JobToAdd);
    public void removeJobFromList(String sId) throws CouldNotFindException, ParameterNullException;
    public void updateJobFromList(Job JobToEdit) throws CouldNotFindException, ParameterNullException;
    public List<Job> getAllJobs() throws EmptyJobListException;

}
