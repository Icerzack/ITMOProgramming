package Commands;

import Controller.CollectionManager;
import Controller.OutputSetup;
import Model.Person;

import java.util.Iterator;

public class Show extends AbstractCommand implements OutputSetup {

    String total = "";

    public Show(CollectionManager manager) {
        super(manager);
        setDescription("вывести в стандартный поток вывода все элементы коллекции в строковом представлении");

    }

    @Override
    public synchronized String execute() {
        Iterator<Person> iter = getManager().getPeople().iterator();
        Person person;
        if(!getManager().getPeople().isEmpty()){
            while (iter.hasNext()) {
                person = iter.next();
                if(person == null) break;
                printInformation("Обработка Person: id=" + person.getId());
                printInformation(person.getName());
                printInformation(person.getCoordinates());
                printInformation(person.getCreationDate()+"");
                printInformation(person.getHeight()+"");
                printInformation(person.getPassportID());
                printInformation(person.getHairColor()+"");
                printInformation(person.getNationality()+"");
                printInformation(person.getLocation());
                printInformation("");
            }
            return total;
        }
        else {return ("Коллекция пуста.");}
    }

    @Override
    public void printInformation(String info) {
        total = total.concat(info+"\n");
    }
}
