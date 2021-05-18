package Commands;

import Controller.CollectionManager;
import Controller.OutputSetup;

import java.util.NoSuchElementException;

public class RemoveFirst extends AbstractCommand implements OutputSetup {
    public RemoveFirst(CollectionManager manager) {
        super(manager);
        setDescription("удалить первый элемент из коллекции");

    }

    @Override
    public synchronized String execute() {
        if (getManager().getPeople().size() != 0) {
            try {
                getManager().getPeople().remove();
                return ("первый элемент коллекции удалён.");
            }
            catch (NoSuchElementException ex) {
                return ("Нельзя удалить первый элемент коллекции. Коллекция пуста.");
            }
        }
        return ("Коллекция пуста.");
    }


    @Override
    public void printInformation(String info) {

    }
}
