package bribeiro.asyncstuff.search.storage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class MemorySearchRepository implements SearchRepository {

  private Map<String, List<String>> memory =
      new ConcurrentHashMap<>();

  private Map<String, Boolean> onGoing = new ConcurrentHashMap<>();

  @Override
  public void save(String searchId, List<String> results) {
    onGoing.remove(searchId);
    memory.put(searchId, results);
  }

  @Override
  public void reserve(String searchId) {
    onGoing.put(searchId, Boolean.TRUE);
  }

  @Override
  public List<String> getSearchById(String searchId) {
    return memory.get(searchId);
  }

  @Override
  public boolean isFinished(String searchId) {
    return memory.containsKey(searchId);
  }

  @Override
  public boolean isOngoing(String searchId) {
    return onGoing.containsKey(searchId);
  }


}
