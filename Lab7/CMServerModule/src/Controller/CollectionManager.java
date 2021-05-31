package Controller;

import Commands.AbstractCommand;
import Model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class CollectionManager implements OutputSetup{

    public static int finalId = 0;
    boolean errorTrigger = false;
    Queue<Person> personPriorityQueue = new PriorityQueue<>();
    public static ZonedDateTime zonedDateTimeOfCreation;

    public final int historyNum = 13;
    public int historyCounter = historyNum-1;
    public final String[] history = new String[historyNum];


    private void parseXML(String pathToFile, Queue<Person> personPriorityQueue) {
        try {

            HashMap hashMap = new HashMap();

            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream( pathToFile ), "UTF-8");
            BufferedReader reader = new BufferedReader( inputStreamReader );
            InputSource inputSource = new InputSource( reader );
            Document document = documentBuilder.parse(inputSource);

            Node root = document.getDocumentElement();

            NodeList books = root.getChildNodes();
            for (int i = 0; i < books.getLength(); i++) {
                Node book = books.item(i);
                if (book.getNodeType() != Node.TEXT_NODE) {
                    Coordinates coordinates = null;
                    Color color = null;
                    Country country = null;
                    Location location = null;
                    NodeList bookProps = book.getChildNodes();
                    for(int j = 0; j < bookProps.getLength(); j++) {
                        Node bookProp = bookProps.item(j);
                        if (bookProp.getNodeType() != Node.TEXT_NODE) {
                            if((bookProp.getNodeName()+"").equals("coordinates")){
                                String string = bookProp.getChildNodes().item(0).getTextContent()+"";
                                String[] parts = string.split(";");
                                String part1 = parts[0];
                                String part2 = parts[1];
                                coordinates = new Coordinates();
                                try {
                                    coordinates.setX(Double.parseDouble(part1));
                                    coordinates.setY(Long.parseLong(part2));
                                }catch (Exception e){
                                    printInformation("Поле coordinates у объекта №"+(i-i/2)+" содержит неверное значение - необходимо исправить");
                                    errorTrigger = true;
                                }
                            }
                            else if((bookProp.getNodeName()+"").equals("hairColor")){
                                switch (bookProp.getChildNodes().item(0).getTextContent()+"") {
                                    case "GREEN":
                                        color = Color.GREEN;
                                        break;
                                    case "BLUE":
                                        color = Color.BLUE;
                                        break;
                                    case "ORANGE":
                                        color = Color.ORANGE;
                                        break;
                                    case "WHITE":
                                        color = Color.WHITE;
                                        break;
                                    case "BROWN":
                                        color = Color.BROWN;
                                        break;
                                    default:
                                        printInformation("Поле hairColor у объекта №"+(i-i/2)+" содержит неверное значение - необходимо исправить");
                                        errorTrigger = true;
                                }
                            }
                            else if((bookProp.getNodeName()+"").equals("nationality")){
                                switch (bookProp.getChildNodes().item(0).getTextContent()+"") {
                                    case "UNITED_KINGDOM":
                                        country = Country.UNITED_KINGDOM;
                                        break;
                                    case "USA":
                                        country = Country.USA;
                                        break;
                                    case "FRANCE":
                                        country = Country.FRANCE;
                                        break;
                                    case "VATICAN":
                                        country = Country.VATICAN;
                                        break;
                                    case "JAPAN":
                                        country = Country.JAPAN;
                                        break;
                                    default:
                                        printInformation("Поле nationality у объекта №"+(i-i/2)+" содержит неверное значение - необходимо исправить");
                                        errorTrigger = true;
                                }
                            }
                            else if((bookProp.getNodeName()+"").equals("location")){
                                String string = bookProp.getChildNodes().item(0).getTextContent()+"";
                                String[] parts = string.split(";");
                                String part1 = parts[0];
                                String part2 = parts[1];
                                String part3 = parts[2];
                                location = new Location();
                                try {
                                    location.setX(Double.parseDouble(part1));
                                    location.setY(Long.parseLong(part2));
                                    location.setName(part3);
                                }catch (Exception e){
                                    printInformation("Поле location у объекта №"+(i-i/2)+" содержит неверное значение - необходимо исправить");
                                    errorTrigger = true;
                                }
                            }
                            else {
                                if((bookProp.getNodeName()+"").equals("height")){
                                    String string = bookProp.getChildNodes().item(0).getTextContent()+"";
                                    try {
                                        float f = Float.parseFloat(string);
                                        hashMap.put("height",f);
                                    }catch (Exception e){
                                        printInformation("Поле height у объекта №"+(i-i/2)+" содержит неверное значение - необходимо исправить");
                                        errorTrigger = true;
                                    }
                                }
                                hashMap.put(bookProp.getNodeName()+"",bookProp.getChildNodes().item(0).getTextContent()+"");
                            }
                        }
                    }
                    if(!errorTrigger){
                        finalId+=1;
                        ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.of("UTC"));
                        personPriorityQueue.add(new Person(finalId,hashMap.get("name")+"",coordinates,zonedDateTimeNow,Float.parseFloat(hashMap.get("height")+""),hashMap.get("passportId")+"",color,country,location,"admin"));
                    }
                }
            }

        } catch (Exception ex) {
            printInformation("Файл с таким именем не найден/Ошибка в файле, перепроверьте синтаксис");
        }
    }

    public void createXML() throws Exception {

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        Document newDoc = builder.newDocument();
        Element root = newDoc.createElement("people");
        newDoc.appendChild(root);

        Queue<Person> tempQueue = new PriorityQueue<>(this.personPriorityQueue);

        while(!tempQueue.isEmpty()){
            Element first = newDoc.createElement("person");
            root.appendChild(first);

            Person person = tempQueue.poll();

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

        StringWriter outWriter = new StringWriter();

        StreamResult result = new StreamResult(outWriter);
        transformer.transform(dom, result);
        StringBuffer sb = outWriter.getBuffer();
        String finalString = sb.toString();

        PrintWriter writerObj = new PrintWriter("file2.xml");
        writerObj.write(finalString);
        writerObj.flush();
        writerObj.close();
    }

    CollectionManager(String collectionPath) {

        try {
            if (collectionPath == null) throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.err.println("Путь к файлу должен быть задан переменной окружения 'Collman_Path.'");
            System.exit(1);
        }
        try {
            parseXML(collectionPath, personPriorityQueue);
            zonedDateTimeOfCreation = ZonedDateTime.now(ZoneId.of("UTC"));
        } catch (SecurityException e) {
            System.err.println("Файл защищён от чтения.");
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Файл пуст");
            System.exit(1);
        }
    }

    public Queue<Person> getPeople() {
        return personPriorityQueue;
    }

    public void setNewPeople(PriorityQueue<Person> pq){
        personPriorityQueue = pq;
    }

    public void save() {
        try {
            createXML();
            printInformation("Коллекция сохранена.");
        } catch (Exception e) {
            e.printStackTrace();
            printInformation("Коллекция НЕ сохранена.");
        }
    }

    public Person getById(int id) {
        return personPriorityQueue.stream().filter(person -> person.getId().equals(id)).findFirst().orElse(null);
    }


    public void addToHistory(String commandName){
        if(historyCounter==-1){
            for(int i=historyNum-1;i>0;i--)
            {
                history[i]=history[i-1];
            }
            history[0]=commandName;
        }
        else{
            history[historyCounter]=commandName;
            historyCounter--;
        }
    }

    public void execute_script(String file_name){
        File file = new File(file_name);
        try {
            Scanner scanner = new Scanner(file);
            String userCommand = "";
            String[] finalUserCommand;
            AbstractCommand errorCommand = new AbstractCommand(null) {
                @Override
                public String execute() {
                    return "Неизвестная команда. Введите 'help' для получения списка команд.";
                }
            };
            while (scanner.hasNextLine()||!userCommand.equals("execute_script")){
                userCommand = scanner.nextLine();
                finalUserCommand = userCommand.trim().split(" ", 2);
                if (finalUserCommand.length == 1) {
                    ServerSide.sendToClient.writeObject(ServerSide.availableCommands.getOrDefault(finalUserCommand[0], errorCommand).execute());
                    addToHistory(finalUserCommand[0]);
                }
                else if (finalUserCommand.length == 2) {
                    ServerSide.sendToClient.writeObject(ServerSide.availableCommands.getOrDefault(finalUserCommand[0], errorCommand).execute(finalUserCommand[1]));
                    addToHistory(finalUserCommand[0]);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void printInformation(String info) {
        System.out.println(info);
    }

}
