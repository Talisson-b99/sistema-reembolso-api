package com.barbosa.sistema_reembolso.security;

import com.barbosa.sistema_reembolso.Exception.business.EmailNaoEncontradoException;

import com.barbosa.sistema_reembolso.domain.model.Usuario;
import com.barbosa.sistema_reembolso.repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class TokenUtil {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final String SECRET_KEY= "tlaissolfçsfksflçskflçdfskçf";

    public  Token encode(Usuario usuario){
        try {
            Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

            String jwtToken = Jwts.builder()
                    .subject(usuario.getEmail())
                    .claim("ROLE", "ROLE_"+usuario.getRole())
                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 *2))
                    .signWith(key)
                    .compact();

            return new Token(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  Authentication decode(HttpServletRequest request){
        try {
            String token = request.getHeader("Authorization");

            if(token != null) {
                token = token.replace("Bearer ", "");
                SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

                JwtParser parser = Jwts.parser().verifyWith(secretKey).build();
                Claims claims = (Claims) parser.parse(token).getPayload();

                String email = claims.getSubject();
                Date exp = claims.getExpiration();
                String role = claims.get("ROLE").toString();

                if(email != null && exp.after(new Date(System.currentTimeMillis()))){

                    Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new EmailNaoEncontradoException(email));

                    UsuarioAutenticado usuarioAuth = new UsuarioAutenticado(
                            usuario.getId(),
                            usuario.getEmail(),
                            usuario.getSenha(),
                            usuario.getRole()
                    );
                    Authentication auth = new UsernamePasswordAuthenticationToken(usuarioAuth, null, usuarioAuth.getAuthorities());
                    return auth;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
