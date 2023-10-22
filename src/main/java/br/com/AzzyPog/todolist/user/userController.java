package br.com.AzzyPog.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

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
}
