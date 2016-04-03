package ratings;

import java.util.List;

public interface Rater {

  void addRating(String item, double rating);

  boolean hasRating(String item);

  String getID();

  double getRating(String item);

  int numRatings();

  List<String> getItemsRated();
}
