package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store {
    private static final Store INST = new Store();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    public Store() {
        posts.put(1, new Post(1, "Junior Java Job", "Need Junior", new Date()));
        posts.put(2, new Post(2, "Middle Java Job", "Need Middle", new Date()));
        posts.put(3, new Post(3, "Senior Java Job", "Need Senior", new Date()));
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}
