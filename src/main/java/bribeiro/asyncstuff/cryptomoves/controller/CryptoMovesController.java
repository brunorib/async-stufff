package bribeiro.asyncstuff.cryptomoves.controller;

import bribeiro.asyncstuff.cryptomoves.result.CryptoMoves;
import bribeiro.asyncstuff.cryptomoves.service.CryptoMovesService;
import bribeiro.asyncstuff.scheduler.task.TopCryptoTask;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
public class CryptoMovesController {

    private CryptoMovesService service;

    private TopCryptoTask task;

    public CryptoMovesController(CryptoMovesService service, TopCryptoTask task) {
        this.task = task;
        this.service = service;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/cryptomoves")
    public List<CryptoMoves> getSearchTerms(@RequestParam("from") String fromDate,
                                            @RequestParam("to") String toDate) {
        try {
            return service.getMovesList(fromDate, toDate);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Data not found between given dates", ex);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/cryptomoves/getdaily")
    public String getDaily() {
        task.execute();
        return "OK";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/cryptomoves/earliest")
    public Earliest earliest() {
        return new Earliest(service.getEarliestDate());
    }

}
