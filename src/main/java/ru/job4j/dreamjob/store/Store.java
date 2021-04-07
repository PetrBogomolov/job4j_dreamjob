package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Store {
    private static final Store INST = new Store();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private static AtomicInteger POST_ID = new AtomicInteger(4);

    public Store() {
        posts.put(1, new Post(1, "Junior Java Job", "Need Junior"));
        posts.put(2, new Post(2, "Middle Java Job", "Need Middle"));
        posts.put(3, new Post(3, "Senior Java Job", "Need Senior"));
        candidates.put(1, new Candidate(1, "Junior Java сandidate"));
        candidates.put(2, new Candidate(2, "Middle Java сandidate"));
        candidates.put(3, new Candidate(3, "Senior Java сandidate"));
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    public void save(Post post) {
        post.setId(POST_ID.incrementAndGet());
        posts.put(post.getId(), post);
    }
}
