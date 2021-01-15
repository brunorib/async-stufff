package bribeiro.asyncstuff.search.payload;

import java.util.List;

public class SearchResult {

  private boolean found = false;

  private List<String> pagesTitle;

  public SearchResult() {}

  public SearchResult(List<String> pagesTitle) {
    this.pagesTitle = pagesTitle;
    this.found = true;
  }

  public List<String> getPagesTitle() {
    return pagesTitle;
  }

  public boolean isFound() {
    return found;
  }
}
