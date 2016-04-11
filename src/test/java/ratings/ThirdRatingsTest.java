package ratings;

import org.junit.Before;
import org.junit.Test;
import step3.*;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * The tests which initialize the MovieDatabase with the large dataset are ignored
 * as they can't be run simultaneously with the smaller dataset, since we have
 * no control over the sequence the tests are run in and the static instance is shared
 */
public class ThirdRatingsTest {

  private static final String SHORT_MOVIES = "ratedmovies_short.csv";
  private static final String SHORT_RATINGS = "ratings_short.csv";

  private static final String FULL_RATINGS = "ratings.csv";

  @Before
  public void setUp() throws Exception {
    MovieDatabase.resetDatabase();
  }

  @Test
  public void testGetRaterSize() throws Exception {
    ThirdRatings thirdRatings = new ThirdRatings(SHORT_RATINGS);
    assertThat(thirdRatings.getRaterSize(), is(5));
    thirdRatings = new ThirdRatings();
    assertThat(thirdRatings.getRaterSize(), is(1048));
  }

  @Test
  public void testGetAverageRatings() throws Exception {
    MovieDatabase.initialize(SHORT_MOVIES);
    ThirdRatings thirdRatings = new ThirdRatings(SHORT_RATINGS);
    List<Rating> averageRatings = thirdRatings.getAverageRatings(3);
    assertThat(averageRatings.size(), is(2));
    assertThat(averageRatings.get(0).getValue(), is(9.0));
    assertThat(averageRatings.get(0).getItem(), is("0068646"));
    assertThat(averageRatings.get(1).getValue(), is(8.25));
    assertThat(averageRatings.get(1).getItem(), is("1798709"));
  }

  @Test
  public void testAverageRatingsQuiz() throws Exception {
    ThirdRatings thirdRatings = new ThirdRatings(FULL_RATINGS);
    List<Rating> averageRatings = thirdRatings.getAverageRatings(35);
    assertThat(averageRatings.size(),is(29));
  }

  @Test
  public void testPrintAverageRatings() throws Exception {
    MovieDatabase.initialize(SHORT_MOVIES);
    ThirdRatings thirdRatings = new ThirdRatings(SHORT_RATINGS);
    List<Rating> averageRatings = thirdRatings.getAverageRatings(1);
    printRatings(thirdRatings, averageRatings);
  }

  @Test
  public void testAverageRatingsByYearAfterQuiz() throws Exception {
    ThirdRatings thirdRatings = new ThirdRatings(FULL_RATINGS);
    YearAfterFilter yearAfterFilter = new YearAfterFilter(2000);
    List<Rating> averageRatingsByFilter = thirdRatings.getAverageRatingsByFilter(20, yearAfterFilter);
    assertThat(averageRatingsByFilter.size(), is(88));
  }

  @Test
  public void testPrintAverageRatingsByFilter() throws Exception {
    MovieDatabase.initialize(SHORT_MOVIES);
    ThirdRatings thirdRatings = new ThirdRatings(SHORT_RATINGS);
    YearAfterFilter yearAfterFilter = new YearAfterFilter(2000);
    List<Rating> averageRatingsByFilter = thirdRatings.getAverageRatingsByFilter(1, yearAfterFilter);
    assertThat(averageRatingsByFilter.size(), is(2));

    String movieID1 = averageRatingsByFilter.get(0).getItem();
    String movieID2 = averageRatingsByFilter.get(1).getItem();

    assertThat(MovieDatabase.getTitle(movieID2), is("Dallas Buyers Club"));
    assertThat(averageRatingsByFilter.get(1).getValue(), is(7.0));
    assertThat(MovieDatabase.getGenres(movieID2), is("Biography, Drama"));
    assertThat(MovieDatabase.getDirector(movieID2), is("Jean-Marc Vallée"));
    assertThat(MovieDatabase.getMinutes(movieID2), is(117));

    assertThat(MovieDatabase.getTitle(movieID1), is("Her"));
    assertThat(averageRatingsByFilter.get(0).getValue(), is(8.25));
    assertThat(MovieDatabase.getGenres(movieID1), is("Drama, Romance, Sci-Fi"));
    assertThat(MovieDatabase.getDirector(movieID1), is("Spike Jonze"));
    assertThat(MovieDatabase.getMinutes(movieID1), is(126));
  }

  @Test
  public void testAverageRatingsByGenreQuiz() throws Exception {
    ThirdRatings thirdRatings = new ThirdRatings(FULL_RATINGS);
    GenreFilter genreFilter = new GenreFilter("Comedy");
    List<Rating> averageRatingsByFilter = thirdRatings.getAverageRatingsByFilter(20, genreFilter);
    assertThat(averageRatingsByFilter.size(), is(19));
  }

  @Test
  public void testPrintAverageRatingsByGenre() throws Exception {
    MovieDatabase.initialize(SHORT_MOVIES);
    ThirdRatings thirdRatings = new ThirdRatings(SHORT_RATINGS);
    GenreFilter genreFilter = new GenreFilter("Crime");
    List<Rating> averageRatingsByFilter = thirdRatings.getAverageRatingsByFilter(1, genreFilter);
    assertThat(averageRatingsByFilter.size(), is(2));

    String movieID1 = averageRatingsByFilter.get(0).getItem();
    String movieID2 = averageRatingsByFilter.get(1).getItem();

    assertThat(MovieDatabase.getTitle(movieID1), is("The Godfather"));
    assertThat(averageRatingsByFilter.get(0).getValue(), is(9.0));
    assertThat(MovieDatabase.getGenres(movieID1), is("Crime, Drama"));
    assertThat(MovieDatabase.getDirector(movieID1), is("Francis Ford Coppola"));
    assertThat(MovieDatabase.getMinutes(movieID1), is(175));

    assertThat(MovieDatabase.getTitle(movieID2), is("Heat"));
    assertThat(averageRatingsByFilter.get(1).getValue(), is(10.0));
    assertThat(MovieDatabase.getGenres(movieID2), is("Action, Crime, Drama"));
    assertThat(MovieDatabase.getDirector(movieID2), is("Michael Mann"));
    assertThat(MovieDatabase.getMinutes(movieID2), is(170));
  }

  @Test
  public void testAverageRatingsByMinutesQuiz() throws Exception {
    ThirdRatings thirdRatings = new ThirdRatings(FULL_RATINGS);
    MinutesFilter minutesFilter = new MinutesFilter(105, 135);
    List<Rating> averageRatingsByFilter = thirdRatings.getAverageRatingsByFilter(5, minutesFilter);
    assertThat(averageRatingsByFilter.size(), is(231));
  }

  @Test
  public void testPrintAverageRatingsByMinutes() throws Exception {
    MovieDatabase.initialize(SHORT_MOVIES);
    ThirdRatings thirdRatings = new ThirdRatings(SHORT_RATINGS);
    MinutesFilter minutesFilter = new MinutesFilter(110, 170);
    List<Rating> averageRatingsByFilter = thirdRatings.getAverageRatingsByFilter(1, minutesFilter);

    assertThat(averageRatingsByFilter.size(), is(3));
    String movieID1 = averageRatingsByFilter.get(0).getItem();
    String movieID2 = averageRatingsByFilter.get(1).getItem();
    String movieID3 = averageRatingsByFilter.get(2).getItem();

    assertThat(MovieDatabase.getTitle(movieID2), is("Dallas Buyers Club"));
    assertThat(averageRatingsByFilter.get(1).getValue(), is(7.0));
    assertThat(MovieDatabase.getGenres(movieID2), is("Biography, Drama"));
    assertThat(MovieDatabase.getDirector(movieID2), is("Jean-Marc Vallée"));
    assertThat(MovieDatabase.getMinutes(movieID2), is(117));

    assertThat(MovieDatabase.getTitle(movieID1), is("Her"));
    assertThat(averageRatingsByFilter.get(0).getValue(), is(8.25));
    assertThat(MovieDatabase.getGenres(movieID1), is("Drama, Romance, Sci-Fi"));
    assertThat(MovieDatabase.getDirector(movieID1), is("Spike Jonze"));
    assertThat(MovieDatabase.getMinutes(movieID1), is(126));

    assertThat(MovieDatabase.getTitle(movieID3), is("Heat"));
    assertThat(averageRatingsByFilter.get(2).getValue(), is(10.0));
    assertThat(MovieDatabase.getGenres(movieID3), is("Action, Crime, Drama"));
    assertThat(MovieDatabase.getDirector(movieID3), is("Michael Mann"));
    assertThat(MovieDatabase.getMinutes(movieID3), is(170));
  }

  @Test
  public void testAverageRatingsByDirectorQuiz() throws Exception {
    ThirdRatings thirdRatings = new ThirdRatings(FULL_RATINGS);
    DirectorsFilter directorsFilter = new DirectorsFilter("Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack");
    List<Rating> averageRatingsByFilter = thirdRatings.getAverageRatingsByFilter(4, directorsFilter);
    assertThat(averageRatingsByFilter.size(), is(22));
  }

  @Test
  public void testPrintAverageRatingsByDirector() throws Exception {
    MovieDatabase.initialize(SHORT_MOVIES);
    ThirdRatings thirdRatings = new ThirdRatings(SHORT_RATINGS);
    DirectorsFilter directorsFilter = new DirectorsFilter("Charles Chaplin,Michael Mann,Spike Jonze");
    List<Rating> averageRatingsByFilter = thirdRatings.getAverageRatingsByFilter(1, directorsFilter);
    assertThat(averageRatingsByFilter.size(), is(2));
    String movieID1 = averageRatingsByFilter.get(0).getItem();
    String movieID2 = averageRatingsByFilter.get(1).getItem();
    assertThat(MovieDatabase.getTitle(movieID1), is("Her"));
    assertThat(averageRatingsByFilter.get(0).getValue(), is(8.25));
    assertThat(MovieDatabase.getGenres(movieID1), is("Drama, Romance, Sci-Fi"));
    assertThat(MovieDatabase.getDirector(movieID1), is("Spike Jonze"));
    assertThat(MovieDatabase.getMinutes(movieID1), is(126));

    assertThat(MovieDatabase.getTitle(movieID2), is("Heat"));
    assertThat(averageRatingsByFilter.get(1).getValue(), is(10.0));
    assertThat(MovieDatabase.getGenres(movieID2), is("Action, Crime, Drama"));
    assertThat(MovieDatabase.getDirector(movieID2), is("Michael Mann"));
    assertThat(MovieDatabase.getMinutes(movieID2), is(170));
  }

  @Test
  public void testAverageRatingsByYearAfterAndGenreQuiz() throws Exception {
    ThirdRatings thirdRatings = new ThirdRatings(FULL_RATINGS);
    YearAfterFilter yearAfterFilter = new YearAfterFilter(1990);
    GenreFilter genreFilter = new GenreFilter("Drama");
    AllFilters allFilters = new AllFilters();
    allFilters.addFilter(yearAfterFilter);
    allFilters.addFilter(genreFilter);
    List<Rating> averageRatingsByFilter = thirdRatings.getAverageRatingsByFilter(8, allFilters);
    assertThat(averageRatingsByFilter.size(), is(132));
  }

  @Test
  public void testAverageRatingsByYearAfterAndGenre() throws Exception {
    MovieDatabase.initialize(SHORT_MOVIES);
    ThirdRatings thirdRatings = new ThirdRatings(SHORT_RATINGS);
    YearAfterFilter yearAfterFilter = new YearAfterFilter(1980);
    GenreFilter genreFilter = new GenreFilter("Romance");
    AllFilters allFilters = new AllFilters();
    allFilters.addFilter(yearAfterFilter);
    allFilters.addFilter(genreFilter);
    List<Rating> averageRatingsByFilter = thirdRatings.getAverageRatingsByFilter(1, allFilters);
    assertThat(averageRatingsByFilter.size(), is(1));
    String movieID = averageRatingsByFilter.get(0).getItem();
    assertThat(MovieDatabase.getTitle(movieID), is("Her"));
    assertThat(averageRatingsByFilter.get(0).getValue(), is(8.25));
    assertThat(MovieDatabase.getGenres(movieID), is("Drama, Romance, Sci-Fi"));
    assertThat(MovieDatabase.getDirector(movieID), is("Spike Jonze"));
    assertThat(MovieDatabase.getMinutes(movieID), is(126));
  }

  @Test
  public void testAverageRatingsByDirectorsAndMinutesQuiz() throws Exception {
    ThirdRatings thirdRatings = new ThirdRatings(FULL_RATINGS);
    DirectorsFilter directorsFilter = new DirectorsFilter("Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack");
    MinutesFilter minutesFilter = new MinutesFilter(90, 180);
    AllFilters allFilters = new AllFilters();
    allFilters.addFilter(directorsFilter);
    allFilters.addFilter(minutesFilter);
    List<Rating> averageRatingsByFilter = thirdRatings.getAverageRatingsByFilter(3, allFilters);
    assertThat(averageRatingsByFilter.size(), is(15));
  }

  @Test
  public void testAverageRatingsByDirectorsAndMinutes() throws Exception {
    MovieDatabase.initialize(SHORT_MOVIES);
    ThirdRatings thirdRatings = new ThirdRatings(SHORT_RATINGS);
    DirectorsFilter directorsFilter = new DirectorsFilter("Spike Jonze,Michael Mann,Charles Chaplin,Francis Ford Coppola");
    MinutesFilter minutesFilter = new MinutesFilter(30, 170);
    AllFilters allFilters = new AllFilters();
    allFilters.addFilter(directorsFilter);
    allFilters.addFilter(minutesFilter);
    List<Rating> averageRatingsByFilter = thirdRatings.getAverageRatingsByFilter(1, allFilters);
    assertThat(averageRatingsByFilter.size(), is(2));

    String movieID1 = averageRatingsByFilter.get(0).getItem();
    String movieID2 = averageRatingsByFilter.get(1).getItem();

    assertThat(MovieDatabase.getTitle(movieID1), is("Her"));
    assertThat(averageRatingsByFilter.get(0).getValue(), is(8.25));
    assertThat(MovieDatabase.getGenres(movieID1), is("Drama, Romance, Sci-Fi"));
    assertThat(MovieDatabase.getDirector(movieID1), is("Spike Jonze"));
    assertThat(MovieDatabase.getMinutes(movieID1), is(126));

    assertThat(MovieDatabase.getTitle(movieID2), is("Heat"));
    assertThat(averageRatingsByFilter.get(1).getValue(), is(10.0));
    assertThat(MovieDatabase.getGenres(movieID2), is("Action, Crime, Drama"));
    assertThat(MovieDatabase.getDirector(movieID2), is("Michael Mann"));
    assertThat(MovieDatabase.getMinutes(movieID2), is(170));
  }

  private void printRatings(ThirdRatings thirdRatings, List<Rating> resultList) {
    System.out.println("Number of raters: " + thirdRatings.getRaterSize());
    System.out.println("Number of movies: " + MovieDatabase.size());
    System.out.println("Number of ratings returned: " + resultList.size());
    System.out.println();
    Collections.sort(resultList);
    for (Rating rating : resultList) {
      String movieID = rating.getItem();
      System.out.println(
        "Title: " + MovieDatabase.getTitle(movieID) + "\n" +
          "Average rating: " + rating.getValue() + "\n" +
          "Genre(s): " + MovieDatabase.getGenres(movieID) + "\n" +
          "Director(s): " + MovieDatabase.getDirector(movieID) + "\n" +
          "Duration: " + MovieDatabase.getMinutes(movieID) + "min"
      );
      System.out.println();
    }

  }
}