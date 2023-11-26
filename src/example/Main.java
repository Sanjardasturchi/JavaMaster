package example;



import example.connection.ConnectionToDatabase;
import example.enums.TaskStatus;
import example.model.Task;
import example.scanner.ScannerU;

import java.util.List;


public class Main {
    static ScannerU scanner=new ScannerU();
    static ConnectionToDatabase connection=new ConnectionToDatabase();

    public static void main(String[] args) {

        do {
            System.out.println("""
                    
                    
                    ***** Menu *****
              
                    1-Create
                    2-Active Task List
                    3-Finished Task List
                    4-Update (by id)
                    5-Delete by id
                    6-Mark as Done
                    0-Exit""");
           String operation= scanner.printString("?: ");
            if (operation.equals("0")) {
                System.out.println("Progress terminated");
                break;
            } else if (operation.trim().isEmpty()) {
                System.out.println("Choose operation:");
            }else {
                chooseOperation(operation);
            }

        }while (true);


    }

    private static void chooseOperation(String operation) {
        switch (operation){
            case "1" -> {
                String title =scanner.printString("Enter title: ");
                String content =scanner.printString("Enter content: ");

                boolean result = connection.creatTask(title,content);

                if (result) {
                    scanner.onlyPrint("Task added");
                }else {
                    scanner.onlyPrint("Task not added");
                }
            }
            case "2" -> {
                List<Task> allActiveTasks=connection.getAllTasksWithStatus(TaskStatus.ACTIVE.toString());

                if(allActiveTasks.size()==0){
                    scanner.onlyPrint("No thing found");
                }

                for (Task task : allActiveTasks) {
                    System.out.println(task);
                }
            }
            case "3" -> {
                List<Task> allDoneTasks=connection.getAllTasksWithStatus(TaskStatus.DONE.toString());

                if(allDoneTasks.size()==0){
                    scanner.onlyPrint("No thing found");
                }

                for (Task task : allDoneTasks) {
                    System.out.println(task);
                }
            }
            case "4" -> {
                int id =scanner.printInt("Enter task id: ");
                String title =scanner.printString("Enter title: ");
                String content =scanner.printString("Enter content: ");

               String result= connection.uptadeById(id,title,content);
                scanner.onlyPrint(result);
            }
            case "5" -> {
                int id =scanner.printInt("Enter task id to delete: ");
                String result = connection.deleteById(id);
                scanner.onlyPrint(result);
            }
            case "6" -> {
                int id =scanner.printInt("Enter task id to murk as done: ");
                String result = connection.murkAsDoneById(id);
                scanner.onlyPrint(result);
            }
        }
    }
}