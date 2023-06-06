package an.example.postcodes.repository;

import an.example.postcodes.entity.PostCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostCodeRepository extends JpaRepository<PostCodeEntity, Long> {
    public Optional<PostCodeEntity> findByPostcode(String postcode);
}
