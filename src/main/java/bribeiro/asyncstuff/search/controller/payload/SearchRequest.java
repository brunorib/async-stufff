package bribeiro.asyncstuff.search.controller.payload;

public class SearchRequest {

  private String term;

  private int numberPages;

  public String getTerm() {
    return term;
  }

  public int getNumberPages() {
    return numberPages;
  }
}
