package example.connection;



import example.enums.TaskStatus;
import example.model.Task;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class ConnectionToDatabase {


    public boolean creatTask(String title, String content) {
        Connection connection = null;

        {
            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_db",
                        "jdbc_user", "123456");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        int i = 0;
        try {
            Statement statement = connection.createStatement();

            String sql = "insert into task(title, content) values ('%s','%s')";
            sql = String.format(sql, title, content);

            i = statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return i != 0;
    }



    public List<Task> getAllTasksWithStatus(String statuss) {
        Connection connection = null;

        {
            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_db",
                        "jdbc_user", "123456");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        List<Task> tasks = new LinkedList<>();

        try {
            Statement statement = connection.createStatement();
            String sql = "select * from task where status='%s'";
            sql = String.format(sql, statuss);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                String status = resultSet.getString("status");
                LocalDateTime createdDate = resultSet.getTimestamp("created_date").toLocalDateTime();

                Task task = new Task(id, title, content, status, createdDate);
                if (status.equals(TaskStatus.DONE.toString())) {
                    Timestamp finishedDate = resultSet.getTimestamp("finished_date");
                    if (finishedDate != null) {
                        LocalDateTime endDate = finishedDate.toLocalDateTime();
                        task.setEndTime(endDate);
                    }
                }
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return tasks;
    }

    public String uptadeById(int id, String title, String content) {
        Connection connection = null;

        {
            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_db",
                        "jdbc_user", "123456");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        int result = 0;
        try {
            Statement statement = connection.createStatement();

            String sql = "update task set title='%s', content='%s' where id=%d";
            sql = String.format(sql, title, content, id);

            result = statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (result != 0) {
            return "The update was successful";
        }
        return "No information was found for the entered id";
    }

    public String deleteById(int id) {
        Connection connection = null;

        {
            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_db",
                        "jdbc_user", "123456");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        int result = 0;
        try {
            Statement statement = connection.createStatement();
            result = statement.executeUpdate("delete from task where id=" + id);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (result != 0) {
            return "The delete was successful";
        }
        return "No information was found for the entered id";
    }

    public String murkAsDoneById(int id) {
        Connection connection = null;

        {
            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_db",
                        "jdbc_user", "123456");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        int result = 0;
        try {
            Statement statement = connection.createStatement();
//            String sql="select murk_task_as_done(%d)";
            String sql = "update task set status='DONE', finished_date=now() where id=%d";
            sql = String.format(sql, id);
            result = statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (result != 0) {
            return "The update was successful";
        }
        return "No information was found for the entered id";
    }
}
