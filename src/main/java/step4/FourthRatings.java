package step4;

import ratings.Rater;
import ratings.Rating;
import step3.Filter;
import step3.MovieDatabase;
import step3.TrueFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FourthRatings {

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
    int numRatingsForMovie = 0;
    for (Rater rater : RaterDatabase.getRaters()) {
      if (rater.hasRating(id)) {
        numRatingsForMovie++;
      }
    }
    if (numRatingsForMovie >= minimalRaters) {
      double sum = 0;
      for (Rater rater : RaterDatabase.getRaters()) {
        if (rater.hasRating(id)) {
          sum += rater.getRating(id);
        }
      }
      average = sum / numRatingsForMovie;
    }
    return average;
  }

  /**
   * @param minimalRaters:  the minimum number of ratings a movie must have
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

  /**
   * Method computes a similarity rating for each rater in the RaterDatabase (except the rater
   * with the ID given by the parameter) to see how similar they are to the Rater whose ID is
   * the parameter id.
   *
   * @param id
   * @return a list of Rating sorted by ratings from highest to lowest rating with the highest
   * rating first and only including those raters who have a positive similarity rating since
   * those with negative values are not similar in any way.
   * Note that in each Rating object the item field is a rater's ID and the value field is the
   * dot product comparison between that rater and the rater whose ID is the parameter id.
   */
  private List<Rating> getSimilarities(String id) {
    List<Rating> ratingList = new ArrayList<>();
    Rater me = RaterDatabase.getRater(id);
    RaterDatabase.getRaters().stream().filter(rater -> !rater.getID().equals(id)).forEach(rater -> {
      double dotProduct = dotProduct(me, rater);
      ratingList.add(new Rating(rater.getID(), dotProduct));
    });
    ratingList.sort(Collections.reverseOrder());
    return ratingList;
  }

  /**
   * Method translates ratings from the 0 to 10 to the scale -5 to 5
   *
   * @param me
   * @param rater
   * @return the dot product of the ratings of movies that both raters rated
   */
  private double dotProduct(Rater me, Rater rater) {
    double dotProduct = 0;
    for (String item : me.getItemsRated()) {
      if (rater.hasRating(item)) {
        dotProduct += (5 - me.getRating(item)) * (5 - rater.getRating(item));
      }
    }
    return dotProduct;
  }

  /**
   * @param id
   * @param numSimilarRaters
   * @param minimalRaters
   * @return list of type Rating, of movies and their weighted average ratings using only
   * the top numSimilarRaters with positive ratings and including only those movies that
   * have at leaset minimalRaters ratings from those top raters.
   * The Rating objects are returned in sorted order by weighted average rating from
   * largest to smallest ratings.
   */
  public List<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters) {
    List<Rating> ratingList = new ArrayList<>();

    List<Rating> similarities = getSimilarities(id);
    List<Rating> topRaters = new ArrayList<>();
    for (int i = 0; i < numSimilarRaters; i++) {
      topRaters.add(similarities.get(i));
    }

    List<String> movies = MovieDatabase.filterBy(new TrueFilter());
    for (String movieID : movies) {
      double curValue = getWeightedAverageByID(movieID, minimalRaters, topRaters);
      if (curValue > 0) {
        ratingList.add(new Rating(movieID, curValue));
      }
    }
    Collections.sort(ratingList, Collections.reverseOrder());
    return ratingList;
  }

  private double getWeightedAverageByID(String movieID, int minimalRaters, List<Rating> topRaters) {
    double average = 0;
    // count number of ratings for this movieID
    int count = 0;
    for (Rating rater : topRaters) {
      Rater curRater = RaterDatabase.getRater(rater.getItem());
      if (curRater.hasRating(movieID)) {
        count++;
      }
    }
    double sum = 0;
    if (count >= minimalRaters) {
      for (Rating rater : topRaters) {
        double rating = RaterDatabase.getRater(rater.getItem()).getRating(movieID);
        if (rating >= 0) {
          sum += rating * rater.getValue();
        }
      }
      average = sum / count;
    }
    return average;
  }

  public List<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters, Filter filterCriteria) {
    List<Rating> ratingList = new ArrayList<>();

    List<Rating> similarities = getSimilarities(id);
    List<Rating> topRaters = new ArrayList<>();
    for (int i = 0; i < numSimilarRaters; i++) {
      topRaters.add(similarities.get(i));
    }

    List<String> movies = MovieDatabase.filterBy(filterCriteria);
    for (String movieID : movies) {
      double curValue = getWeightedAverageByID(movieID, minimalRaters, topRaters);
      if (curValue > 0) {
        ratingList.add(new Rating(movieID, curValue));
      }
    }

    Collections.sort(ratingList, Collections.reverseOrder());
    return ratingList;
  }

}
