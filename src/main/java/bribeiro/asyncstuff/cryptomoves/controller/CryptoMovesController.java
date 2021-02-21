package bribeiro.asyncstuff.cryptomoves.controller;

import bribeiro.asyncstuff.cryptomoves.result.CryptoMoves;
import bribeiro.asyncstuff.cryptomoves.service.CryptoMovesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class CryptoMovesController {

    private CryptoMovesService service;

    public CryptoMovesController(CryptoMovesService service) {
        this.service = service;
    }

    @GetMapping("/cryptomoves")
    public List<CryptoMoves> getSearchTerms(@RequestParam("from") String fromDate,
                                            @RequestParam("to") String toDate) {
        return service.getMovesList(fromDate, toDate);
    }

}
