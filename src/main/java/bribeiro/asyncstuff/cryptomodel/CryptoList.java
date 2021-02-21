package bribeiro.asyncstuff.cryptomodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@JsonIgnoreProperties
@Document(collection = "cryptos")
public class CryptoList {

    @JsonProperty("data")
    public List<CryptoData> data;

    @JsonIgnore
    @MongoId
    public String date;

}
