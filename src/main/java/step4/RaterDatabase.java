package step4;


/**
 * An efficient way to get information about raters.
 * ourRaters maps a rater ID String to a Rater object that
 * includes all the movie ratings made by this rater
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ratings.EfficientRater;
import ratings.Rater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaterDatabase {
  private static Map<String,Rater> ourRaters;

  /**
   * Initialize the Map if ourRaters does not exist
   */
  private static void initialize() {
    // this method is only called from addRatings
    if (ourRaters == null) {
      ourRaters = new HashMap<>();
    }
  }

  /**
   * Call this method with the name of the file used to initialize the
   * rater database
   * @param filename
   */
  public static void initialize(String filename) {
    if (ourRaters == null) {
      ourRaters= new HashMap<>();
      addRatings("data/" + filename);
    }
  }

  /**
   * Alternatively call this method to add rater ratings to the
   * database from a file
   * @param filename
   */
  public static void addRatings(String filename) {
    initialize();
    FileResource fr = new FileResource(filename);
    CSVParser csvp = fr.getCSVParser();
    for(CSVRecord rec : csvp) {
      String id = rec.get("rater_id");
      String item = rec.get("movie_id");
      String rating = rec.get("rating");
      addRaterRating(id,item,Double.parseDouble(rating));
    }
  }

  /**
   * This method can be used to add one rater and their movie rating to the database
   * Note that the addRatings method calls this method
   * @param raterID
   * @param movieID
   * @param rating
   */
  public static void addRaterRating(String raterID, String movieID, double rating) {
    initialize();
    Rater rater;
    if (ourRaters.containsKey(raterID)) {
      rater = ourRaters.get(raterID);
    }
    else {
      rater = new EfficientRater(raterID);
      ourRaters.put(raterID,rater);
    }
    rater.addRating(movieID,rating);
  }

  public static Rater getRater(String id) {
    initialize();
    return ourRaters.get(id);
  }

  public static List<Rater> getRaters() {
    initialize();
    List<Rater> list = new ArrayList<>(ourRaters.values());

    return list;
  }

  public static void resetDatabase() {
    ourRaters = null;
  }

  public static int size() {
    return ourRaters.size();
  }

}
