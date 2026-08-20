package com.example.demo.controller;

import com.example.demo.dto.fornecedor.FornecedorPostDTO;
import com.example.demo.service.FornecedorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/fornecedor")
public class FornecedorController {

    private final FornecedorService fornService;

    public FornecedorController(FornecedorService fornService){
        this.fornService = fornService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarUsuario(@Valid @RequestBody
                             FornecedorPostDTO fornecedorPostDTO){
        fornService.postFornecedor(fornecedorPostDTO);
    }

}
