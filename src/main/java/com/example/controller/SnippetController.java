package com.example.controller;

import com.example.core.Calculator;
import com.example.dto.SnippetDto;
import com.example.entity.Snippet;
import com.example.service.SnippetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.service.DtoUtils.toDto;

@RestController()
@RequestMapping("/rest")
public class SnippetController {

    private final SnippetService snippetService;



    @Autowired
    public SnippetController(SnippetService snippetService) {
        this.snippetService = snippetService;
    }

    @GetMapping("/{id}")
    public SnippetDto getSnippet(@PathVariable(name = "id")String snippetId){
        //TODO refactor it
        return toDto(snippetService.find(snippetId));
    }

    @PostMapping
    public SnippetDto saveSnippet(@RequestBody SnippetDto snippetDto){
        return snippetService.save(snippetDto);
    }
}
