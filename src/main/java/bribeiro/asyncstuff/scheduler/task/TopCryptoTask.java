package bribeiro.asyncstuff.scheduler.task;

import bribeiro.asyncstuff.search.services.async.TopCryptoSearch;
import bribeiro.asyncstuff.storage.mongo.TopCryptoRepository;
import bribeiro.asyncstuff.util.TimeUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TopCryptoTask {

    private TopCryptoSearch searcher;
    private TopCryptoRepository repository;

    public TopCryptoTask(TopCryptoSearch searcher, TopCryptoRepository repository) {
        this.searcher = searcher;
        this.repository = repository;
    }

    @Scheduled(fixedRate = 86400000)
    public void execute() {
        searcher.getOrderedCrypto().thenAccept(ordered -> {
            ordered.date = TimeUtils.getDateNormalFormat();
            repository.save(ordered);
        });
    }

}
