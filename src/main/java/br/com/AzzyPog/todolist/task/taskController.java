package br.com.AzzyPog.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.catalina.connector.Response;
import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class taskController {
    
    @Autowired
    private ItaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody taskModel taskModel, HttpServletRequest request) {
        //pegando o iduser do request e setando na task
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID)idUser);

        var currentDate = LocalDateTime.now();

        if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(400)
            .body("A data inicial/final deve ser maior que a data atual.");
        }

        if(taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(400).body("A data de ínicio deve ser maior que a data de término.");
        }

        var created = this.taskRepository.save(taskModel);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/")
    public ResponseEntity userIndex(HttpServletRequest request) {

        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID)idUser);

        return ResponseEntity.status(200).body(tasks);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity update(@RequestBody taskModel taskModel, @PathVariable UUID taskId, HttpServletRequest request) {
        return ResponseEntity.status();
    }
}
