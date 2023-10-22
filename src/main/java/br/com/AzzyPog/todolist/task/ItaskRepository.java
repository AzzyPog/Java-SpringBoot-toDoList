package br.com.AzzyPog.todolist.task;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItaskRepository extends JpaRepository<taskModel, UUID>{


    
}