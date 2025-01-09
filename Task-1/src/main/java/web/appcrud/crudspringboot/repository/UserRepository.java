package web.appcrud.crudspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.appcrud.crudspringboot.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
