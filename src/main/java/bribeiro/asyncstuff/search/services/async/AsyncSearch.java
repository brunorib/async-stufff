package bribeiro.asyncstuff.search.services.async;

import bribeiro.asyncstuff.search.services.scrapping.TitleScrapper;
import bribeiro.asyncstuff.search.services.scrapping.TopTermsSearcher;
import bribeiro.asyncstuff.search.storage.SearchRepository;
import bribeiro.asyncstuff.util.CompletableFutureUtil;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsyncSearch {

  private final static Logger LOG = LoggerFactory.getLogger(AsyncSearch.class);

  @Autowired
  private SearchRepository searchRepository;

  @Autowired
  private TopTermsSearcher topTermsSearcher;

  @Autowired
  private TitleScrapper titleScrapper;

  public String searchIntoFirstPages(String term, int numLinks) {
    String uuid = UUID.randomUUID().toString();
    searchRepository.reserve(uuid);
    CompletableFuture<List<String>> urls = topTermsSearcher.getTopUrls(term, numLinks);
    urls.thenCompose(urlsList -> {
      LOG.info("Searching titles for urls...");
      List<CompletableFuture<String>> listOfCompletables =
          titleScrapper.getTitleFromUrls(urlsList);
      return CompletableFutureUtil.allOf(listOfCompletables);
    }).thenAccept(titles -> {
      LOG.info("Saving titles...");
      searchRepository.save(uuid, titles);
    });
    return uuid;
  }

  public List<String> getSearch(String searchId) {
    return searchRepository.getSearchById(searchId);
  }

  public boolean isSearchOngoing(String searchId) {
    return searchRepository.isOngoing(searchId);
  }
}
