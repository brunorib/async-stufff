package bribeiro.asyncstuff.cryptomodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties
public class CryptoData {

    @JsonProperty("symbol")
    public String symbol;

    @JsonProperty("cmc_rank")
    public int rank;

}
