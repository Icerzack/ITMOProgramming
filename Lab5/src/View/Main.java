package View;
/**
 * Класс реализации программы - отвечает за вызов всех необоходимых классов.
 * @autor Максим Кузнецов
 * @version 1.0
 */
import Controller.CollectionManager;
import Controller.Commander;
import Controller.PriorityQueueCollector;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        Controller.Commander commander = new Controller.Commander(new Controller.CollectionManager(System.getenv("Collman_Path")));
//        commander.interactiveMod();
        PriorityQueueCollector priorityQueueCollector = new PriorityQueueCollector(System.getenv("Collman_Path"));
        priorityQueueCollector.addDataToQueue();
        CollectionManager collectionManager = new CollectionManager(priorityQueueCollector.getPersonPriorityQueue());
        Commander commander = new Commander(collectionManager);
        commander.interactiveMod();

    }
}
