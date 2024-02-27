package services;

import enums.Status;
import repository.TaskRepo;

import java.sql.*;
import java.util.Scanner;

public class TaskService {
    TaskRepo taskRepo=new TaskRepo();
    public void createTask(String description){
        String query = "INSERT INTO tasks (status, description) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = taskRepo.createConnection().prepareStatement(query)) {
            preparedStatement.setString(1, Status.IN_PROGRESS.name());
            preparedStatement.setString(2, description);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка ошибок
        }

    }

    public void editTask(int id, String description){
        Connection connection=taskRepo.createConnection();
        try {
            String status = "[Не выполнено]";
            String query = "UPDATE tasks SET description = ? , status = ?  WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, description);
                preparedStatement.setString(2, status);
                preparedStatement.setInt(3, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String deleteTask(int id){
        try {
            Statement statement = taskRepo.createConnection().createStatement();
            statement.execute("update tasks set status ='"+ Status.DELETED.name() +"'  where id = '" + id + "'");
            return "Your task with id " + id + " successfully deleted";
        } catch (SQLException e) {
            System.err.println("Error deleting task with id " + id + ": " + e.getMessage());
            }
        return null;
    }

    public void showTaskSelection(){
        try {
            Statement statement = taskRepo.createConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT id, description, " +
                            "CASE " +
                            "    WHEN status = '" + Status.DONE.name() + "' THEN 'Выполненная задача' " +
                            "    WHEN status = '" + Status.IN_PROGRESS.name() + "' THEN 'Не выполненная задача' " +
                            "    WHEN status = '" + Status.DELETED.name() + "' THEN 'Удаленная задача' " +
                            "    ELSE 'Неизвестный статус' " +
                            "END AS status " +
                            "FROM tasks ORDER BY id ASC"
            );

            while (resultSet.next()) {
                // Здесь вы можете получить значения из каждого столбца в текущей строке
                int id = resultSet.getInt("id");
                String status = resultSet.getString("status");
                String description = resultSet.getString("description");

                // Просто пример вывода, вы можете адаптировать под свои нужды
                System.out.println("ID: " + id + ", Status: " + status + ", Description: " + description);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public void markCompletedTasks(int id){
        try {
            Statement statement = taskRepo.createConnection().createStatement();
            statement.execute("UPDATE tasks SET status = '" + Status.DONE.name() + "' WHERE id = '" + id + "'");
        } catch (SQLException e) {
            System.err.println("Не получилось отметить! " + id + ": " + e.getMessage());
        }
    }

}
