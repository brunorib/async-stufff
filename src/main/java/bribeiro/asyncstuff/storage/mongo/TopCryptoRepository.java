package bribeiro.asyncstuff.storage.mongo;

import bribeiro.asyncstuff.cryptomodel.CryptoList;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface TopCryptoRepository extends MongoRepository<CryptoList, String> {

    CryptoList findByDate(String date);

}
