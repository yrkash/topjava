package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UserComparator;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        ValidationUtil.users.forEach(this::save);
    }

    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(),user);
            log.info("save {}", user);
            return user;
        }
        log.info("update {}", user);
        return repository.computeIfPresent(user.getId(),(id, oldUser)->user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return repository.values().stream()
                .sorted(new UserComparator())
                .toList();
    }

    @Override
    public User getByEmail(String email) {
        return  repository.values().stream()
                .filter(user-> user.getEmail().equals(email))
                .findAny().orElse(null);
        /*
        for(Map.Entry<Integer, User> entry: repository.entrySet()) {
            String currentEmail = entry.getValue().getEmail();
            if (currentEmail.equals(email)) {
                log.info("getByEmail {}", email);
                return entry.getValue();
            }
        }
        return null;*/
    }
}
