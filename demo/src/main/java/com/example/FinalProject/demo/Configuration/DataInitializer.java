package com.example.FinalProject.demo.Configuration;

import com.example.FinalProject.demo.Model.Usuario;
import com.example.FinalProject.demo.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email:#{null}}")
    private String adminEmail;

    @Value("${app.admin.password:#{null}}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {

        if(adminEmail == null || adminPassword == null || adminEmail.isBlank() || adminPassword.isBlank()) {
            System.out.println(" Variables de entorno para administrador no configuradas. No se creara el administrador. ");
            return;
        }

        if(!usuarioRepository.existsByEmail(adminEmail)){
            Usuario admin = Usuario.builder()
                    .email(adminEmail)
                    .contrasenaHash(passwordEncoder.encode(adminPassword))
                    .rol("ROLE_ADMIN")
                    .monedaPreferida("COP")
                    .monedaBase("COP")
                    .activo(true)
                    .build();
            usuarioRepository.save(admin);
            System.out.println("Usuario administrador creado: "+ adminEmail);
        }
    }
}
