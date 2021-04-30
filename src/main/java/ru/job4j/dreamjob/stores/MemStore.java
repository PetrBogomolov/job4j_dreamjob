package ru.job4j.dreamjob.stores;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore implements Store {
    private static final MemStore INST = new MemStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final Map<String, User> users = new ConcurrentHashMap<>();
    private static final AtomicInteger POST_ID = new AtomicInteger();
    private static final AtomicInteger CANDIDATE_ID = new AtomicInteger();

    public MemStore() {
    }

    public static MemStore instOf() {
        return INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    @Override
    public Collection<City> findAllCities() {
        return null;
    }

    @Override
    public void savePost(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    @Override
    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    @Override
    public Post findByIdPost(int id) {
        return posts.get(id);
    }

    @Override
    public Candidate findByIdCandidate(int id) {
        return candidates.get(id);
    }

    @Override
    public void deleteCandidate(int id) {
       candidates.remove(id);
    }

    @Override
    public void deletePost(int id) {

    }

    @Override
    public void saveUser(User user) {
        users.putIfAbsent(user.getName(), user);
    }

    @Override
    public User findUserByEmail(String email) {
        User result = null;
        List<User> list = new ArrayList<>(users.values());
        for (User user : list) {
            if (user.getEmail().equals(email)) {
                result = user;
                break;
            }
        }
        return result;
    }

    @Override
    public City findCityById(int id) {
        return null;
    }
}
