package br.com.AzzyPog.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.AzzyPog.todolist.user.IuserRepository;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//toda requisição passa por esse filtro
@Component
public class filterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IuserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                var route = request.getServletPath();
                var method = request.getMethod();

                if(route.startsWith("/tasks/") || (route.startsWith("/users/") && method.equals("DELETE"))) {
                    //pegando a autenticação do usuário
                    var Auth = request.getHeader("Authorization");
                    Auth = Auth.substring(5).trim();
        
                    byte[] authDecoded = Base64.getDecoder().decode(Auth);
                    var authTranslate = new String(authDecoded);

                    String username = authTranslate.split(":")[0];
                    String password = authTranslate.split(":")[1];

                    //validar usuário
                    var user = this.userRepository.findByUsername(username);

                    if(user != null) {
                    //validar senha
                    var verifyPassword = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

                    if(verifyPassword.verified){
                        //enviando o idUser pelo request para ser setado na task e no delete do usuário
                        request.setAttribute("idUser", user.getId());
                        //continue a requisição
                        filterChain.doFilter(request, response);
                        } else {
                            response.sendError(401);
                        }
                    } else {
                    response.sendError(401);
                }
                } else {
                    filterChain.doFilter(request, response);
                }         
    }

}
