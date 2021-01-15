package bribeiro.asyncstuff.search.async;

import java.util.List;

public interface AsyncSearch {

  String searchIntoFirstPages(String term, int numLinks);

  List<String> getSearch(String searchId);

}
