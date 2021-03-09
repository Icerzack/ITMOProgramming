package Controller;

import java.io.*;
import java.util.*;

/**
 * Класс управления командами.
 * @autor Максим Кузнецов
 * @version 1.0
 */
public class Commander {

    private CollectionManager manager;
    private String userCommand;
    private String[] finalUserCommand;

    {
        userCommand = "";
    }

    /**
     * Конструктор - создание нового "каммандера"
     * @param manager - экземпляр "CollectionManager'а", с которым мы работаем
     * @see CollectionManager#CollectionManager(Queue) ()
     */
    public Commander(CollectionManager manager) {
        this.manager = manager;
    }

    /**
     * interactiveMod() - запуск интерактивного режима для работы с коллекциями"
     * @see CollectionManager#CollectionManager(Queue) ()
     */
    public void interactiveMod() throws IOException {
        try(Scanner commandReader = new Scanner(System.in)) {
            while (!userCommand.equals("exit")) {
                userCommand = commandReader.nextLine();
                finalUserCommand = userCommand.trim().split(" ", 2);
                try {
                    switch (finalUserCommand[0]) {
                        case "": break;
                        case "remove_first":
                            manager.remove_first();
                            break;
                        case "add":
                            manager.add();
                            break;
                        case "save":
                            manager.save();
                            break;
                        case "show":
                            manager.show();
                            break;
                        case "clear":
                            manager.clear();
                            break;
                        case "info":
                            manager.info();
                            break;
                        case "update":
                            manager.update(Integer.parseInt(finalUserCommand[1]));
                            break;
                        case "load":
//                            manager.load();
                            break;
                        case "remove":
//                            manager.remove(finalUserCommand[1]);
                            break;
                        case "add_if_min":
//                            manager.add_if_min(finalUserCommand[1]);
                            break;
                        case "help":
                            manager.help();
                            break;
                        case "exit":
                            manager.save();
                            break;
                        case "sort":
//                            manager.sort(finalUserCommand[1]);
                            break;
                        case "man":
//                            manager.man(finalUserCommand[1]);
                            break;
                        default:
                            System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println("Отсутствует аргумент.");
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commander)) return false;
        Commander commander = (Commander) o;
        return Objects.equals(manager, commander.manager);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(manager, userCommand);
        result = 31 * result + Arrays.hashCode(finalUserCommand);
        return result;
    }
}