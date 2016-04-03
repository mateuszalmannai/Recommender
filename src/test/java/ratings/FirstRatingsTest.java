package ratings;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class FirstRatingsTest {

  private static final String SHORT_MOVIES = "ratedmovies_short.csv";
  private static final String FULL_MOVIES = "ratedmoviesfull.csv";
  private static final String SHORT_RATINGS = "ratings_short.csv";
  private static final String FULL_RATINGS = "ratings.csv";
  private FirstRatings firstRatings;

  @Before
  public void setUp() throws Exception {
    firstRatings = new FirstRatings();
  }

  @Test
  public void testLoadMoviesOnSmallFile() throws Exception {
    List<Movie> movies = firstRatings.loadMovies(SHORT_MOVIES);
    assertThat(movies.size(), is(5));
  }

  @Test
  public void testLoadMoviesOnLargerFile() throws Exception {
    List<Movie> movies = firstRatings.loadMovies(FULL_MOVIES);
    assertThat(movies.size(), is(3143));
  }

  @Test
  public void testFilterByGenreOnSmallFile() throws Exception {
    List<Movie> movies = firstRatings.loadMovies(SHORT_MOVIES);
    List<Movie> comedyList = firstRatings.filterByGenre("Comedy", movies);
    assertThat(comedyList.size(), is(1));
  }

  @Test
  public void testFilterByGenreOnLargerFile() throws Exception {
    List<Movie> movies = firstRatings.loadMovies(FULL_MOVIES);
    List<Movie> comedyList = firstRatings.filterByGenre("Comedy", movies);
    assertThat(comedyList.size(), is(960));
  }

  @Test
  public void testFilterByGreaterThanMinimumLengthOnSmallFile() throws Exception {
    List<Movie> movies = firstRatings.loadMovies(SHORT_MOVIES);
    List<Movie> moviesGreaterThan150 = firstRatings.filterByGreaterThanMinimumLength(150, movies);
    assertThat(moviesGreaterThan150.size(), is(2));
  }

  @Test
  public void testFilterByGreaterThanMinimumLengthOnLargerFile() throws Exception {
    List<Movie> movies = firstRatings.loadMovies(FULL_MOVIES);
    List<Movie> moviesGreaterThan150 = firstRatings.filterByGreaterThanMinimumLength(150, movies);
    assertThat(moviesGreaterThan150.size(), is(132));
  }

  @Test
  public void testMoviesPerDirectorOnSmallFile() throws Exception {
    List<Movie> movies = firstRatings.loadMovies(SHORT_MOVIES);
    Map<String, Integer> moviesPerDirector = firstRatings.moviesPerDirector(movies);
    assertThat(moviesPerDirector.keySet().size(), is(5));
    System.out.println(moviesPerDirector);
  }

  @Test
  public void testMoviesPerDirectorOnLargerFile() throws Exception {
    List<Movie> movies = firstRatings.loadMovies(FULL_MOVIES);
    Map<String, Integer> moviesPerDirector = firstRatings.moviesPerDirector(movies);
    assertThat(moviesPerDirector.keySet().size(), is(2073));
    int max = 0;
    String directorWithMostMovies = "null";
    for (String director : moviesPerDirector.keySet()) {
      Integer numberOfMovies = moviesPerDirector.get(director);
      if (numberOfMovies > max) {
        max = numberOfMovies;
        directorWithMostMovies = director;
      }
    }
    System.out.println(directorWithMostMovies + ":  " + max);
  }

  @Test
  public void testLoadRatersOnSmallFile() throws Exception {
    List<Rater> raterList = firstRatings.loadRaters(SHORT_RATINGS);
    for (Rater rater : raterList) {
      System.out.println(rater.getID() + " " + rater.numRatings());
      for (String item : rater.getItemsRated()) {
        System.out.println(item + " " + rater.getRating(item));
      }
    }
    assertThat(raterList.size(), is(5));
  }

  @Test
  public void testLoadRatersOnLargerFile() throws Exception {
    List<Rater> raterList = firstRatings.loadRaters(FULL_RATINGS);
    assertThat(raterList.size(), is(1048));
  }

  @Test
  public void testGetNumRatingsForRaterFromLargerFile() throws Exception {
    List<Rater> raterList = firstRatings.loadRaters(FULL_RATINGS);
    int ratings = firstRatings.getRatingsForRater(193, raterList);
    assertThat(ratings, is(119));
  }

  @Test
  public void testGetNumRatingsForRaterFromSmallFile() throws Exception {
    List<Rater> raterList = firstRatings.loadRaters(SHORT_RATINGS);
    int ratings = firstRatings.getRatingsForRater(2, raterList);
    assertThat(ratings, is(3));
  }

  @Test
  public void testGetMaxRatingsFromLargerFile() throws Exception {
    List<Rater> raterList = firstRatings.loadRaters(FULL_RATINGS);
    int maxRatings = firstRatings.getMaxRatings(raterList);
    assertThat(maxRatings, is(314));
    List<Rater> ratersWithMaxRatings = firstRatings.getRatersWithNumRatings(314, raterList);
    assertThat(ratersWithMaxRatings.get(0).getID(), is("735"));
  }

  @Test
  public void testGetMaxRatingsFromSmallFile() throws Exception {
    List<Rater> raterList = firstRatings.loadRaters(SHORT_RATINGS);
    int maxRatings = firstRatings.getMaxRatings(raterList);
    assertThat(maxRatings, is(3));
  }

  @Test
  public void testRatersWithNumRatings() throws Exception {
    List<Rater> raterList = firstRatings.loadRaters(SHORT_RATINGS);
    List<Rater> ratersWithNumRatings = firstRatings.getRatersWithNumRatings(3, raterList);
    assertThat(ratersWithNumRatings.size(), is(1));
    assertThat(ratersWithNumRatings.get(0).getID(), is("2"));
  }

  @Test
  public void testGetNumRatingsForMovieFromLargerFile() throws Exception {
    List<Rater> raterList = firstRatings.loadRaters(FULL_RATINGS);
    int numRatingsForMovie = firstRatings.getNumRatingsForMovie("1798709", raterList);
    assertThat(numRatingsForMovie, is(38));
  }

  @Test
  public void testGetNumRatingsForMovie() throws Exception {
    List<Rater> raterList = firstRatings.loadRaters(SHORT_RATINGS);
    int numRatingsForMovie = firstRatings.getNumRatingsForMovie("1798709", raterList);
    assertThat(numRatingsForMovie, is(4));
  }

  @Test
  public void testGetNumOfRatedMoviesFromShortFile() throws Exception {
    List<Rater> raterList = firstRatings.loadRaters(SHORT_RATINGS);
    int numOfRatedMovies = firstRatings.getNumOfRatedMovies(raterList);
    assertThat(numOfRatedMovies, is(4));
  }

  @Test
  public void testGetNumOfRatedMoviesFromLargerFile() throws Exception {
    List<Rater> raterList = firstRatings.loadRaters(FULL_RATINGS);
    int numOfRatedMovies = firstRatings.getNumOfRatedMovies(raterList);
    assertThat(numOfRatedMovies, is(3143));
  }
}