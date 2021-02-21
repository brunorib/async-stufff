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

    public void execute() {
        String id = TimeUtils.getDateNormalFormat();
        if (!repository.existsById(id)) {
            searcher.getOrderedCrypto().thenAccept(ordered -> {
                ordered.date = id;
                repository.save(ordered);
            });
        }
    }

}
