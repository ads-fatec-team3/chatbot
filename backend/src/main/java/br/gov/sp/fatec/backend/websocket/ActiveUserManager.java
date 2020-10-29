package br.gov.sp.fatec.backend.websocket;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class ActiveUserManager {
  private final Map<String, Object> users;
  private final List<ActiveUserChangeListener> listeners;
  private final ThreadPoolExecutor notifyPool;

  private ActiveUserManager() {
    users = new ConcurrentHashMap<>();
    listeners = new CopyOnWriteArrayList<>();
    notifyPool = new ThreadPoolExecutor(1, 5, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
  }

  public void addUser(String username, String sessionId) {
    users.put(username, sessionId);
    notifyListeners();
  }

  /**
   * Removes username from the concurrentHashMap
   * @param username - to be removed
   */
  public void removeUser(String username) {
    users.remove(username);
    notifyListeners();
  }

  /**
   * Get a set of all active user except username that passed in the parameter
   * @param username - current username
   * @return - a Set of users except passed username
   */
  public Set<String> getActiveUsers() {
    Set<String> activeUsers = new HashSet<>(this.users.keySet());
    return activeUsers;
  }

  /**
   * To get notified when active users changed
   * @param listener - object that implements ActiveUserChangeListener
   */
  public void subscribeListener(ActiveUserChangeListener listener) {
    listeners.add(listener);
  }

  /**
   * Stop receiving notification.
   * @param listener - object that implements ActiveUserChangeListener
   */
  public void unsubscribeListener(ActiveUserChangeListener listener) {
    listeners.remove(listener);
  }

  private void notifyListeners() {
    notifyPool.submit(() -> listeners.forEach(
        ActiveUserChangeListener::notifyActiveUserChange
      )
    );
  }
}