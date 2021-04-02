package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store {
    private static final Store INST = new Store();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    public Store() {
        posts.put(1, new Post(1, "Junior Java Job", "Need Junior", new Date()));
        posts.put(2, new Post(2, "Middle Java Job", "Need Middle", new Date()));
        posts.put(3, new Post(3, "Senior Java Job", "Need Senior", new Date()));
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
}
