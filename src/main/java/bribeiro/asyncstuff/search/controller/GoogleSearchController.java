package bribeiro.asyncstuff.search.controller;

import bribeiro.asyncstuff.search.async.AsyncSearch;
import bribeiro.asyncstuff.search.payload.Callback;
import bribeiro.asyncstuff.search.payload.Search;
import bribeiro.asyncstuff.search.payload.SearchResult;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoogleSearchController {

  private static final SearchResult NOT_FOUND = new SearchResult();

  @Autowired
  private AsyncSearch asyncSearch;

  @GetMapping("/search/{id}")
  public SearchResult getSearchTerms(@PathVariable("id") String searchId) {
    List<String> search = asyncSearch.getSearch(searchId);

    if (search == null) {
      return NOT_FOUND;
    }

    return new SearchResult(search);
  }

  @PostMapping("/search")
  public Callback searchIntoGoogle(@RequestBody Search payload) {
    return new Callback(
        asyncSearch.searchIntoFirstPages(payload.getTerm(), payload.getNumberPages()));
  }

}
