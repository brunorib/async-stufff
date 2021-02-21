package bribeiro.asyncstuff.cryptomoves.service;

import bribeiro.asyncstuff.cryptomodel.CryptoData;
import bribeiro.asyncstuff.cryptomodel.CryptoList;
import bribeiro.asyncstuff.cryptomoves.result.CryptoMoves;
import bribeiro.asyncstuff.storage.mongo.TopCryptoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CryptoMovesService {

    private TopCryptoRepository repository;

    public CryptoMovesService(TopCryptoRepository repository) {
        this.repository = repository;
    }

    public List<CryptoMoves> getMovesList(String dateFrom, String dateTo) {
        CryptoList from = repository.findByDate(dateFrom);
        CryptoList to = repository.findByDate(dateTo);

        ArrayList<CryptoMoves> list = new ArrayList<>(from.data.size());

        from.data.forEach(fromData -> {
            Optional<CryptoData> toData = to.data.stream().filter(other -> other.id == fromData.id).findFirst();
            if (toData.isPresent()) {
                list.add(new CryptoMoves(fromData.name, fromData.symbol, fromData.rank, toData.get().rank));
            } else {
                list.add(new CryptoMoves(fromData.name, fromData.symbol, fromData.rank));
            }
        });

        list.sort(CryptoMoves::compareTo);
        return list;
    }

}
