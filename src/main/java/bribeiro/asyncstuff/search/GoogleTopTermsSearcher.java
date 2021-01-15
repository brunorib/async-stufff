package bribeiro.asyncstuff.search;

import bribeiro.asyncstuff.search.async.AsyncSearchMemory;
import bribeiro.asyncstuff.search.httpclient.CompletableHttpClient;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import okhttp3.Request;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoogleTopTermsSearcher implements TopTermsSearcher {

  private final static Logger LOG = LoggerFactory.getLogger(AsyncSearchMemory.class);

  private static final String GOOGLE_SEARCH_URL = "https://www.google.com/search?q=";

  @Autowired
  private CompletableHttpClient asyncClient;

  @Override
  public CompletableFuture<List<String>> getTopUrls(final String term, final int numLinks) {
    Request request = new Request.Builder()
        .url(GOOGLE_SEARCH_URL + term)
        .build();

    return asyncClient.get(request)
        .thenApply(document -> {
          LOG.debug("Getting links from parsed google page...");
          Element mainDiv = document.body().getElementById("main");
          List<String> links = mainDiv.getElementsByClass("kCrYT").stream()
              .map(element -> element.getElementsByTag("a"))
              .map(element -> element.attr("href"))
              .map(url -> url.replace("/url?q=", ""))
              .map(url -> url.substring(0, url.indexOf("&sa=U")))
              .filter(url -> !url.isEmpty())
              .limit(numLinks)
              .collect(Collectors.toList());
          LOG.debug("Finished getting links");
          return links;
        });
  }
}
