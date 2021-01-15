package bribeiro.asyncstuff.search;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface TopTermsSearcher {

  CompletableFuture<List<String>> getTopUrls(String term, int numLinks);

}
