package intern.freedesk.tokenservice.repository;

import intern.freedesk.tokenservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    User findByEmail (String email);

}
