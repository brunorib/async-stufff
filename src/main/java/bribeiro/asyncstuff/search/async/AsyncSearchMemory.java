package bribeiro.asyncstuff.search.async;

import bribeiro.asyncstuff.search.TitleScrapper;
import bribeiro.asyncstuff.search.TopTermsSearcher;
import bribeiro.asyncstuff.util.CompletableFutureUtil;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsyncSearchMemory implements AsyncSearch {

  private final static Logger LOG = LoggerFactory.getLogger(AsyncSearchMemory.class);

  private ConcurrentHashMap<String, List<String>> results =
      new ConcurrentHashMap<>();

  @Autowired
  private TopTermsSearcher topTermsSearcher;

  @Autowired
  private TitleScrapper titleScrapper;

  @Override
  public String searchIntoFirstPages(String term, int numLinks) {
    String uuid = UUID.randomUUID().toString();
    CompletableFuture<List<String>> urls = topTermsSearcher.getTopUrls(term, numLinks);
    urls.thenCompose(urlsList -> {
      LOG.info("Searching titles for urls...");
      List<CompletableFuture<String>> listOfCompletables =
          titleScrapper.getTitleFromUrls(urlsList);
      return CompletableFutureUtil.allOf(listOfCompletables);
    }).thenApply(titles -> {
      LOG.info("Saving titles...");
      return results.put(uuid, titles);
    });
    return uuid;
  }

  @Override
  public List<String> getSearch(String searchId) {
    return results.get(searchId);
  }
}
