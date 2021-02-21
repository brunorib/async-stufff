package bribeiro.asyncstuff.search.services.scrapping;

import bribeiro.asyncstuff.search.httpclient.CompletableHttpClient;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import okhttp3.Request;
import okhttp3.Request.Builder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TitleScrapper {

  @Autowired
  private CompletableHttpClient asyncClient;

  public List<CompletableFuture<String>> getTitleFromUrls(List<String> urls) {
    return urls.stream().parallel()
        .map(url -> {
          Request request = new Builder()
              .url(url)
              .build();
          return asyncClient.get(request).thenApply(response -> {
              try {
                  Document parsed = Jsoup.parse(response.body().string());
                  return parsed;
              } catch (IOException e) {
                  throw new RuntimeException(e);
              }
          });
        }).map(
            futureDocument -> futureDocument.thenApply(Document::title)
        ).collect(Collectors.toList());
  }

}
