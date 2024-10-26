package br.com.codeup.social.domain.repository;

import br.com.codeup.social.domain.model.Follower;
import br.com.codeup.social.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {

    public boolean follows(User follower, User user) {

        Map<String, Object> params = new HashMap<>();
        params.put("follower", follower);
        params.put("user", user);

        PanacheQuery<Follower> query = this.find("follower = :follower and user = :user", params);

        Optional<Follower> result = query.firstResultOptional();

        return result.isPresent();
    }

    public List<Follower> findByUser(Long userId) {
        return this.find("user.id", userId).list();
    }

    public void deleteByFollowerAndUser(Long userId, Long followerId) {

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("followerId", followerId);

        this.delete("follower.id = :followerId and user.id = :userId", params);
    }

}
