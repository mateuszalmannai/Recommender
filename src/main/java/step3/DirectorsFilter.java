package step3;

public class DirectorsFilter implements Filter{

  private final String[] directors;

  public DirectorsFilter(String directors) {
    this.directors = directors.split(",");
  }

  @Override
  public boolean satisfies(String id) {
    for (String director : directors) {
      if (MovieDatabase.getDirector(id).indexOf(director) >= 0) {
        return true;
      }
    }
    return false;
  }
}
