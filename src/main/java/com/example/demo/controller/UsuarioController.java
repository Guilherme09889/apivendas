package com.example.demo.controller;

import com.example.demo.dto.usuario.UsuarioGet;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.usuario.UsuarioPost;
import com.example.demo.service.UsuarioService;
import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService userService;

    public UsuarioController(UsuarioService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarUsuario(@Valid @RequestBody UsuarioPost userPost) {
        userService.criarUsuario(userPost);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioGet> listarTodos(){
        return userService.findAllNative();
    }


}
