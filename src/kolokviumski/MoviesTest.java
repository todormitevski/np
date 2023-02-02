package kolokviumski;

import java.util.*;
import java.util.stream.Collectors;

class Movie{
    String title;
    int[] ratings;

    public Movie(String title, int[] ratings) {
        this.title = title;
        this.ratings = ratings;
    }

    public String getTitle() {
        return title;
    }

    public int[] getRatings() {
        return ratings;
    }

    public double calcRating(){
        double sum = 0.0;
        for(int i=0;i<ratings.length;i++){
            sum += ratings[i];
        }
        return sum / ratings.length;
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f) of %d ratings",title,calcRating(),ratings.length);
    }
}

class MoviesList{
    List<Movie> movies;

    public MoviesList() {
        movies = new ArrayList<Movie>();
    }

    public void addMovie(String title, int[] ratings){
        Movie movie = new Movie(title,ratings);
        movies.add(movie);
    }

    public List<Movie> top10ByAvgRating(){
        List<Movie> top10MoviesByAvgRatings = new ArrayList<>();

        top10MoviesByAvgRatings = movies.stream()
                .sorted(Comparator.comparing(Movie::calcRating).reversed()
                        .thenComparing(Movie::getTitle))
                .limit(10)
                .collect(Collectors.toList());

        return top10MoviesByAvgRatings;
    }

    int maxNumOfRatings(){
        int max = 0;
        for(int i=0;i<movies.size();i++){
            if(movies.get(i).ratings.length > max){
                max = movies.get(i).ratings.length;
            }
        }
        return max;
    }

    public List<Movie> top10ByRatingCoef(){
        movies.sort(new RatingCoefComparator(maxNumOfRatings()));
        if(movies.size() >= 10){
            return movies.subList(0,10);
        } else return movies;
    }
}

class RatingCoefComparator implements Comparator<Movie>{
    int maxRating;

    public RatingCoefComparator(int maxRating) {
        this.maxRating = maxRating;
    }

    @Override
    public int compare(Movie o1, Movie o2) {
        int ar = Double.compare(
                o1.calcRating() * o1.getRatings().length / maxRating,
                o2.calcRating() * o2.getRatings().length / maxRating
                );
        if (ar == 0) {
            return o1.title.compareTo(o2.title);
        }
        return -ar;
    }
}

public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}
