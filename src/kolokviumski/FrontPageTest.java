package kolokviumski;

import java.util.*;

public class FrontPageTest {
    public static void main(String[] args) {
        // Reading
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        Category[] categories = new Category[parts.length];
        for (int i = 0; i < categories.length; ++i) {
            categories[i] = new Category(parts[i]);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        FrontPage frontPage = new FrontPage(categories);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            cal = Calendar.getInstance();
            int min = scanner.nextInt();
            cal.add(Calendar.MINUTE, -min);
            Date date = cal.getTime();
            scanner.nextLine();
            String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
            frontPage.addNewsItem(tni);
        }

        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -min);
            scanner.nextLine();
            Date date = cal.getTime();
            String url = scanner.nextLine();
            int views = scanner.nextInt();
            scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
            frontPage.addNewsItem(mni);
        }
        // Execution
        String category = scanner.nextLine();
        System.out.println(frontPage);
        for(Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch(CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}


// Vasiot kod ovde
abstract class NewsItem{
    protected String title;
    protected Date publishDate;
    protected Category category;

    public NewsItem(String title, Date publishDate, Category category) {
        this.title = title;
        this.publishDate = publishDate;
        this.category = category;
    }

    public int when(){
        Date now = new Date();
        long ms = now.getTime() - publishDate.getTime();
        return (int)(ms/1000)/60;
    }

    public abstract String getTeaser();
}

class Category{
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        Category c = (Category)obj; //cannot .equals on Object
        return this.name.equals(c.name);
    }
}

class TextNewsItem extends NewsItem{
    String text;

    private static final int LEN = 80;

    public TextNewsItem(String title, Date publishDate, Category category, String text) {
        super(title, publishDate, category);
        this.text=text;
    }

    @Override
    public String getTeaser() {
        String teaser = text;
        if(text.length()>LEN){
            teaser = text.substring(0, LEN);
        }
        return String.format("%s\n%d\n%s\n", title, when(), teaser);
    }
}

class MediaNewsItem extends NewsItem{
    String url;
    int views;

    public MediaNewsItem(String title, Date publishDate, Category category, String url, int views) {
        super(title, publishDate, category);
        this.url=url;
        this.views=views;
    }

    @Override
    public String getTeaser() {
        return String.format("%s\n%d\n%s\n%d\n", title, when(), url, views);
    }
}

class FrontPage{
    ArrayList<NewsItem> newsItems;
    Category[] categories;

    public FrontPage(Category[] categories) {
        this.categories = categories;
        this.newsItems = new ArrayList<NewsItem>();
    }

    void addNewsItem(NewsItem newsItem){
        newsItems.add(newsItem);
    }

    ArrayList<NewsItem> listByCategory(Category category){
        ArrayList<NewsItem> filteredList = new ArrayList<NewsItem>();
        for(NewsItem ni : newsItems){
            if(ni.category.equals(category)) {
                filteredList.add(ni);
            }
        }
        return filteredList;
    }

    ArrayList<NewsItem> listByCategoryName(String name) throws CategoryNotFoundException{

        int flag = 0, index = 0;

        for (int i=0;i<categories.length;i++){
            if(categories[i].getName().equals(name)){
                index=i;
                flag = 1;
                break;
            }
        }

        if(flag == 0)
            throw new CategoryNotFoundException(name);
        return listByCategory(categories[index]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(NewsItem ni : newsItems){
            sb.append(ni.getTeaser());
        }
        return sb.toString();
    }
}

class CategoryNotFoundException extends Exception{
    public CategoryNotFoundException(String message){
        super(String.format("Category %s was not found",
                message));
    }
}