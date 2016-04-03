package step3;

import java.util.ArrayList;
import java.util.List;

public class AllFilters implements Filter {

  List<Filter> filters;

  public AllFilters() {
    filters = new ArrayList<>();
  }

  public void addFilter(Filter f) {
    filters.add(f);
  }

  @Override
  public boolean satisfies(String id) {
    for (Filter f : filters) {
      if (!f.satisfies(id)) {
        return false;
      }
    }
    return true;
  }

}
