package bribeiro.asyncstuff.cryptomoves.result;

public class CryptoMoves implements Comparable<CryptoMoves> {

    public String symbol;
    public String name;
    public int rank;
    public int previousRank;
    public int move;

    public CryptoMoves(String name, String symbol, int previousRank, int rank) {
        this.name = name;
        this.symbol = symbol;
        this.rank = rank;
        this.previousRank = previousRank;
        this.move = previousRank - rank;
    }

    public CryptoMoves(String name, String symbol, int previousRank) {
        this.name = name;
        this.symbol = symbol;
        this.previousRank = previousRank;
        this.move = 0;
    }

    @Override
    public int compareTo(CryptoMoves o) {
        return ((CryptoMoves) o).move - this.move;
    }
}
