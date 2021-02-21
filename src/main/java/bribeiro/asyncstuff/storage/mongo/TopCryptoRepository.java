package bribeiro.asyncstuff.storage.mongo;

import bribeiro.asyncstuff.cryptomodel.CryptoData;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface TopCryptoRepository extends MongoRepository<CryptoData, String> {

    CryptoData findByDate(String date);

}
