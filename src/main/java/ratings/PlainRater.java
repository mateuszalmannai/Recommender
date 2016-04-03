package ratings;
/**
 * Write a description of class Rater here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.ArrayList;
import java.util.List;

public class PlainRater implements Rater {

  private String myID;
  private List<Rating> myRatings;

  public PlainRater(String id) {
    myID = id;
    myRatings = new ArrayList<>();
  }

  @Override
  public void addRating(String item, double rating) {
    myRatings.add(new Rating(item, rating));
  }

  @Override
  public boolean hasRating(String item) {
    for (int k = 0; k < myRatings.size(); k++) {
      if (myRatings.get(k).getItem().equals(item)) {
        return true;
      }
    }

    return false;
  }

  @Override
  public String getID() {
    return myID;
  }

  @Override
  public double getRating(String item) {
    for (int k = 0; k < myRatings.size(); k++) {
      if (myRatings.get(k).getItem().equals(item)) {
        return myRatings.get(k).getValue();
      }
    }

    return -1;
  }

  @Override
  public int numRatings() {
    return myRatings.size();
  }

  @Override
  public List<String> getItemsRated() {
    ArrayList<String> list = new ArrayList<>();
    for (int k = 0; k < myRatings.size(); k++) {
      list.add(myRatings.get(k).getItem());
    }

    return list;
  }
}
