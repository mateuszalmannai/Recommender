package step4;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ratings.Rating;
import step3.*;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FourthRatingsTest {

  private static final String SHORT_MOVIES = "ratedmovies_short.csv";
  private static final String SHORT_RATINGS = "ratings_short.csv";

  private static final String FULL_RATINGS = "ratings.csv";
  private static final String FULL_MOVIES = "ratedmoviesfull.csv";


  @Before
  public void setUp() throws Exception {
    MovieDatabase.resetDatabase();
    RaterDatabase.resetDatabase();
  }

  @Test
  public void testSmallDatabaseSizes() throws Exception {
    MovieDatabase.initialize(SHORT_MOVIES);
    RaterDatabase.initialize(SHORT_RATINGS);

    assertThat(MovieDatabase.size(), is(5));
    assertThat(RaterDatabase.size(), is(5));
  }

  @Test
  public void testLargeDatabaseSizes() throws Exception {
    MovieDatabase.initialize(FULL_MOVIES);
    RaterDatabase.initialize(FULL_RATINGS);

    assertThat(MovieDatabase.size(), is(3143));
    assertThat(RaterDatabase.size(), is(1048));
  }

  @Test
  public void testGetAverageRatings() throws Exception {
    MovieDatabase.initialize(SHORT_MOVIES);
    RaterDatabase.initialize(SHORT_RATINGS);
    List<Rating> averageRatings = new FourthRatings().getAverageRatings(3);

    assertThat(averageRatings.size(), is(2));

    assertThat(averageRatings.get(0).getValue(), is(9.0));
    assertThat(averageRatings.get(0).getItem(), is("0068646"));

    assertThat(averageRatings.get(1).getValue(), is(8.25));
    assertThat(averageRatings.get(1).getItem(), is("1798709"));
  }

  @Test
  public void testPrintAverageRatings() throws Exception {
    MovieDatabase.initialize(SHORT_MOVIES);
    RaterDatabase.initialize(SHORT_RATINGS);

    System.out.println("read data for " + RaterDatabase.size());

  }

  @Test
  public void testPrintSimilarRatings() throws Exception {
    MovieDatabase.initialize(FULL_MOVIES);
    RaterDatabase.initialize(FULL_RATINGS);

    List<Rating> similarRatings = new FourthRatings().getSimilarRatings("65", 20, 5);
    Rating firstResult = similarRatings.get(0);
    assertThat(MovieDatabase.getTitle(firstResult.getItem()), is("The Fault in Our Stars"));
    assertThat(firstResult.getValue(), is(1186.6));
  }

  @Test
  public void testPrintSimilarRatingsQuiz() throws Exception {
    MovieDatabase.initialize(FULL_MOVIES);
    RaterDatabase.initialize(FULL_RATINGS);

    List<Rating> similarRatings = new FourthRatings().getSimilarRatings("337", 10, 3);
    Rating firstResult = similarRatings.get(0);
    assertThat(MovieDatabase.getTitle(firstResult.getItem()), is("Frozen"));
  }

  @Test
  public void testPrintSimilarRatingsQuiz2() throws Exception {
    MovieDatabase.initialize(FULL_MOVIES);
    RaterDatabase.initialize(FULL_RATINGS);

    List<Rating> similarRatings = new FourthRatings().getSimilarRatings("71", 20, 5);
    Rating firstResult = similarRatings.get(0);
    assertThat(MovieDatabase.getTitle(firstResult.getItem()), is("About Time"));
  }

  @Test
  public void testPrintSimilarRatingsByGenre() throws Exception {
    MovieDatabase.initialize(FULL_MOVIES);
    RaterDatabase.initialize(FULL_RATINGS);

    GenreFilter genreFilter = new GenreFilter("Action");

    List<Rating> similarRatingsByFilter = new FourthRatings().getSimilarRatingsByFilter("65", 20, 5, genreFilter);
    Rating topRating = similarRatingsByFilter.get(0);
    assertThat(MovieDatabase.getTitle(topRating.getItem()), is("Rush"));
    assertThat(topRating.getValue(), is(1094.8181818181818));
  }

  @Test
  public void testPrintSimilarRatingsByGenreQuiz() throws Exception {
    MovieDatabase.initialize(FULL_MOVIES);
    RaterDatabase.initialize(FULL_RATINGS);

    GenreFilter genreFilter = new GenreFilter("Mystery");

    List<Rating> similarRatingsByFilter = new FourthRatings().getSimilarRatingsByFilter("964", 20, 5, genreFilter);
    Rating topRating = similarRatingsByFilter.get(0);
    assertThat(MovieDatabase.getTitle(topRating.getItem()), is("Gone Girl"));
  }

  @Test
  public void testPrintSimilarRatingsByDirector() throws Exception {
    MovieDatabase.initialize(FULL_MOVIES);
    RaterDatabase.initialize(FULL_RATINGS);

    DirectorsFilter directorsFilter = new DirectorsFilter("Clint Eastwood,Sydney Pollack,David Cronenberg,Oliver Stone");

    List<Rating> similarRatingsByFilter = new FourthRatings().getSimilarRatingsByFilter("1034", 10, 3, directorsFilter);
    Rating topRating = similarRatingsByFilter.get(0);
    assertThat(MovieDatabase.getTitle(topRating.getItem()), is("Unforgiven"));
  }

  @Test
  public void testPrintSimilarRatingsByDirectorQuiz() throws Exception {
    MovieDatabase.initialize(FULL_MOVIES);
    RaterDatabase.initialize(FULL_RATINGS);

    DirectorsFilter directorsFilter = new DirectorsFilter("Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh");

    List<Rating> similarRatingsByFilter = new FourthRatings().getSimilarRatingsByFilter("120", 10, 2, directorsFilter);
    Rating topRating = similarRatingsByFilter.get(0);
    assertThat(MovieDatabase.getTitle(topRating.getItem()), is("Star Trek"));
  }


  @Test
  public void testPrintSimilarRatingsByGenreAndMinutes() throws Exception {
    MovieDatabase.initialize(FULL_MOVIES);
    RaterDatabase.initialize(FULL_RATINGS);

    GenreFilter adventureFilter = new GenreFilter("Adventure");
    MinutesFilter minutesFilter = new MinutesFilter(100, 200);

    AllFilters allFilters = new AllFilters();
    allFilters.addFilter(adventureFilter);
    allFilters.addFilter(minutesFilter);

    List<Rating> similarRatingsByFilter = new FourthRatings().getSimilarRatingsByFilter("65", 10, 5, allFilters);
    Rating topRating = similarRatingsByFilter.get(0);
    assertThat(MovieDatabase.getTitle(topRating.getItem()), is("Interstellar"));
  }


  @Test
  public void testPrintSimilarRatingsByGenreAndMinutesQuiz() throws Exception {
    MovieDatabase.initialize(FULL_MOVIES);
    RaterDatabase.initialize(FULL_RATINGS);

    GenreFilter adventureFilter = new GenreFilter("Drama");
    MinutesFilter minutesFilter = new MinutesFilter(80, 160);

    AllFilters allFilters = new AllFilters();
    allFilters.addFilter(adventureFilter);
    allFilters.addFilter(minutesFilter);

    List<Rating> similarRatingsByFilter = new FourthRatings().getSimilarRatingsByFilter("168", 10, 3, allFilters);
    Rating topRating = similarRatingsByFilter.get(0);
    assertThat(MovieDatabase.getTitle(topRating.getItem()), is("The Imitation Game"));
  }

  @Test
  public void testPrintSimilarRatingsByYearAfterAndMinutes() throws Exception {
    MovieDatabase.initialize(FULL_MOVIES);
    RaterDatabase.initialize(FULL_RATINGS);

    YearAfterFilter yearAfterFilter = new YearAfterFilter(2000);
    MinutesFilter minutesFilter = new MinutesFilter(80, 100);
    AllFilters allFilters = new AllFilters();
    allFilters.addFilter(yearAfterFilter);
    allFilters.addFilter(minutesFilter);

    List<Rating> similarRatingsByFilter = new FourthRatings().getSimilarRatingsByFilter("65", 10, 5, allFilters);
    Rating topRating = similarRatingsByFilter.get(0);
    assertThat(MovieDatabase.getTitle(topRating.getItem()), is("The Grand Budapest Hotel"));
  }

  @Test
  public void testPrintSimilarRatingsByYearAfterAndMinutesQuiz() throws Exception {
    MovieDatabase.initialize(FULL_MOVIES);
    RaterDatabase.initialize(FULL_RATINGS);

    YearAfterFilter yearAfterFilter = new YearAfterFilter(1975);
    MinutesFilter minutesFilter = new MinutesFilter(70, 200);
    AllFilters allFilters = new AllFilters();
    allFilters.addFilter(yearAfterFilter);
    allFilters.addFilter(minutesFilter);

    List<Rating> similarRatingsByFilter = new FourthRatings().getSimilarRatingsByFilter("314", 10, 5, allFilters);
    Rating topRating = similarRatingsByFilter.get(0);
    assertThat(MovieDatabase.getTitle(topRating.getItem()), is("Nightcrawler"));
  }
}