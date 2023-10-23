package br.com.AzzyPog.todolist.task;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ItaskRepository extends JpaRepository<taskModel, UUID>{
    List<taskModel> findByIdUser(UUID idUser);
}