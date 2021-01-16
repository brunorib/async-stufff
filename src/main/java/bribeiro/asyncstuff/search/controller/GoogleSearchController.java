package bribeiro.asyncstuff.search.controller;

import bribeiro.asyncstuff.search.services.async.AsyncSearch;
import bribeiro.asyncstuff.search.controller.payload.Callback;
import bribeiro.asyncstuff.search.controller.payload.SearchRequest;
import bribeiro.asyncstuff.search.controller.payload.SearchResult;
import bribeiro.asyncstuff.search.controller.payload.Status;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoogleSearchController {

  private static final SearchResult NOT_FOUND = new SearchResult(Status.NOT_FOUND);

  private static final SearchResult ON_GOING = new SearchResult(Status.ON_GOING);

  @Autowired
  private AsyncSearch asyncSearch;

  @GetMapping("/search/{id}")
  public SearchResult getSearchTerms(@PathVariable("id") String searchId) {
    List<String> search = asyncSearch.getSearch(searchId);

    if (search == null) {
      if (asyncSearch.isSearchOngoing(searchId)) {
        return ON_GOING;
      }
      return NOT_FOUND;
    }

    return new SearchResult(search);
  }

  @PostMapping("/search")
  public Callback searchIntoGoogle(@RequestBody SearchRequest payload) {
    return new Callback(
        asyncSearch.searchIntoFirstPages(payload.getTerm(), payload.getNumberPages()));
  }

}
