package ru.job4j.dreamjob;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;
import java.util.Collection;

public interface Store {

    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void savePost(Post post);

    void saveCandidate(Candidate candidate);

    Post findById(int id);
}
