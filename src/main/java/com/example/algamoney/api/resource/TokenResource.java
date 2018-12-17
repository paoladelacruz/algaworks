package com.example.algamoney.api.resource;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.config.property.AlgamoneyApiProperty;
import com.example.algamoney.api.model.Usuario;
import com.example.algamoney.api.repository.UsuarioRepository;

@RestController
@RequestMapping("/tokens")
public class TokenResource {
	
	@Autowired
	private AlgamoneyApiProperty algamoneyApiProperty;
	
	@DeleteMapping("revoke")
	public void revoke(HttpServletRequest req, HttpServletResponse resp) {
		Cookie cookie = new Cookie("refreshToken", null);
		cookie.setHttpOnly(true);
		cookie.setSecure(algamoneyApiProperty.getSeguranca().isEnableHttps()); 
		cookie.setPath(req.getContextPath() + "/oauth/token");
		cookie.setMaxAge(0);

		
		resp.addCookie(cookie);
		resp.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	@Autowired
    private UsuarioRepository usuarioRepository;
    
    @GetMapping("/usuario/logeado") //Usuario logado no sistema
    public ResponseEntity<Usuario> getUsuarioAtual(HttpServletRequest req) {
        Principal principal = req.getUserPrincipal();
        String usuNome = principal.getName();
        Optional<Usuario> usuoptional = usuarioRepository.findByEmail(usuNome);
        Usuario usuario = usuoptional.orElseThrow(() ->new RuntimeException("Info de usuário logado não foram acessíveis."));
        
        return ResponseEntity.ok(usuario);
    }
}
