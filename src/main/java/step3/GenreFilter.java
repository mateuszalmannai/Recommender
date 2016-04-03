package step3;

public class GenreFilter implements Filter{

  private final String genre;

  public GenreFilter(String genre) {
    this.genre = genre;
  }

  @Override
  public boolean satisfies(String id) {
    return MovieDatabase.getGenres(id).contains(genre);
  }
}
