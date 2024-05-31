package application.repo;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import application.model.Movie;
import application.model.User;
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
	Optional<Movie> findByIdAndUser(Long id, User user);
	List<Movie> findAllByUser(User user);
}
