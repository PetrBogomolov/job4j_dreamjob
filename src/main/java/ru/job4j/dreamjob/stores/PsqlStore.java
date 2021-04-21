package ru.job4j.dreamjob.stores;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.dreamjob.Store;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store {

    private static final Logger LOG = LogManager.getLogger(PsqlStore.class.getName());
    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        ClassLoader loader = PsqlStore.class.getClassLoader();
        Properties config = new Properties();
        try (InputStream io = loader.getResourceAsStream("db.properties")) {
            config.load(io);
            Class.forName(config.getProperty("driver"));
        } catch (IOException | ClassNotFoundException e) {
            LOG.error(e.toString(), e);
        }
        pool.setDriverClassName(config.getProperty("driver"));
        pool.setUrl(config.getProperty("url"));
        pool.setUsername(config.getProperty("username"));
        pool.setPassword(config.getProperty("password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> allPosts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement statement = cn.prepareStatement("SELECT * FROM posts")) {
            try (ResultSet it = statement.executeQuery()) {
                while (it.next()) {
                   Post post = new Post(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"));
                   post.setCreated(it.getTimestamp("created"));
                   allPosts.add(post);
                }
            }
        } catch (SQLException se) {
            LOG.error(se.toString(), se);
        }
        return allPosts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> allCandidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement statement = cn.prepareStatement("SELECT * FROM candidates")) {
            try (ResultSet it = statement.executeQuery()) {
                while (it.next()) {
                    allCandidates.add(new Candidate(
                            it.getInt("id"),
                            it.getString("name")
                    ));
                }
            }
        } catch (SQLException se) {
            LOG.error(se.toString(), se);
        }
        return allCandidates;
    }

    @Override
    public void savePost(Post post) {
        if (post.getId() == 0) {
            createPost(post);
        } else {
            updatePost(post);
        }
    }

    private void createPost(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement statement = cn.prepareStatement(
                     "INSERT INTO posts (name, description, created) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )) {
            statement.setString(1, post.getName());
            statement.setString(2, post.getDescription());
            statement.setTimestamp(3, new Timestamp(post.getCreated().getTime()));
            statement.execute();
            try (ResultSet id = statement.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (SQLException se) {
            LOG.error(se.toString(), se);
        }
    }

    private void updatePost(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement statement = cn.prepareStatement(
                     "UPDATE posts SET name = (?), description = (?) WHERE id = (?)"
             )) {
            statement.setString(1, post.getName());
            statement.setString(2, post.getDescription());
            statement.setInt(3, post.getId());
            statement.executeUpdate();
        } catch (SQLException se) {
            LOG.error(se.toString(), se);
        }
    }

    @Override
    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            createCandidate(candidate);
        } else {
            updateCandidate(candidate);
        }
    }

    private void createCandidate(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement statement = cn.prepareStatement(
                     "INSERT INTO candidates (name) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )) {
            statement.setString(1, candidate.getName());
            statement.execute();
            try (ResultSet id = statement.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (SQLException se) {
            LOG.error(se.toString(), se);
        }
    }

    private void updateCandidate(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement statement = cn.prepareStatement(
                     "UPDATE candidates SET name = (?) WHERE id = (?)"
             )) {
            statement.setString(1, candidate.getName());
            statement.setInt(2, candidate.getId());
            statement.executeUpdate();
        } catch (SQLException se) {
            LOG.error(se.toString(), se);
        }
    }

    @Override
    public Post findByIdPost(int id) {
        Post post = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement statement = cn.prepareStatement(
                     "SELECT * FROM posts WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    post = new Post(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description")
                    );
                    post.setCreated(resultSet.getDate("created"));
                }
            }
        } catch (SQLException se) {
            LOG.error(se.toString(), se);
        }
        return post;
    }

    @Override
    public Candidate findByIdCandidate(int id) {
        Candidate candidate = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement statement = cn.prepareStatement(
                     "SELECT * FROM candidates WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    candidate = new Candidate(
                            resultSet.getInt("id"),
                            resultSet.getString("name")
                    );
                }
            }
        } catch (SQLException se) {
            LOG.error(se.toString(), se);
        }
        return candidate;
    }

    @Override
    public void deleteCandidate(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement statement = cn.prepareStatement(
                     "DELETE FROM candidates WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException se) {
            LOG.error(se.toString(), se);
        }
    }

    @Override
    public void saveUser(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement statement = cn.prepareStatement(
                     "INSERT INTO users (name, email, password) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.execute();
            try (ResultSet id = statement.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (SQLException se) {
            LOG.error(se.toString(), se);
        }
    }

    @Override
    public User findUserByEmail(String email) {
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement statement = cn.prepareStatement(
                     "SELECT * FROM users WHERE email = ?")) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password")
                    );
                }
            }
        } catch (SQLException se) {
            LOG.error(se.toString(), se);
        }
        return user;
    }
}
