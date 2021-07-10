package main.java.com.hit.datamodels;

public class Job
{
    private String city, salary, id, workercount;

    public String getCity() {
        return city;
    }

    public String getSalary() {
        return salary;
    }

    public String getId() {
        return id;
    }

    public String getWorkercount() {
        return workercount;
    }

    public Job( String id,String salary,String city, String workercount)
    {
        this.id = id;
        this.city = city;
        this.salary = salary;
        this.workercount = workercount;
    }
    public Job(){}

    @Override
    public String toString() {
        return "Job{" +
                "city='" + city + '\'' +
                ", salary='" + salary + '\'' +
                ", id='" + id + '\'' +
                ", workercount='" + workercount + '\'' +
                '}';
    }
}
