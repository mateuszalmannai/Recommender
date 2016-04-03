package step3;

import ratings.FirstRatings;
import ratings.Movie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieDatabase {
  private static Map<String, Movie> ourMovies;

  private static final String BASE_PATH = "/Users/mateusz/IdeaProjects/Recommender/src/main/resources/data/";

  public static void initialize(String movieFile) {
    if (ourMovies == null) {
      ourMovies = new HashMap<>();
      loadMovies(movieFile);
    }
  }

  /**
   * This method will load the movie file ratedmoviesfull.csv if
   * no file has been loaded using the public constructor.
   * This method is called as a safety check with any of the other
   * public methods to make sure there is movie data in the database;
   */
  private static void initialize() {
    if (ourMovies == null) {
      ourMovies = new HashMap<>();
      loadMovies("ratedmoviesfull.csv");
    }
  }


  /**
   * Build the HashMap
   * @param filename
   */
  private static void loadMovies(String filename) {
    FirstRatings fr = new FirstRatings();
    List<Movie> list = fr.loadMovies(filename);
    for (Movie m : list) {
      ourMovies.put(m.getID(), m);
    }
  }

  /**
   * @param id
   * @return true if the id is a movie in the database,
   * and false otherwise
   */
  public static boolean containsID(String id) {
    initialize();
    return ourMovies.containsKey(id);
  }

  public static int getYear(String id) {
    initialize();
    return ourMovies.get(id).getYear();
  }

  public static String getGenres(String id) {
    initialize();
    return ourMovies.get(id).getGenres();
  }

  public static String getTitle(String id) {
    initialize();
    return ourMovies.get(id).getTitle();
  }

  public static Movie getMovie(String id) {
    initialize();
    return ourMovies.get(id);
  }

  public static String getPoster(String id) {
    initialize();
    return ourMovies.get(id).getPoster();
  }

  public static int getMinutes(String id) {
    initialize();
    return ourMovies.get(id).getMinutes();
  }

  public static String getCountry(String id) {
    initialize();
    return ourMovies.get(id).getCountry();
  }

  public static String getDirector(String id) {
    initialize();
    return ourMovies.get(id).getDirector();
  }

  public static int size() {
    return ourMovies.size();
  }

  /**
   * @param f
   * @return a List of type String of movie IDs that match the filtering criteria
   */
  public static List<String> filterBy(Filter f) {
    initialize();
    return ourMovies.keySet().stream().filter(id -> f.satisfies(id)).collect(Collectors.toList());
  }

}
