package br.com.AzzyPog.todolist.user;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.http.HttpServletRequest;

/*
MODIFICADORES:
* public
* private
* protected
 */ 

@RestController
@RequestMapping("/users")
public class userController {
    
    @Autowired
    private IuserRepository userRepository;
/*
 * TIPOS:
 * String
 * Integer
 * Double
 * Float
 * Char
 * Date
 * void
 */
    @PostMapping("/")
    public ResponseEntity create(@RequestBody userModel userModel) {
        var User = this.userRepository.findByUsername(userModel.getUsername());

        if(User != null) {
            System.out.println("Nome de usuário já existe.");
            return ResponseEntity.status(400).body("Usuário já Existe.");
        }

        var hashPassword = BCrypt.withDefaults()
        .hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(hashPassword);

        var create = this.userRepository.save(userModel);
        return ResponseEntity.status(201).body(create);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity destroy(@PathVariable UUID userId, HttpServletRequest request) {
        var User = this.userRepository.findById(userId).orElse(null);
     
        if(User == null) {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }

        if(!userId.equals(request.getAttribute("idUser"))) {
            return ResponseEntity.status(400).body("Usuário não tem permissão de deletar essa conta.");
        }

        this.userRepository.delete(User);

        return ResponseEntity.status(200).body("Usuário deletado com sucesso.");
    }
}
