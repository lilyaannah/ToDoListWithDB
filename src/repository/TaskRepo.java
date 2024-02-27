package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TaskRepo {
    private final String url="jdbc:postgresql://localhost:5432/ToDoList";
    private final String user="postgres";
    private final String password="731009";

    public Connection createConnection(){
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
