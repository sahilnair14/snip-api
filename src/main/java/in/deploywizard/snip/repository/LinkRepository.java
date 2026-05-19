package in.deploywizard.snip.repository;

import in.deploywizard.snip.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LinkRepository extends JpaRepository<Link, UUID> {
    boolean existsByCode(String code);
    Optional<Link> findByCode(String code);
}
