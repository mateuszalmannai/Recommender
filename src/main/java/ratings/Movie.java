package ratings;

// An immutable passive data object (PDO) to represent item data
public class Movie {
  private String id;
  private String title;
  private int year;
  private String genres;
  private String director;
  private String country;
  private String poster;
  private int minutes;

  public Movie(String anID, String aTitle, String aYear, String theGenres) {
    // just in case data file contains extra whitespace
    id = anID.trim();
    title = aTitle.trim();
    year = Integer.parseInt(aYear.trim());
    genres = theGenres;
  }

  public Movie(String anID, String aTitle, String aYear, String theGenres, String aDirector,
               String aCountry, String aPoster, int theMinutes) {
    // just in case data file contains extra whitespace
    id = anID.trim();
    title = aTitle.trim();
    year = Integer.parseInt(aYear.trim());
    genres = theGenres;
    director = aDirector;
    country = aCountry;
    poster = aPoster;
    minutes = theMinutes;
  }

  public Movie(String id, String title, int year, String genres, String director, String country, String poster,
               int minutes) {
    this.id = id;
    this.title = title;
    this.year = year;
    this.genres = genres;
    this.director = director;
    this.country = country;
    this.poster = poster;
    this.minutes = minutes;
  }

  // Returns ID associated with this item
  public String getID() {
    return id;
  }

  // Returns title of this item
  public String getTitle() {
    return title;
  }

  // Returns year in which this item was published
  public int getYear() {
    return year;
  }

  // Returns genres associated with this item
  public String getGenres() {
    return genres;
  }

  public String getCountry() {
    return country;
  }

  public String getDirector() {
    return director;
  }

  public String getPoster() {
    return poster;
  }

  public int getMinutes() {
    return minutes;
  }

  @Override
  public String toString() {
    return "Movie{" +
      "id='" + id + '\'' +
      ", title='" + title + '\'' +
      ", year=" + year +
      ", genres='" + genres + '\'' +
      ", director='" + director + '\'' +
      ", country='" + country + '\'' +
      ", poster='" + poster + '\'' +
      ", minutes=" + minutes +
      '}';
  }
}
