package ratings;

import step3.Filter;
import step3.MovieDatabase;
import step3.TrueFilter;

import java.util.ArrayList;
import java.util.List;

public class ThirdRatings {
  private List<Rater> myRaters;
  private FirstRatings firstRatings;

  // default constructor
  public ThirdRatings() {
    this("ratings.csv");
  }

  public ThirdRatings(String ratingsFile) {
    firstRatings = new FirstRatings();
    myRaters = firstRatings.loadRaters(ratingsFile);
  }

  public int getRaterSize() {
    return myRaters.size();
  }

  public List<Rating> getAverageRatings(int minimalRaters) {
    List<String> movies = MovieDatabase.filterBy(new TrueFilter());
    List<Rating> resultList = new ArrayList<>();
    for (String movie : movies) {
      double averageByID = getAverageByID(movie, minimalRaters);
      if (averageByID > 0) {
        resultList.add(new Rating(movie, averageByID));
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

  /**
   *
   * @param minimalRaters: the minimum number of ratings a movie must have
   * @param filterCriteria: Filter
   * @return a list of type Rating of all the movies that have at least minimalRaters ratings
   * and satisfies the filter criteria
   */
  public List<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
    List<Rating> ratingList = new ArrayList<>();
    List<String> movies = MovieDatabase.filterBy(filterCriteria);
    for (String movie : movies) {
      double averageByID = getAverageByID(movie, minimalRaters);
      if (averageByID > 0) {
        ratingList.add(new Rating(movie, averageByID));
      }
    }
    return ratingList;
  }
}
