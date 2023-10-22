package br.com.AzzyPog.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "db_tasks")
public class taskModel {
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String description;

    @Column(length = 50)
    private String title;

    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;
    
    //relacionamento de usuário com task
    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
