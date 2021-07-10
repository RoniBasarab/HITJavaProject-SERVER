package main.java.com.hit.controllers;
import main.java.com.hit.dao.exceptions.CouldNotFindException;
import main.java.com.hit.dao.exceptions.EmptyJobListException;
import main.java.com.hit.dao.exceptions.ParameterNullException;
import main.java.com.hit.datamodels.Job;
import main.java.com.hit.services.JobFinderService;
import main.java.com.hit.stringmatching.implementations.RKImpl;
import main.java.com.hit.server.response.ResponseFactory;
import main.java.com.hit.server.socket.SocketExchange;
import java.util.List;


public class JobFinderController
{
    private final JobFinderService SearchService;
    public JobFinderController()
    {
        SearchService = new JobFinderService(new RKImpl());
    }

    public void getAll(SocketExchange exchange)
    {
        List<Job> data;
        try {
            data = SearchService.getAll();
            exchange.sendData(ResponseFactory.createSuccessfulResponse(data));
        } catch (EmptyJobListException e) {
            exchange.sendData(ResponseFactory.createFailedResponse("No jobs to search for"));
        } finally {
            exchange.closeConnection();
        }

    }

    public void getByCity(SocketExchange exchange, Object cityObj)
    {
        try{
            String city = (String) cityObj;
            var data = SearchService.getByCity(city);
            exchange.sendData(ResponseFactory.createSuccessfulResponse(data));
        } catch(CouldNotFindException e)
        {
            exchange.sendData(ResponseFactory.createFailedResponse("No such city exists"));
        } catch(ParameterNullException e)
        {
            exchange.sendData(ResponseFactory.createFailedResponse("City parameter missing"));
        } finally {
            exchange.closeConnection();
        }
    }

    public void getById(SocketExchange exchange, Object idObj)
    {
        try{
            String id = (String) idObj;
            var data = SearchService.getById(id);
            exchange.sendData(ResponseFactory.createSuccessfulResponse(data));
        } catch(CouldNotFindException e)
        {
            exchange.sendData(ResponseFactory.createFailedResponse("No such id exists"));
        } catch(ParameterNullException e)
        {
            exchange.sendData(ResponseFactory.createFailedResponse("Id parameter missing"));
        } finally {
            exchange.closeConnection();
        }
    }

    public void getBySalary(SocketExchange exchange, Object salaryObj)
    {
        try{
            String salary = (String) salaryObj;
            var data = SearchService.getBySalary(salary);
            exchange.sendData(ResponseFactory.createSuccessfulResponse(data));
        } catch(CouldNotFindException e)
        {
            exchange.sendData(ResponseFactory.createFailedResponse("No such salary exists"));
        } catch(ParameterNullException e)
        {
            exchange.sendData(ResponseFactory.createFailedResponse("Salary parameter missing"));
        } finally {
            exchange.closeConnection();
        }
    }

    public void addToList(SocketExchange exchange, Object JobToAddObj)
    {
        try{
            Job JobToAdd = (Job) JobToAddObj;
           SearchService.AddToList(JobToAdd);
           exchange.sendData(ResponseFactory.createSuccessfulResponse());
        }catch(ParameterNullException e)
        {
            exchange.sendData(ResponseFactory.createFailedResponse("Job object was null"));
        }finally {
            exchange.closeConnection();
        }

    }

    public void addSeveralToList(SocketExchange exchange, Object JobsToAddObj)
    {
        try{

            Job[] JobsToAdd = (Job[]) JobsToAddObj;
            SearchService.AddSeveralToList(JobsToAdd);
            exchange.sendData(ResponseFactory.createSuccessfulResponse());
        } finally {
            exchange.closeConnection();
        }
    }

    public void removeFromList(SocketExchange exchange, Object idObj)
    {
        try{
            String id = (String) idObj;
            SearchService.removeFromList(id);
            exchange.sendData(ResponseFactory.createSuccessfulResponse());
        } catch(CouldNotFindException e)
        {
            exchange.sendData(ResponseFactory.createFailedResponse("No such id exists"));
        } catch(ParameterNullException e)
        {
            exchange.sendData(ResponseFactory.createFailedResponse("Id parameter missing"));
        } finally {
            exchange.closeConnection();
        }
    }

    public void removeSeveralFromList(SocketExchange exchange, Object idsObj)
    {
        String[] ids = (String[]) idsObj;
        for(var id : ids)
        {
            try{
                SearchService.removeFromList(id);
                exchange.sendData(ResponseFactory.createSuccessfulResponse());
            } catch(CouldNotFindException e)
            {
                exchange.sendData(ResponseFactory.createFailedResponse("No such id exists"));
            } catch(ParameterNullException e)
            {
                exchange.sendData(ResponseFactory.createFailedResponse("Id parameter missing"));
            } finally {
                exchange.closeConnection();
            }
        }
    }

    public void updateFromList(SocketExchange exchange, Object JobToEditObj)
    {
        Job JobToEdit = (Job) JobToEditObj;
        try{
            SearchService.updateFromList(JobToEdit);
            exchange.sendData(ResponseFactory.createSuccessfulResponse());
        } catch(CouldNotFindException e)
        {
            exchange.sendData(ResponseFactory.createFailedResponse("Job object was null"));
        } catch(ParameterNullException e)
        {
            exchange.sendData(ResponseFactory.createFailedResponse("Job parameter missing"));
        } finally {
            exchange.closeConnection();
        }
    }
}
