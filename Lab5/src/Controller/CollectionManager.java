package Controller;
/**
 * Класс реализации команд со свойствами.
 * @autor Максим Кузнецов
 * @version 1.0
 */
import Model.*;

import java.io.File;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class CollectionManager {

    Queue<Person> priorityQueue;
    protected static TreeMap<String, String> manual;

    static {
        manual = new TreeMap<>();
        manual.put("help", "вывести справку по доступным командам");
        manual.put("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        manual.put("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        manual.put("add {element}", "добавить новый элемент в коллекцию");
        manual.put("update id {element}", "обновить значение элемента коллекции, id которого равен заданному");
        manual.put("remove_by_id id", "удалить элемент из коллекции по его id");
        manual.put("clear", "очистить коллекцию");
        manual.put("save", "сохранить коллекцию в файл");
        manual.put("execute_script file_name", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        manual.put("exit", "завершить программу (без сохранения в файл)");
        manual.put("remove_first", "удалить первый элемент из коллекции");
        manual.put("add_if_min {element}", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        manual.put("history", "вывести последние 13 команд (без их аргументов)");
        manual.put("filter_by_location location", "вывести элементы, значение поля location которых равно заданному");
        manual.put("filter_contains_name name", "вывести элементы, значение поля name которых содержит заданную подстроку");
        manual.put("filter_less_than_passport_i_d passportID", "вывести элементы, значение поля passportID которых меньше заданного");
    }
    /**
     * Конструктор - создание новой очереди с определенными значениями
     * @param priorityQueue - экземпляр списка, с которым мы работаем
     * @see CollectionManager#CollectionManager(Queue) ()
     */
    public CollectionManager(Queue<Person> priorityQueue) {
        this.priorityQueue = priorityQueue;
    }

    /*public Controller.CollectionManager(String collectionPath) throws IOException {
        try {
            if (collectionPath == null) throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.out.println("Путь до файла XML нужно передать через переменную окружения Collman_Path.");
            System.exit(1);
        }
        File file = new File(collectionPath);
        try {
            if (file.exists()) this.xmlCollection = new File(collectionPath);
            else throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.out.println("Файл по указанному пути не существует.");
            System.exit(1);
        }
        wasStart = true;
    }*/
    /**
     * Функция удаления первого элемента очереди {@link CollectionManager#remove_first()}
     */
    public void remove_first() {
        try {
            priorityQueue.remove();
            System.out.println("первый элемент коллекции удалён.");
        }
        catch (NoSuchElementException ex) {
            System.out.println("Нельзя удалить первый элемент коллекции. Коллекция пуста.");
        }
    }
    /**
     * Функция добавления новго элемента в очередь {@link CollectionManager#add()} ()}
     */
    public void add() {
        Scanner scanner = new Scanner(System.in);
        String name = null;
        Coordinates coordinates = new Coordinates();
        Color color = null;
        Country country = null;
        Location location = new Location();
        String temp2;
        Double temp1;
        double temp4;
        long temp3 = 0;
        float height = 0;
        Long passportID = null;
        System.out.println("Введите name");
        while (scanner.hasNext()) {
            try {
                name = scanner.next();
                try {
                    Long.parseLong(name);
                    System.err.println("Invalid input! Try again:");
                    scanner.nextLine();
                }
                catch (Exception e){
                    if(name==null){
                        scanner.nextLine();
                    }
                    else{
                        break;
                    }
                }

            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите coordinates, сначала x, потом y (1<=х<=203 и y-любое)");
        System.out.println("Введите x");
        while (scanner.hasNext()||!scanner.equals("exit")) {
            try {
                temp1 = scanner.nextDouble();
                if(temp1<1||temp1>203){
                    System.err.println("Invalid input! Try again:");
                    scanner.nextLine();
                }
                else{
                    coordinates.setX(temp1);
                    break;
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите y");
        while (scanner.hasNext()) {
            try {
                temp3 = scanner.nextLong();
                if(temp3==0){
                    System.err.println("Invalid input! Try again:");
                    scanner.nextLine();
                }
                else{
                    coordinates.setY(temp3);
                    break;
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите height");
        while (scanner.hasNext()) {
            try {
                height = scanner.nextFloat();
                if(height==0){
                    System.err.println("Invalid input! Try again:");
                    scanner.nextLine();
                }
                else{
                    break;
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите passportID");
        while (scanner.hasNext()) {
            try {
                passportID = scanner.nextLong();
                Iterator<Person> iter = priorityQueue.iterator();
                Person testPerson;
                while (iter.hasNext()) {
                    testPerson = iter.next();
                    if(testPerson.getPassportID().equals(passportID)){
                        System.out.println("Человек с таким PassportID уже существует");
                        scanner.nextLine();
                    }
                }
                break;
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите hairColor");
        System.out.println("Возможные варианты:");
        System.out.println(java.util.Arrays.asList(Color.values()));
        while (scanner.hasNext()) {
            try {
                temp2 = scanner.next();
                if(temp2!=null) {
                    switch (temp2) {
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
                            System.err.println("Invalid input! Try again:");
                            scanner.nextLine();
                    }
                }
                else{
                    scanner.nextLine();
                }
                if(color!=null){
                    break;
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите nationality ");
        System.out.println(java.util.Arrays.asList(Country.values()));
        while (scanner.hasNext()) {
            try {
                temp2 = scanner.next();
                if(temp2!=null) {
                    switch (temp2) {
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
                            System.err.println("Invalid input! Try again:");
                            scanner.nextLine();
                    }
                }
                else{
                    scanner.nextLine();
                }
                if(country!=null){
                    break;
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите location, сначала x, потом y, потом name (х!=0 и y-любое, name-непустое)");
        System.out.println("Введите x");
        while (scanner.hasNext()) {
            try {
                temp1 = scanner.nextDouble();
                if(temp1==0){
                    System.err.println("Invalid input! Try again:");
                    scanner.nextLine();
                }
                else{
                    location.setX(temp1);
                    break;
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите y");
        while (scanner.hasNext()) {
            try {
                temp4 = scanner.nextDouble();
                if(temp4==0){
                    System.err.println("Invalid input! Try again:");
                    scanner.nextLine();
                }
                else{
                    location.setY(temp3);
                    break;
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите name");
        while (scanner.hasNext()) {
            try {
                temp2 = scanner.next();
                if(temp2==null){
                    System.err.println("Invalid input! Try again:");
                    scanner.nextLine();
                }
                else{
                    location.setName(temp2);
                    break;
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }

        Random rand = new Random();
        int id = rand.nextInt(100);
        ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.of("UTC"));
        priorityQueue.add(new Person(id,name,coordinates,zonedDateTimeNow,height,passportID+"",color,country,location));
    }
    /**
     * Функция полной очистки очереди {@link CollectionManager#clear()} ()}
     */
    public void clear() {
        priorityQueue.clear();
        System.out.print("Коллекция очищена.");
    }
    /**
     * Функция вывода доступных команд {@link CollectionManager#help()} ()}
     */
    public void help() {
        System.out.println("Команды: \n");
        for (String s:manual.keySet()) {
            System.out.println(s+" -- "+manual.get(s));
        }
    }
    /**
     * Функция сохранения очереди {@link CollectionManager#save()} ()}
     */
    public void save() {
        PriorityQueueBuilder priorityQueueBuilder = new PriorityQueueBuilder();
        try {
            priorityQueueBuilder.createXML(priorityQueue);
            System.out.print("Коллекция сохранена.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("Коллекция НЕ сохранена.");
        }
    }
    /**
     * Функция отображения информации об очереди {@link CollectionManager#info()} ()}
     */
    public void info() {
        System.out.println("Тип коллекции: "+priorityQueue.getClass());
        System.out.println("Размер коллекции: "+priorityQueue.size());
        System.out.println("Время создания коллекции: "+PriorityQueueCollector.zonedDateTimeOfCreation);
    }
    /**
     * Функция показа элементов очереди {@link CollectionManager#show()} ()}
     */
    public void show() {
        Iterator<Person> iter = priorityQueue.iterator();
        Person person;
        if(!priorityQueue.isEmpty()){
            while (iter.hasNext()) {
                person = iter.next();
                if(person == null) break;
                System.out.println("Обработка Person: id=" + person.getId());
                System.out.println(person.getName());
                System.out.println(person.getCoordinates());
                System.out.println(person.getCreationDate());
                System.out.println(person.getHeight());
                System.out.println(person.getPassportID());
                System.out.println(person.getHairColor());
                System.out.println(person.getNationality());
                System.out.println(person.getLocation());
                System.out.println();
            }
        }
        else {System.out.println("Коллекция пуста.");}
    }
    /**
     * Функция обновления элемента очереди {@link CollectionManager#update(int)} ()}
     */
    public void update(int id){
        Scanner scanner = new Scanner(System.in);
        Iterator<Person> iter = priorityQueue.iterator();
        Person person = null;
        while (iter.hasNext()) {
            person = iter.next();
            if(person.getId()==id){
                break;
            }
        }
        if(person==null){
            System.err.println("Человек с id "+id+" не существует");
            return;
        }
        String name;
        Color color = null;
        Country country = null;
        String temp1;
        Double xC = null;
        Double xL = null;
        double yL = 0;
        long yC;
        float height;
        String passportID;
        System.out.println("Введите name - текущее значение "+person.getName()+" (для сохранения текущего значения, введите YES)");
        while (scanner.hasNext()) {
            try {
                name = scanner.next();
                if(name==null){
                    System.err.println("Invalid input! Try again:");
                    scanner.nextLine();
                }
                else if(name.equals("YES")){
                    System.err.println("Сохранено текущее значение");
                    break;
                }
                else{
                    try {
                        Long.parseLong(name);
                        System.err.println("Invalid input! Try again:");
                        scanner.nextLine();
                    }
                    catch (Exception e){
                        person.setName(name);
                        break;
                    }
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите coordinates, сначала x, потом y (1<=х<=203 и y-любое)");
        System.out.println("Введите x - текущее значение "+person.getCoordinates().split(";")[0]+"(для сохранения текущего значения, введите YES)");
        while (scanner.hasNext()) {
            try {
                temp1 = scanner.next();
                if(temp1.equals("YES")){
                    System.err.println("Сохранено текущее значение");
                    break;
                }
                else {
                    try {
                        xC = Double.parseDouble(temp1);
                        if (xC < 1 || xC > 203) {
                            System.err.println("Invalid input! Try again:");
                            scanner.nextLine();
                        } else {
                            person.setCoordinates(xC, Long.parseLong(person.getCoordinates().split(";")[1]));
                            break;
                        }
                    } catch (Exception e){
                        System.err.println("Invalid input! Try again:");
                        scanner.nextLine();
                    }
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите y - текущее значение "+person.getCoordinates().split(";")[1]+" (для сохранения текущего значения, введите YES)");
        while (scanner.hasNext()) {
            try {
                temp1 = scanner.next();
                if(temp1.equals("YES")){
                    System.err.println("Сохранено текущее значение");
                    break;
                }
                else {
                    try {
                        yC = Long.parseLong(temp1);
                        if (yC==0) {
                            System.err.println("Invalid input! Try again:");
                            scanner.nextLine();
                        } else {
                            if(xC!=0){
                                person.setCoordinates(xC, yC);
                            }
                            else{
                                person.setCoordinates(Double.parseDouble(person.getCoordinates().split(";")[0]),yC);
                            }
                            break;
                        }
                    } catch (Exception e){
                        System.err.println("Invalid input! Try again:");
                        scanner.nextLine();
                    }
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите height - текущее значение "+person.getHeight()+" (для сохранения текущего значения, введите YES)");
        while (scanner.hasNext()) {
            try {
                temp1 = scanner.next();
                if(temp1.equals("YES")){
                    System.err.println("Сохранено текущее значение");
                    break;
                }
                else {
                    try {
                        height = Float.parseFloat(temp1);
                        if (height<=0) {
                            System.err.println("Invalid input! Try again:");
                            scanner.nextLine();
                        } else {
                            person.setHeight(height);
                            break;
                        }
                    } catch (Exception e){
                        System.err.println("Invalid input! Try again:");
                        scanner.nextLine();
                    }
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите passportID - текущее значение "+person.getPassportID()+" (для сохранения текущего значения, введите YES)");
        while (scanner.hasNext()) {
            try {
                passportID = scanner.next();
                if(passportID==null){
                    System.err.println("Invalid input! Try again:");
                    scanner.nextLine();
                }
                else if(passportID.equals("YES")){
                    System.err.println("Сохранено текущее значение");
                    break;
                }
                else{
                    try {
                        Long.parseLong(passportID);
                        Iterator<Person> iter1 = priorityQueue.iterator();
                        Person testPerson;
                        while (iter1.hasNext()) {
                            testPerson = iter1.next();
                            if(testPerson.getPassportID().equals(passportID)){
                                System.err.println("Человек с таким PassportID уже существует");
                                scanner.nextLine();
                                }
                        }
                        person.setPassportID(passportID);
                        break;
                    }
                    catch (Exception e){
                        System.err.println("Invalid input! Try again:");
                        scanner.nextLine();
                    }
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите hairColor - текущее значение "+person.getHairColor()+" (для сохранения текущего значения, введите YES)");
        System.out.println("Возможные варианты:");
        System.out.println(java.util.Arrays.asList(Color.values()));
        while (scanner.hasNext()) {
            try {
                temp1 = scanner.next();
                if(temp1.equals("YES")){
                    System.err.println("Сохранено текущее значение");
                    break;
                }
                else if(temp1!=null) {
                    switch (temp1) {
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
                            System.err.println("Invalid input! Try again:");
                            scanner.nextLine();
                    }
                }
                else{
                    scanner.nextLine();
                }
                if(color!=null){
                    person.setHairColor(color);
                    break;
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите nationality - текущее значение "+person.getNationality()+" (для сохранения текущего значения, введите YES)");
        System.out.println("Возможные варианты:");
        System.out.println(java.util.Arrays.asList(Country.values()));
        while (scanner.hasNext()) {
            try {
                temp1 = scanner.next();
                if(temp1.equals("YES")){
                    System.err.println("Сохранено текущее значение");
                    break;
                }
                else if(temp1!=null) {
                    switch (temp1) {
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
                            System.err.println("Invalid input! Try again:");
                            scanner.nextLine();
                    }
                }
                else{
                    scanner.nextLine();
                }
                if(country!=null){
                    person.setNationality(country);
                    break;
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите location, сначала x, потом y, потом name (х!=0 и y-любое, name-непустое)");
        System.out.println("Введите x - текущее значение "+person.getLocation().split(";")[0]+" (для сохранения текущего значения, введите YES)");
        while (scanner.hasNext()) {
            try {
                temp1 = scanner.next();
                if(temp1.equals("YES")){
                    System.err.println("Сохранено текущее значение");
                    break;
                }
                else {
                    try {
                        xL = Double.parseDouble(temp1);
                        if (xL==0) {
                            System.err.println("Invalid input! Try again:");
                            scanner.nextLine();
                        } else {
                            person.setLocation(xL,Double.parseDouble(person.getLocation().split(";")[1]),person.getLocation().split(";")[2]);
                            break;
                        }
                    } catch (Exception e){
                        System.err.println("Invalid input! Try again:");
                        scanner.nextLine();
                    }
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите y - текущее значение "+person.getLocation().split(";")[1]+" (для сохранения текущего значения, введите YES)");
        while (scanner.hasNext()) {
            try {
                temp1 = scanner.next();
                if(temp1.equals("YES")){
                    System.err.println("Сохранено текущее значение");
                    break;
                }
                else {
                    try {
                        yL = Long.parseLong(temp1);
                        if(xL!=0){
                            person.setLocation(xL, yL, person.getLocation().split(";")[2]);
                        }
                        else{
                            person.setLocation(Double.parseDouble(person.getLocation().split(";")[0]), yL, person.getLocation().split(";")[2]);
                        }
                        break;
                    } catch (Exception e){
                        System.err.println("Invalid input! Try again:");
                        scanner.nextLine();
                    }
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите name - текущее значение "+person.getLocation().split(";")[2]+" (для сохранения текущего значения, введите YES)");
        while (scanner.hasNext()) {
            try {
                temp1 = scanner.next();
                if(temp1==null){
                    System.err.println("Invalid input! Try again:");
                    scanner.nextLine();
                }
                else if(temp1.equals("YES")){
                    System.err.println("Сохранено текущее значение");
                    break;
                }
                else{
                    try {
                        Long.parseLong(temp1);
                        System.err.println("Invalid input! Try again:");
                        scanner.nextLine();
                    }
                    catch (Exception e){
                        if(xL!=0){
                            if(yL!=0){
                                person.setLocation(xL, yL, temp1);
                            }
                            else{
                                person.setLocation(xL, Double.parseDouble(person.getLocation().split(";")[1]), temp1);
                            }
                        }
                        else{
                            if(yL!=0){
                                person.setLocation(Double.parseDouble(person.getLocation().split(";")[0]), yL, temp1);
                            }
                            else{
                                person.setLocation(Double.parseDouble(person.getLocation().split(";")[0]), Double.parseDouble(person.getLocation().split(";")[1]), temp1);
                            }
                        }
                        break;
                    }
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }


    }
    /**
     * Функция удаления элемента очереди по id{@link CollectionManager#remove_by_id(int)} ()}
     */
    public void remove_by_id(int id) {
        if (priorityQueue.size() != 0) {
            try {
                Iterator<Person> iter = priorityQueue.iterator();
                Person personToDelete = null;
                while (iter.hasNext()) {
                    personToDelete = iter.next();
                    if (personToDelete.getId() == id) {
                        break;
                    }
                }
                if (personToDelete == null) {
                    System.err.println("Человек с id " + id + " не существует");
                    return;
                }
                PriorityQueue<Person> updatedPriorityQueue = new PriorityQueue<Person>();
                for (Person p : priorityQueue) {
                    if (p.getId() != id) {
                        updatedPriorityQueue.add(priorityQueue.poll());
                    }
                }
                priorityQueue = updatedPriorityQueue;
            }catch (Exception e){
                System.err.println("Возникла непредвиденная ошибка...");
            }
        }
        else System.err.println("Коллекция пуста.");
    }
    /**
     * Функция добавленмя элемента в очередь, с проверкой условия на name{@link CollectionManager#add_if_min()} ()}
     */
    public void add_if_min(){
        if (priorityQueue.isEmpty()){
            System.err.println("Коллекция пуста.");
            return;
        }
        Collections.min(priorityQueue);
        Scanner scanner = new Scanner(System.in);
        String name = null;
        Coordinates coordinates = new Coordinates();
        Color color = null;
        Country country = null;
        Location location = new Location();
        String temp2;
        Double temp1;
        double temp4;
        long temp3 = 0;
        float height = 0;
        Long passportID = null;
        System.out.println("Введите name");
        while (scanner.hasNext()) {
            try {
                name = scanner.next();
                try {
                    Long.parseLong(name);
                    System.err.println("Invalid input! Try again:");
                    scanner.nextLine();
                }
                catch (Exception e){
                    if(name==null){
                        scanner.nextLine();
                    }
                    else{
                        break;
                    }
                }

            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите coordinates, сначала x, потом y (1<=х<=203 и y-любое)");
        System.out.println("Введите x");
        while (scanner.hasNext()||!scanner.equals("exit")) {
            try {
                temp1 = scanner.nextDouble();
                if(temp1<1||temp1>203){
                    System.err.println("Invalid input! Try again:");
                    scanner.nextLine();
                }
                else{
                    coordinates.setX(temp1);
                    break;
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите y");
        while (scanner.hasNext()) {
            try {
                temp3 = scanner.nextLong();
                if(temp3==0){
                    System.err.println("Invalid input! Try again:");
                    scanner.nextLine();
                }
                else{
                    coordinates.setY(temp3);
                    break;
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите height");
        while (scanner.hasNext()) {
            try {
                height = scanner.nextFloat();
                if(height==0){
                    System.err.println("Invalid input! Try again:");
                    scanner.nextLine();
                }
                else{
                    break;
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите passportID");
        while (scanner.hasNext()) {
            try {
                passportID = scanner.nextLong();
                Iterator<Person> iter = priorityQueue.iterator();
                Person testPerson;
                while (iter.hasNext()) {
                    testPerson = iter.next();
                    if(testPerson.getPassportID().equals(passportID)){
                        System.out.println("Человек с таким PassportID уже существует");
                        scanner.nextLine();
                    }
                }
                break;
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите hairColor");
        System.out.println("Возможные варианты:");
        System.out.println(java.util.Arrays.asList(Color.values()));
        while (scanner.hasNext()) {
            try {
                temp2 = scanner.next();
                if(temp2!=null) {
                    switch (temp2) {
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
                            System.err.println("Invalid input! Try again:");
                            scanner.nextLine();
                    }
                }
                else{
                    scanner.nextLine();
                }
                if(color!=null){
                    break;
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите nationality ");
        System.out.println(java.util.Arrays.asList(Country.values()));
        while (scanner.hasNext()) {
            try {
                temp2 = scanner.next();
                if(temp2!=null) {
                    switch (temp2) {
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
                            System.err.println("Invalid input! Try again:");
                            scanner.nextLine();
                    }
                }
                else{
                    scanner.nextLine();
                }
                if(country!=null){
                    break;
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите location, сначала x, потом y, потом name (х!=0 и y-любое, name-непустое)");
        System.out.println("Введите x");
        while (scanner.hasNext()) {
            try {
                temp1 = scanner.nextDouble();
                if(temp1==0){
                    System.err.println("Invalid input! Try again:");
                    scanner.nextLine();
                }
                else{
                    location.setX(temp1);
                    break;
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите y");
        while (scanner.hasNext()) {
            try {
                temp4 = scanner.nextDouble();
                if(temp4==0){
                    System.err.println("Invalid input! Try again:");
                    scanner.nextLine();
                }
                else{
                    location.setY(temp3);
                    break;
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }
        System.out.println("Введите name");
        while (scanner.hasNext()) {
            try {
                temp2 = scanner.next();
                if(temp2==null){
                    System.err.println("Invalid input! Try again:");
                    scanner.nextLine();
                }
                else{
                    location.setName(temp2);
                    break;
                }
            }
            catch (FormatterClosedException exp) {
                System.out.println(exp);
                break;
            }
            catch (NoSuchElementException exp) {
                System.err.println("Invalid input! Try again:");
                scanner.nextLine();
            }
        }

        Random rand = new Random();
        int id = rand.nextInt(100);
        ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.of("UTC"));
        Person person = new Person(id,name,coordinates,zonedDateTimeNow,height,passportID+"",color,country,location);
        for (Person p:priorityQueue) {
            if(person.compareTo(p) > 0){
                System.err.println("Значение поля name больше, чем у наименьшего элемента этой коллекции");
                System.err.println("Элемент не будет добавлен в коллекцию");
                return;
            }
        }
        priorityQueue.add(person);
    }
    /**
     * Функция вывода элементов очереди по их Location{@link CollectionManager#filter_by_location(String)} ()}
     */
    public void filter_by_location(String location){
        Iterator<Person> iter = priorityQueue.iterator();
        Person person;
        if(!priorityQueue.isEmpty()){
            while (iter.hasNext()) {
                person = iter.next();
                if(person == null) break;
                else if(person.getLocation().equals(location)){
                    System.out.println("Обработка Person: location=" + person.getLocation());
                    System.out.println(person.getName());
                    System.out.println(person.getCoordinates());
                    System.out.println(person.getCreationDate());
                    System.out.println(person.getHeight());
                    System.out.println(person.getPassportID());
                    System.out.println(person.getHairColor());
                    System.out.println(person.getNationality());
                    System.out.println(person.getLocation());
                    System.out.println();
                }
            }
        }
        else {System.out.println("Коллекция пуста.");}
    }
    /**
     * Функция вывода элементов очереди по их Name {@link CollectionManager#filter_contains_name(String)} ()}
     */
    public void filter_contains_name(String name){
        Iterator<Person> iter = priorityQueue.iterator();
        Person person;
        if(!priorityQueue.isEmpty()){
            while (iter.hasNext()) {
                person = iter.next();
                if(person == null) break;
                else if(person.getName().contains(name)){
                    System.out.println("Обработка Person: name=" + person.getName());
                    System.out.println(person.getId());
                    System.out.println(person.getName());
                    System.out.println(person.getCoordinates());
                    System.out.println(person.getCreationDate());
                    System.out.println(person.getHeight());
                    System.out.println(person.getPassportID());
                    System.out.println(person.getHairColor());
                    System.out.println(person.getNationality());
                    System.out.println(person.getLocation());
                    System.out.println();
                }
            }
        }
        else {System.out.println("Коллекция пуста.");}
    }
    /**
     * Функция вывода элементов очереди по их Location, который меньше заданного значения {@link CollectionManager#filter_less_than_passport_i_d(String)} ()}
     */
    public void filter_less_than_passport_i_d(String passportID){
        Iterator<Person> iter = priorityQueue.iterator();
        Person person;
        if(!priorityQueue.isEmpty()){
            while (iter.hasNext()) {
                person = iter.next();
                if(person == null) break;
                else if(Long.parseLong(person.getPassportID())<Long.parseLong(passportID)){
                    System.out.println("Обработка Person: passportID < "+passportID+"=" + person.getPassportID());
                    System.out.println(person.getName());
                    System.out.println(person.getCoordinates());
                    System.out.println(person.getCreationDate());
                    System.out.println(person.getHeight());
                    System.out.println(person.getPassportID());
                    System.out.println(person.getHairColor());
                    System.out.println(person.getNationality());
                    System.out.println(person.getLocation());
                    System.out.println();
                }
            }
        }
        else {System.out.println("Коллекция пуста.");}
    }
}