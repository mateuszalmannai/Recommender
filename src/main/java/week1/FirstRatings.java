package week1;

import duke.FileResource;
import org.apache.commons.csv.CSVRecord;

import java.util.*;
import java.util.stream.Collectors;

public class FirstRatings {

  private static final String BASE_PATH = "/Users/mateusz/IdeaProjects/Recommender/src/main/resources/data/";

  /**
   * Method should process every record from the CSV file whose name is filename
   *
   * @param filename
   * @return a list of type Movie with all of the movie data from the file.
   */
  public List<Movie> loadMovies(String filename) {
    final List<Movie> resultList = new ArrayList<>();
    final FileResource fileResource = new FileResource(BASE_PATH + filename);
//    id,title,year,country,genre,director,minutes,poster
    for (CSVRecord record : fileResource.getCSVParser()) {
      Movie movie = new Movie(
        record.get("id"),
        record.get("title"),
        record.get("year"),
        record.get("genre"),
        record.get("director"),
        record.get("country"),
        record.get("poster"),
        Integer.valueOf(record.get("minutes")));
      resultList.add(movie);
    }

    return resultList;
  }

  public List<Movie> filterByGenre(String targetGenre, List<Movie> allMovies) {
    return allMovies.stream().filter(movie -> movie.getGenres().contains(targetGenre)).collect(Collectors.toList());
  }

  public List<Movie> filterByGreaterThanMinimumLength(int minutes, List<Movie> allMovies) {
    return allMovies.stream().filter(movie -> movie.getMinutes() > minutes).collect(Collectors.toList());
  }

  public Map<String, Integer> moviesPerDirector(List<Movie> allMovies) {
    Map<String, Integer> counters = new HashMap<>();
    for (Movie movie : allMovies) {
      String[] directorArray = movie.getDirector().split(", ");
      for (String director : directorArray) {
        if (!counters.containsKey(director)) {
          counters.put(director, 1);
        } else {
          counters.put(director, counters.get(director) + 1);
        }
      }
    }
    return counters;
  }

  public List<Rater> loadRaters(String filename) {
    final List<Rater> resultList = new ArrayList<>();
    final FileResource fileResource = new FileResource(BASE_PATH + filename);
    // rater_id,movie_id,rating,time
    for (CSVRecord record : fileResource.getCSVParser()) {
      String raterID = record.get("rater_id");
      String movieID = record.get("movie_id");
      Double rating = Double.valueOf(record.get("rating"));

      int raterIndex = raterIndex(resultList, raterID);
      if (raterIndex == -1) {
        Rater currentRater = new Rater(raterID);
        currentRater.addRating(movieID, rating);
        resultList.add(currentRater);
      } else {
        resultList.get(raterIndex).addRating(movieID, rating);
      }
    }
    return resultList;
  }


  private int raterIndex(List<Rater> raterList, String raterID) {
    int result = -1;
    for (int i = 0; i < raterList.size(); i++) {
      if (raterList.get(i).getID().equals(raterID)) {
        result = i;
      }
    }
    return result;
  }

  public int getRatingsForRater(int raterID, List<Rater> raterList) {
    int result = 0;
    for (Rater rater : raterList) {
      if (rater.getID().equals(String.valueOf(raterID))) {
        result = rater.numRatings();
      }
    }
    return result;
  }

  public int getMaxRatings(List<Rater> raterList) {
    int maxRatings = 0;
    for (Rater rater : raterList) {
      if (rater.numRatings() > maxRatings) {
        maxRatings = rater.numRatings();
      }
    }
    return maxRatings;
  }

  public List<Rater> getRatersWithNumRatings(int targetRatings, List<Rater> raterList) {
    return raterList.stream().filter(rater -> rater.numRatings() == targetRatings).collect(Collectors.toList());
  }

  public int getNumRatingsForMovie(String movieID, List<Rater> raterList) {
    int ratingsCount = 0;
    for (Rater rater : raterList) {
      if (rater.hasRating(movieID)) {
        ratingsCount++;
      }
    }
    return ratingsCount;
  }

  public int getNumOfRatedMovies(List<Rater> raterList) {
    List<String> movieList = new ArrayList<>();
    for (Rater rater : raterList) {
      for (String movie : rater.getItemsRated()) {
        if (!movieList.contains(movie)) {
          movieList.add(movie);
        }
      }
    }
    return movieList.size();
  }
}
