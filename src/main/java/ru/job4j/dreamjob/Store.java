package ru.job4j.dreamjob;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;
import java.util.Collection;

public interface Store {

    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void savePost(Post post);

    void saveCandidate(Candidate candidate);

    Post findByIdPost(int id);

    Candidate findByIdCandidate(int id);

    void deleteCandidate(int id);

    void saveUser(User user);

    User findUserByEmail(String email);

    Collection<User> findAllUsers();

    void deleteUser(String email);
}
