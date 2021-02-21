package bribeiro.asyncstuff.search.services.async;

import bribeiro.asyncstuff.cryptomodel.CryptoData;
import bribeiro.asyncstuff.search.httpclient.CompletableHttpClient;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class TopCryptoSearch {

    private final static Logger LOG = LoggerFactory.getLogger(TopCryptoSearch.class);
    public static final String API_KEY = "a1735663-cc5e-4fee-bf17-8e496bbccd01";
    public static final String URI = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";

    private final CompletableHttpClient httpClient;

    public TopCryptoSearch(CompletableHttpClient httpClient) {
        this.httpClient = httpClient;
    }


    public CompletableFuture<CryptoData> getOrderedCrypto() {
        HashMap<String, String> params = new HashMap<>();
        params.put("limit", "5000");

        return makeAPICall(URI, params);
    }

    private CompletableFuture<CryptoData> makeAPICall(String uri, Map<String, String> params) {

        HttpUrl.Builder httpBuilder = HttpUrl.parse(uri).newBuilder();
        if (params != null) {
            for(Map.Entry<String, String> param : params.entrySet()) {
                httpBuilder.addQueryParameter(param.getKey(),param.getValue());
            }
        }

        Request request = new Request.Builder()
                .addHeader("X-CMC_PRO_API_KEY", API_KEY)
                .url(httpBuilder.build()).build();

        return httpClient.get(request).thenApply(response -> {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                assert response.body() != null;
                String body = response.body().string();
                return objectMapper.readValue(body, CryptoData.class);
            } catch (IOException e) {
                throw new RuntimeException("FALLO");
            }
        });
    }


}
