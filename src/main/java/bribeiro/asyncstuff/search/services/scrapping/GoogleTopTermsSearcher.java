package bribeiro.asyncstuff.search.services.scrapping;

import bribeiro.asyncstuff.search.httpclient.CompletableHttpClient;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import okhttp3.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoogleTopTermsSearcher implements TopTermsSearcher {

  private final static Logger LOG = LoggerFactory.getLogger(GoogleTopTermsSearcher.class);

  private static final String GOOGLE_SEARCH_URL = "https://www.google.com/search?q=";

  @Autowired
  private CompletableHttpClient asyncClient;

  @Override
  public CompletableFuture<List<String>> getTopUrls(final String term, final int numLinks) {
    Request request = new Request.Builder()
        .url(GOOGLE_SEARCH_URL + term)
        .build();

    return asyncClient.get(request)
        .thenApply(response -> {
            try {
                LOG.info("Async request done for - {}", request.url());
                Document parsed = Jsoup.parse(response.body().string());
                LOG.info("Parsed - {}", request.url());
                return parsed;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        })
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
