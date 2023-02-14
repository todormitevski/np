package ispitni;

import com.sun.source.util.Trees;

import java.util.*;

class DuplicateNumberException extends Exception{
    public DuplicateNumberException(String number) {
        super(String.format("Duplicate number: %s",number));
    }
}

class Contact implements Comparable<Contact>{
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

    public boolean containsNumber(String num){
        return this.number.contains(num);
    }

    @Override
    public String toString() {
        return String.format("%s %s",name,number);
    }

    public List<String> splitEachThreeDigits(){
        List<String> threeDigits = new ArrayList<String>();
        for(int i=0;i<=number.length()-3;++i){
            for(int j=i+3;j<=number.length();++j){
                String digits = number.substring(i,j);
                threeDigits.add(digits);
            }
        }
        return threeDigits;
    }

    @Override
    public int compareTo(Contact o) {
        if(this.name.equals(o.name))
            return this.number.compareTo(o.number);
        else return this.name.compareTo(o.name);
    }
}

class PhoneBook{
    Map<String,Set<Contact>> contactsByNumber;
    Map<String,Set<Contact>> contactsByName;

    public PhoneBook() {
        contactsByNumber = new TreeMap<String,Set<Contact>>();
        contactsByName = new HashMap<String,Set<Contact>>();
    }

    void addContact(String name, String number) throws DuplicateNumberException {
        if(contactsByNumber.containsKey(number)){
            throw new DuplicateNumberException(number);
        }
        Contact contact = new Contact(name,number);
        List<String> threeDigits = contact.splitEachThreeDigits();
        for(String threeDigit : threeDigits){
            Set<Contact> contacts = contactsByNumber.get(threeDigit);
            if(contacts == null){
                contacts = new TreeSet<Contact>();
                contactsByNumber.put(threeDigit,contacts);
            }
            contacts.add(contact);
        }
        Set<Contact> nameContacts = contactsByName.get(name);
        if(nameContacts == null){
            nameContacts = new TreeSet<Contact>();
            contactsByName.put(name,nameContacts);
        }
        nameContacts.add(contact);
    }

    void contactsByNumber(String number){
//        Set<Contact> contacts = contactsByNumber.get(number);
//        if(contacts == null)
//            System.out.println("NOT FOUND");
//        else contacts.stream()
//                .filter(contact -> contact.containsNumber(number))
//                .forEach(System.out::println);
    }

    void contactsByName(String name){
//        Set<Contact> contacts = contactsByName.get(name);
//        if(contacts == null)
//            System.out.println("NOT FOUND");
//        else contacts.forEach(System.out::println);
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