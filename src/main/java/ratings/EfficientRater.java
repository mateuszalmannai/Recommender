package ratings;

import java.util.*;

public class EfficientRater implements Rater {

  private String myID;
  private Map<String, Rating> myRatings;

  public EfficientRater(String id) {
    myID = id;
    myRatings = new HashMap<>();
  }

  @Override
  public void addRating(String item, double rating) {
    myRatings.put(item, new Rating(item, rating));

  }

  @Override
  public boolean hasRating(String item) {
    boolean result = false;
    if (myRatings.containsKey(item)) {
      result = true;
    }
    return result;
  }

  @Override
  public String getID() {
    return myID;
  }

  @Override
  public double getRating(String item) {
    double result = -1;
    if (hasRating(item)) {
      result = myRatings.get(item).getValue();
    }
    return result;
  }

  @Override
  public int numRatings() {
    return myRatings.size();
  }

  @Override
  public List<String> getItemsRated() {
    return new ArrayList<>(myRatings.keySet());
  }
}
