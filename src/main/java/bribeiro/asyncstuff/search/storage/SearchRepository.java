package bribeiro.asyncstuff.search.storage;

import java.util.List;

public interface SearchRepository {

  void save(String searchId, List<String> results);

  void reserve(String searchId);

  List<String> getSearchById(String searchId);

  boolean isFinished(String searchId);

  boolean isOngoing(String searchId);

}
