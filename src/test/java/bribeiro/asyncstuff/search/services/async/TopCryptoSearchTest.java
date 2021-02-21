package bribeiro.asyncstuff.search.services.async;

import bribeiro.asyncstuff.cryptomodel.CryptoData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class TopCryptoSearchTest {

    @Autowired
    private TopCryptoSearch sut;

    @Test
    public void test() throws ExecutionException, InterruptedException {
        CompletableFuture<CryptoData> future = sut.getOrderedCrypto();
        future.get();
    }

}