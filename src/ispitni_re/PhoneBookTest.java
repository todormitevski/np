package ispitni_re;

import java.util.*;
import java.util.stream.Collectors;

class DuplicateNumberException extends Exception{
    public DuplicateNumberException(String message) {
        super(message);
    }
}

class Contact{
    String name;
    String number;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return String.format("%s %s",name,number);
    }
}

class PhoneBook{
    Map<String,Contact> phoneBookMap;
    Set<Contact> phoneBookSet;

    public PhoneBook() {
        phoneBookMap = new HashMap<>();
        phoneBookSet = new TreeSet<>(Comparator.comparing(Contact::getNumber));
    }

    public void addContact(String name, String number) throws DuplicateNumberException {
        phoneBookSet.add(new Contact(name,number));
        if(phoneBookMap.containsKey(number))
            throw new DuplicateNumberException(String.format(
                    "Duplicate number: %s",
                    number
            ));
        phoneBookMap.putIfAbsent(number,new Contact(name,number));
    }

    public void contactsByNumber(String number) {
        List<Contact> matchingContacts = phoneBookMap.values().stream()
                .filter(i -> i.getNumber().contains(number))
                .sorted(Comparator.comparing(Contact::getName)
                        .thenComparing(Contact::getNumber))
                .collect(Collectors.toList());
        if(matchingContacts.size() == 0)
            System.out.println("NOT FOUND");
        else matchingContacts.forEach(System.out::println);
    }

    public void contactsByName(String name) {
        boolean found = false;
        for(Contact contact : phoneBookSet){
            if(contact.getName().equals(name)){
                System.out.println(contact);
                found = true;
            }
        }
        if(!found)
            System.out.println("NOT FOUND");
    }
}

public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
                phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }
    }
}

// Вашиот код овде

