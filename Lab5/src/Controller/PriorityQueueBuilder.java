package Controller;

import Model.Person;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;

public class PriorityQueueBuilder {
    public Comparator<Person> idComparator = new Comparator<Person>(){

        @Override
        public int compare(Person c1, Person c2) {
            return c1.getId() - c2.getId();
        }
    };
    //служебный метод для обработки данных очереди

    public void createXML(Queue<Person> personPriorityQueue) throws Exception {

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        Document newDoc = builder.newDocument();
        Element root = newDoc.createElement("people");
        newDoc.appendChild(root);

        while(!personPriorityQueue.isEmpty()){
            Element first = newDoc.createElement("person");
            root.appendChild(first);

            Person person = personPriorityQueue.poll();

            Element name = newDoc.createElement("name");
            name.appendChild(newDoc.createTextNode(person.getName()));
            first.appendChild(name);

            Element coordinates = newDoc.createElement("coordinates");
            coordinates.appendChild(newDoc.createTextNode(person.getCoordinates()));
            first.appendChild(coordinates);

            Element height = newDoc.createElement("height");
            height.appendChild(newDoc.createTextNode(person.getHeight()+""));
            first.appendChild(height);

            Element passportId = newDoc.createElement("passportId");
            passportId.appendChild(newDoc.createTextNode(person.getPassportID()));
            first.appendChild(passportId);

            Element hairColor = newDoc.createElement("hairColor");
            hairColor.appendChild(newDoc.createTextNode(person.getHairColor()+""));
            first.appendChild(hairColor);

            Element nationality = newDoc.createElement("nationality");
            nationality.appendChild(newDoc.createTextNode(person.getNationality()+""));
            first.appendChild(nationality);

            Element location = newDoc.createElement("location");
            location.appendChild(newDoc.createTextNode(person.getLocation()));
            first.appendChild(location);
        }
        DOMSource dom = new DOMSource(newDoc);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();

        StreamResult result = new StreamResult(new File("file2.xml"));
        transformer.transform(dom, result);

//        PrintWriter writerObj = new PrintWriter(new File("file2.xml"));
//        writerObj.write(newDoc.get);
//        writerObj.flush();
//        writerObj.close();
    }
}
