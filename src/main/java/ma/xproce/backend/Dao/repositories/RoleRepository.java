package ma.xproce.backend.Dao.repositories;

import ma.xproce.backend.Dao.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {


    Optional<Role> findByName(String name);
}
