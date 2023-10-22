package br.com.AzzyPog.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IuserRepository extends JpaRepository<userModel, UUID>{
    userModel findByUsername(String username);
}
