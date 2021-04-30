package ru.job4j.dreamjob.stores;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;
import java.util.Collection;

public interface Store {

    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    Collection<City> findAllCities();

    void savePost(Post post);

    void saveCandidate(Candidate candidate);

    Post findByIdPost(int id);

    Candidate findByIdCandidate(int id);

    void deleteCandidate(int id);

    void deletePost(int id);

    void saveUser(User user);

    User findUserByEmail(String email);

    City findCityById(int id);
}
