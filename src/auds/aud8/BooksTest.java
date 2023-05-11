package auds.aud8;

import java.util.*;
import java.util.stream.Collectors;

public class BooksTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        BookCollection booksCollection = new BookCollection();
        Set<String> categories = fillCollection(scanner, booksCollection);
        System.out.println("=== PRINT BY CATEGORY ===");
        for (String category : categories) {
            System.out.println("CATEGORY: " + category);
            booksCollection.printByCategory(category);
        }
        System.out.println("=== TOP N BY PRICE ===");
        print(booksCollection.getCheapestN(n));
    }

    static void print(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    static TreeSet<String> fillCollection(Scanner scanner,
                                          BookCollection collection) {
        TreeSet<String> categories = new TreeSet<String>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            Book book = new Book(parts[0], parts[1], Float.parseFloat(parts[2]));
            collection.addBook(book);
            categories.add(parts[1]);
        }
        return categories;
    }
}

// Вашиот код овде
class Book/* implements Comparable<Book>*/{
    String title;
    String category;
    double price;

    public Book(String title, String category, double price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }

    @Override
    public String toString() {
        // A Brief History of Time (A) 25.66
        return String.format("%s (%s) %.2f", title,category,price);
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Double.compare(book.price, price) == 0 && Objects.equals(title, book.title) && Objects.equals(category, book.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, category, price);
    }

    //    @Override
//    public int compareTo(Book o) {
//        int result = this.title.compareTo(o.title);
//        if(result == 0){ //if title is identical, compare by price
//            return Double.compare(this.price, o.price);
//        } else{
//            return result;
//        }
//    }
}

    // slowest way (most understandable)
//class TitleAndPriceComparator implements Comparator<Book> {
//    @Override
//    public int compare(Book o1, Book o2) {
//        int result = o1.title.compareTo(o2.title);
//        if(result == 0){ //if title is identical, compare by price
//            return Double.compare(o1.price, o2.price);
//        } else{
//            return result;
//        }
//    }
//}
//
//class PriceComparator implements Comparator<Book>{
//    @Override
//    public int compare(Book o1, Book o2) {
//        int result = Double.compare(o1.price, o2.price);
//        if(result == 0){
//            return o1.title.compareTo(o2.title);
//        } else{
//            return result;
//        }
//    }
//}

class BookCollection{
    Collection<Book> books;

    // improved way
//    final Comparator<Book> titleAndPriceComparator = (o1,o2) ->{
//        int res = o1.title.compareTo(o2.title);
//        if(res == 0){
//            return Double.compare(o1.price, o2.price);
//        } else{
//            return res;
//        }
//    };
//
//    final Comparator<Book> priceComparator = (o1,o2) ->{
//        int res = Double.compare(o1.price, o2.price);
//        if(res == 0){
//            return o1.title.compareTo(o2.title);
//        } else{
//            return res;
//        }
//    };

        //best way (no classes, no lambda)
    final Comparator<Book> titleAndPriceComparator = Comparator
            .comparing(Book::getTitle)
            .thenComparing(Book::getPrice);

    final Comparator<Book> priceComparator = Comparator
            .comparing(Book::getPrice)
            .thenComparing(Book::getCategory);

    public BookCollection() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void printByCategory(String category) {
        books.stream()
                .filter(each -> each.category.equalsIgnoreCase(category))
                .sorted(/*new TitleAndPriceComparator()*/ titleAndPriceComparator) //uses comparator to sort
                .forEach(System.out::println);
    }

    public List<Book> getCheapestN(int n) {
        return books.stream()
                .sorted(/*new PriceComparator()*/ priceComparator)
                .limit(n)
                .collect(Collectors.toList());
    }
}