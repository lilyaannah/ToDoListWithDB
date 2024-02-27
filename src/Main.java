import services.TaskService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        while(true){

            System.out.print("TO DO LIST \n 1.Создать  \n 2.Редактировать \n 3.Удалить \n 4.Отобразить задачи \n" +
                    "5. Отметить выполненные задачи \n Выберите номер действия: ");
            int answer=scanner.nextInt();
            TaskService taskService=new TaskService();
            switch(answer){
                case 1:
                    System.out.print("Введите текстовое описание задачи : ");
                    String answerCreate=scanner.next();
                    taskService.createTask(answerCreate);break;
                case 2:
                    System.out.print("Введите идентификатор задачи для редактирования: ");
                    int editId=scanner.nextInt();
                    System.out.print("Новое текстовое описание: ");
                    String answerEdit=scanner.next();
                    taskService.editTask(editId, answerEdit); break;
                case 3:
                    System.out.print("Введите идентификатор задачи, который хотите удалить: ");
                    taskService.deleteTask(scanner.nextInt()); break;
                case 4:
                    taskService.showTaskSelection(); break;
                case 5:
                    System.out.print("Введите идентификатор выполненной задачи: ");
                    int markId=scanner.nextInt();
                    taskService.markCompletedTasks(markId); break;
                default:
                    System.out.println("Введите числа от 1-4!");
            }
        }
    }
}