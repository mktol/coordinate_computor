package com.example.controller;

import com.example.dto.SnippetDto;
import com.example.service.SnippetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.service.DtoUtils.toDto;

@RestController()
@RequestMapping("/rest")
public class SnippetController {

    private final SnippetService snippetService;

    @Autowired
    public SnippetController(SnippetService snippetService) {
        this.snippetService = snippetService;
    }

    @GetMapping("/snippets/{id}")
    public SnippetDto getSnippet(@PathVariable(name = "id")String snippetId){
        return toDto(snippetService.find(snippetId));
    }

    @PostMapping("/snippets")
    public SnippetDto saveSnippet(@RequestBody SnippetDto snippetDto){
        return snippetService.save(snippetDto);
    }
    @GetMapping("/snippets")
    public List<SnippetDto> findAll(){
        return snippetService.findAll();
    }

    @DeleteMapping("/snippets/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") Long id){
        snippetService.remove(id);
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }
}
