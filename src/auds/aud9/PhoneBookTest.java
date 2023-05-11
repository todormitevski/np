package auds.aud9;

import java.util.*;

class DuplicateNumberException extends Exception{
    public DuplicateNumberException(String number) {
        super(String.format("Duplicate number: %s", number));
    }
}

class Contact{
    String name;
    String number;
    static Comparator<Contact> COMPARATOR = Comparator.comparing(Contact::getName).thenComparing(Contact::getNumber);

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
    Set<String> uniquePhoneNumbers;
    Map<String,Set<Contact>> contactsbyname;

    //null pointer prevention
    public PhoneBook() {
        uniquePhoneNumbers = new HashSet<>();
        contactsbyname = new HashMap<>();
    }

    public void addContact(String name, String number) throws DuplicateNumberException {
        Contact contact = new Contact(name,number);
        if(uniquePhoneNumbers.contains(number)){
            throw new DuplicateNumberException(number);
        } else{
            uniquePhoneNumbers.add(number);
        }

        contactsbyname.putIfAbsent(name,new TreeSet<>(Contact.COMPARATOR));
        contactsbyname.get(name).add(contact);
    }



    public void contactsByName(String name) {
        if (!contactsbyname.containsKey(name)){
            System.out.println("NOT FOUND");
        } else{
            Set<Contact> contacts = contactsbyname.get(name);
            contacts.forEach(System.out::println);
        }
    }

    public void contactsByNumber(String number) {

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