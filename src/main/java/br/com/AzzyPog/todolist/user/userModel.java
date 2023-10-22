package br.com.AzzyPog.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

//usando biblioteca para get e set
@Data
@Entity(name = "db_users")
public class userModel {
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String username;
    
    private String name;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;


    //getters e setters - buscar e setar infos privadas
    /*  public void setUsername(String username) {
         this.username = username;
     }
     public String getUsername() {
         return username;
     }

     public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }
    
     public void setPassword(String password) {
         this.password = password;
     }
     public String getPassword() {
         return password;
     } */
}
