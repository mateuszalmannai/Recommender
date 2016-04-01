package ratings;

/**
 * Write a description of SecondRatings here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.*;

public class SecondRatings {
  private List<Movie> myMovies;
  private List<Rater> myRaters;
  private FirstRatings firstRatings;

  // default constructor
  public SecondRatings() {
    this("ratedmoviesfull.csv", "ratings.csv");
  }

  public SecondRatings(String movieFile, String ratingsFile) {
    firstRatings = new FirstRatings();
    myMovies = firstRatings.loadMovies(movieFile);
    myRaters = firstRatings.loadRaters(ratingsFile);
  }

  public int getMovieSize() {
    return myMovies.size();
  }

  public int getRaterSize() {
    return myRaters.size();
  }

  public List<Rating> getAverageRatings(int minimalRaters) {
    List<Rating> resultList = new ArrayList<>();
    for (Movie movie : myMovies) {
      String id = movie.getID();
      double averageByID = getAverageByID(id, minimalRaters);
      if (averageByID > 0) {
        resultList.add(new Rating(id, averageByID));
      }
    }
    return resultList;
  }

  private double getAverageByID(String id, int minimalRaters) {
    double average = 0;
    int numRatingsForMovie = firstRatings.getNumRatingsForMovie(id, myRaters);
    if (numRatingsForMovie >= minimalRaters) {
      double sum = 0;
      for (Rater rater : myRaters) {
        if (rater.hasRating(id)) {
          sum += rater.getRating(id);
        }
      }
      average = sum / numRatingsForMovie;
    }
    return average;
  }

  public String getTitle(String id) {
    String result = "No title found for id: " + id;
    for (Movie movie : myMovies) {
      if (movie.getID().equals(id)) {
        result = movie.getTitle();
      }
    }
    return result;
  }

  public String getID(String title) {
    String result = "NO SUCH TITLE";
    for (Movie movie : myMovies) {
      if (movie.getTitle().equals(title)) {
        result = movie.getID();
      }
    }
    return result;
  }


}
