package bribeiro.asyncstuff.search.httpclient;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CompletableHttpClient {

  private final static Logger LOG = LoggerFactory.getLogger(CompletableHttpClient.class);

  private final OkHttpClient client = new OkHttpClient();

  private final ForkJoinPool taskPool = new ForkJoinPool();

  public CompletableFuture<Document> get(final Request request) {
    LOG.info("Doing http async request... {}", request.url());
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return client.newCall(request).execute();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        },
        taskPool
    ).thenApply(response -> {
      try {
        LOG.info("Async request done for - {}", request.url());
        Document parsed = Jsoup.parse(response.body().string());
        LOG.info("Parsed - {}", request.url());
        return parsed;
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

}
