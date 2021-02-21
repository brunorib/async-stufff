package bribeiro.asyncstuff.search.services.async;

import bribeiro.asyncstuff.cryptomodel.CryptoList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class TopCryptoSearchTest {

    @Autowired
    private TopCryptoSearch sut;

    @Test
    public void test() throws ExecutionException, InterruptedException {
        CompletableFuture<CryptoList> future = sut.getOrderedCrypto();
        CryptoList list = future.get();
        Assert.notNull(list, "Should exist obect");
    }

}