package ratings;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SecondRatingsTest {

  private static final String SHORT_MOVIES = "ratedmovies_short.csv";
  private static final String FULL_MOVIES = "ratedmoviesfull.csv";
  private static final String SHORT_RATINGS = "ratings_short.csv";
  private static final String FULL_RATINGS = "ratings.csv";

  private SecondRatings secondRatings;

  @Test
  public void testGetMovieSize() throws Exception {
    secondRatings = new SecondRatings(SHORT_MOVIES, SHORT_RATINGS);
    assertThat(secondRatings.getMovieSize(), is(5));
    secondRatings = new SecondRatings(FULL_MOVIES, FULL_RATINGS);
    assertThat(secondRatings.getMovieSize(), is(3143));
  }

  @Test
  public void testGetRaterSize() throws Exception {
    secondRatings = new SecondRatings(SHORT_MOVIES, SHORT_RATINGS);
    assertThat(secondRatings.getRaterSize(), is(5));
    secondRatings = new SecondRatings(FULL_MOVIES, FULL_RATINGS);
    assertThat(secondRatings.getRaterSize(), is(1048));
  }

  @Test
  public void testGetAverageRatings() throws Exception {
    secondRatings = new SecondRatings(SHORT_MOVIES, SHORT_RATINGS);
    List<Rating> averageRatings = secondRatings.getAverageRatings(3);
    assertThat(averageRatings.size(), is(2));
    assertThat(averageRatings.get(0).getValue(), is(9.0));
    assertThat(averageRatings.get(0).getItem(), is("0068646"));
    assertThat(averageRatings.get(1).getValue(), is(8.25));
    assertThat(averageRatings.get(1).getItem(), is("1798709"));
  }

  @Test
  public void testGetAverageRatingsQuiz() throws Exception {
    SecondRatings secondRatings = new SecondRatings(FULL_MOVIES, FULL_RATINGS);
    List<Rating> averageRatings = secondRatings.getAverageRatings(50);

    assertThat(averageRatings.size(), is(7));
  }

  @Test
  public void testPrintAverageRatings() throws Exception {
    secondRatings = new SecondRatings(SHORT_MOVIES, SHORT_RATINGS);
    List<Rating> averageRatings = secondRatings.getAverageRatings(3);
    Collections.sort(averageRatings);
    for (Rating rating : averageRatings) {
      System.out.println(rating.getValue() + " " + secondRatings.getTitle(rating.getItem()));
    }
  }

  @Test
  public void testPrintAverageRatingsQuiz() throws Exception {
    secondRatings = new SecondRatings(FULL_MOVIES, FULL_RATINGS);
    List<Rating> averageRatings = secondRatings.getAverageRatings(12);
    Collections.sort(averageRatings);
    for (Rating rating : averageRatings) {
      System.out.println(rating.getValue() + " " + secondRatings.getTitle(rating.getItem()));
    }
  }

  @Test
  public void testGetID() throws Exception {
    secondRatings = new SecondRatings(SHORT_MOVIES, SHORT_RATINGS);
    String id = secondRatings.getID("Behind the Screen");
    assertThat(id, is("0006414"));
    id = secondRatings.getID("Blah");
    assertThat(id, is("NO SUCH TITLE"));
  }

  @Test
  public void testGetAverageRatingOneMovie() throws Exception {
    secondRatings = new SecondRatings(SHORT_MOVIES, SHORT_RATINGS);

    String title = "The Godfather";
    String id = secondRatings.getID(title);
    List<Rating> averageRatings = secondRatings.getAverageRatings(2);
    double value = 0;
    for (Rating rating : averageRatings) {
      if (rating.getItem().equals(id)) {
        value = rating.getValue();
      }
    }
    System.out.println(title + ": " + value);
  }

  @Test
  public void testGetAverageRatingOneMovieQuiz() throws Exception {
    secondRatings = new SecondRatings(FULL_MOVIES, FULL_RATINGS);

    String title = "Vacation";
    String id = secondRatings.getID(title);
    List<Rating> averageRatings = secondRatings.getAverageRatings(2);
    double value = 0;
    for (Rating rating : averageRatings) {
      if (rating.getItem().equals(id)) {
        value = rating.getValue();
      }
    }
    System.out.println(title + ": " + value);
  }
}