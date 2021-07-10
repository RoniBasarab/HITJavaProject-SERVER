package main.java.com.hit.dao;
import main.java.com.hit.dao.exceptions.CouldNotFindException;
import main.java.com.hit.dao.exceptions.EmptyJobListException;
import main.java.com.hit.dao.exceptions.ParameterNullException;
import main.java.com.hit.datamodels.Job;
import main.java.com.hit.services.JobFinderService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

import static main.java.com.hit.server.Server.gson;

public class DaoFileImpl implements IDao
{
    String Filepath;
    Semaphore semaphore = new Semaphore(1); // handles access to file
    ArrayList<Job> Jobs = new ArrayList<>();
    public DaoFileImpl(String Filepath)
    {
        this.Filepath = Filepath;
        LoadCacheFromFile();
    }

    public void AddJobsToFile(Job[] JobToAdd)
    {
        this.Jobs.addAll(Arrays.asList(JobToAdd));
        SaveCacheToFile();
    }

    public List<Job> getJobByCity(String sCity) throws CouldNotFindException, ParameterNullException
    {
        if(sCity == null) throw new ParameterNullException("city must be defined");
        List<Job> FilteredJobs = new ArrayList<>();
        for (int i = 0; i < this.Jobs.size(); i++)
        {
            if(JobFinderService.Stringmatcher.search(sCity,this.Jobs.get(i).getCity()) != -1)
            {
                FilteredJobs.add(this.Jobs.get(i));
            }
        }
        if(FilteredJobs.isEmpty())
        {
            throw new CouldNotFindException("Couldn't find job at city " + sCity + " because no such job exists");
        }
        else
        {
            return FilteredJobs;
        }
    }

    public List<Job> getJobBySalary (String sSalary) throws CouldNotFindException, ParameterNullException
    {
        if(sSalary == null) throw new ParameterNullException("salary must be defined");
        List<Job> FilteredJobs = new ArrayList<>();
        for (int i = 0; i < this.Jobs.size(); i++)
        {
            if(JobFinderService.Stringmatcher.search(sSalary,this.Jobs.get(i).getSalary()) != -1)
            {
                FilteredJobs.add(this.Jobs.get(i));
            }
        }
        if(FilteredJobs.isEmpty())
        {
            throw new CouldNotFindException("Couldn't find job with salary " + sSalary + " because no such job exists");
        }
        else
        {
            return FilteredJobs;
        }
    }

    public Job getJobById(String sId) throws CouldNotFindException, ParameterNullException
    {
        if(sId == null) throw new ParameterNullException("id must be defined");
        for (int i = 0; i < this.Jobs.size(); i++)
        {
            if(JobFinderService.Stringmatcher.search(sId,this.Jobs.get(i).getId()) != -1)
            {
                return this.Jobs.get(i);
            }
        }
        throw new CouldNotFindException("Couldn't find job with id " + sId + " because no such job exists");
    }

    public void AddJobToFile(Job NewJob) throws ParameterNullException
    {
        this.Jobs.add(NewJob);
        SaveCacheToFile();
    }

    public void removeJobFromList(String sId) throws CouldNotFindException, ParameterNullException
    {
        if(sId == null) throw new ParameterNullException("id must be defined");
        boolean FoundID = false;
        for (int i = 0; i < this.Jobs.size(); i++)
        {
            if(JobFinderService.Stringmatcher.search(sId,this.Jobs.get(i).getId()) != -1)
            {
                this.Jobs.remove(i);
                SaveCacheToFile();
                FoundID = true;
                break;
            }
        }
        if(!FoundID)
        {
            throw new CouldNotFindException("Couldn't delete job with id " + sId + " because no such job exists");
        }
    }

    public void updateJobFromList(Job JobToEdit) throws CouldNotFindException, ParameterNullException
    {
        if(JobToEdit == null) throw new ParameterNullException("an existing job must be defined");
        boolean FoundID = false;
        for (int i = 0; i < this.Jobs.size(); i++)
        {
            if(JobFinderService.Stringmatcher.search(JobToEdit.getId(),this.Jobs.get(i).getId()) != -1)
            {
                this.Jobs.remove(i);
                this.Jobs.add(JobToEdit);
                SaveCacheToFile();
                FoundID = true;
                break;
            }
        }
        if(!FoundID)
        {
            throw new CouldNotFindException("Couldn't delete job with id " + JobToEdit.getId() + " because no such job exists");
        }
    }

    private String ReadFile()
    {
        FileInputStream myReader = null;
        StringBuilder lines;
        try {
            semaphore.acquire();
        } catch (Exception e)
        {
            System.out.println("Semaphore awaiting interrupted.");
            return null;
        }
        try {
            File myFile = new File(Filepath);
            int character;
            lines = new StringBuilder();
            myReader = new FileInputStream(myFile);
            while ((character= myReader.read()) != -1)
            {
                lines.append((char)character);
            }
            myReader.close();
            return lines.toString();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }finally {
            try {
                if (myReader != null) {
                    myReader.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            semaphore.release();
        }
        return null;
    }

    private void SaveCacheToFile()
    {
        String json = gson.toJson(this.Jobs);
        WriteFile(json);
    }

    private void LoadCacheFromFile()
    {
        Job[] JobsFromFile = gson.fromJson(ReadFile(), Job[].class);
        if(JobsFromFile != null)
        {
            this.Jobs = new ArrayList<>();
            this.Jobs.addAll(Arrays.asList(JobsFromFile));
        }
    }
    public List<Job> getAllJobs() throws EmptyJobListException
    {
        if(this.Jobs.isEmpty())
        {
            throw new EmptyJobListException("Could not find any jobs to show");
        }
        else
        {
            return this.Jobs;
        }
    }

    private void WriteFile(String s)
    {
        FileOutputStream myWriter = null;
        StringBuilder lines;
        try {
            semaphore.acquire();
        } catch (Exception e)
        {

            System.out.println("Semaphore awaiting interrupted.");
            return;
        }
        try {
            File myFile = new File(Filepath);
            myWriter = new FileOutputStream(myFile);
            myWriter.write(s.getBytes(StandardCharsets.UTF_8));
            myWriter.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }finally {
            try {
                if (myWriter != null) {
                    myWriter.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            semaphore.release();
        }
    }
}
