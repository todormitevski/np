package ispitni_re.old_exams;

import java.util.*;
import java.util.stream.Collectors;

class CategoryNotFoundException extends Exception{
    public CategoryNotFoundException(String category) {
        super(String.format("Category %s was not found", category));
    }
}

class Category{
    public String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

abstract class NewsItem{
    public String title;
    public Date datePublished;
    public Category category;

    public NewsItem(String title, Date datePublished, Category category) {
        this.title = title;
        this.datePublished = datePublished;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public abstract String getTeaser();
}

class TextNewsItem extends NewsItem{
    public String newsText;

    public TextNewsItem(String title, Date datePublished, Category category, String newsText) {
        super(title, datePublished, category);
        this.newsText = newsText;
    }

    @Override
    public String getTeaser() {
        return String.format(
                "%s\n%s\n%s",
                title,
                datePublished.getTime(),
                newsText.length() > 80 ? newsText.substring(0,80) : newsText
        );
    }
}

class MediaNewsItem extends NewsItem{
    public String url;
    public int views;

    public MediaNewsItem(String title, Date datePublished, Category category, String url, int views) {
        super(title, datePublished, category);
        this.url = url;
        this.views = views;
    }

    @Override
    public String getTeaser() {
        return String.format(
                "%s\n%d\n%s\n%d",
                title,
                datePublished.getTime(),
                url,
                views
        );
    }
}

class FrontPage{
    public Category[] categories;
    public List<NewsItem> newsItems;

    public FrontPage(Category[] categories) {
        this.categories = categories;
        this.newsItems = new ArrayList<>();
    }

    public void addNewsItem(NewsItem newsItem) {
        newsItems.add(newsItem);
    }

    public List<NewsItem> listByCategory(Category category) {
        return newsItems.stream()
                .filter(i -> i.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    public List<NewsItem> listByCategoryName(String category) throws CategoryNotFoundException {
        List<NewsItem> itemsToReturn = newsItems.stream()
                .filter(i -> i.getCategory().getName().equals(category))
                .collect(Collectors.toList());
        if(itemsToReturn.size() == 0)
            throw new CategoryNotFoundException(category);
        else return itemsToReturn;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (NewsItem ni : newsItems){
            sb.append(ni.getTeaser()).append("\n");
        }
        return sb.toString();
    }
}

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
