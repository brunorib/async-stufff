package bribeiro.asyncstuff.search.controller.payload;

import java.util.List;

public class SearchResult {

  private Status status;

  private List<String> pagesTitle;

  public SearchResult(Status status) {
    this.status = status;
  }

  public SearchResult(List<String> pagesTitle) {
    this(Status.FOUND);
    this.pagesTitle = pagesTitle;
  }

  public List<String> getPagesTitle() {
    return pagesTitle;
  }

  public Status getStatus() {
    return status;
  }
}
