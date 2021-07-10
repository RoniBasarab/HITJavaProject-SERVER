package main.java.com.hit.server.enums;

public enum Commands
{
    GET_ALL, GET_ID, GET_CITY, GET_SALARY, ADD,ADD_SEVERAL, REMOVE, UPDATE,REMOVE_SEVERAL;
    public String toString()
    {
        return switch (this) {
            case GET_ALL -> "GET_ALL";
            case GET_ID -> "GET_ID";
            case GET_CITY -> "GET_CITY";
            case GET_SALARY -> "GET_SALARY";
            case ADD -> "ADD";
            case REMOVE -> "REMOVE";
            case UPDATE -> "UPDATE";
            case ADD_SEVERAL -> "ADD_SEVERAL";
            case REMOVE_SEVERAL -> "REMOVE_SEVERAL";
        };
    }

    public static Commands fromString(String s)
    {
        return switch(s)
                {
                    case "GET_ALL" -> GET_ALL;
                    case "GET_ID" -> GET_ID;
                    case "GET_CITY" -> GET_CITY;
                    case "GET_SALARY" -> GET_SALARY;
                    case "ADD" -> ADD;
                    case "REMOVE" -> REMOVE;
                    case "UPDATE" -> UPDATE;
                    case "ADD_SEVERAL" -> ADD_SEVERAL;
                    case "REMOVE_SEVERAL" -> REMOVE_SEVERAL;
                    default -> throw new IllegalStateException("Unexpected value: " + s);
                };
    }
}
