package bribeiro.asyncstuff.cryptomodel;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class CryptoList {

    @JsonProperty("data")
    List<CryptoData> data;

}
