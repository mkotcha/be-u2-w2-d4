package org.emmek.beu2w2d4.controller;

import org.emmek.beu2w2d4.entities.Author;
import org.emmek.beu2w2d4.exceptions.BadRequestException;
import org.emmek.beu2w2d4.payloads.author.AuthorPostDTO;
import org.emmek.beu2w2d4.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("")
    public Page<Author> getAuthors(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String sort) {
        return authorService.getAuthors(page, size, sort);
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Author postAuthors(@RequestBody @Validated AuthorPostDTO author, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return authorService.save(author);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Author getAuthorById(@PathVariable int id) {
        try {
            return authorService.findById(id);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/{id}")
    public Author findByIdAndUpdate(@PathVariable int id, @RequestBody Author author) {
        return authorService.findByIdAndUpdate(id, author);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable int id) {
        authorService.findByIdAndDelete(id);
    }

    @PostMapping("/{id}/avatar")
    public String uploadExample(@PathVariable int id, @RequestParam("avatar") MultipartFile body) throws IOException {
        System.out.println(body.getSize());
        System.out.println(body.getContentType());
        return authorService.uploadPicture(id, body);
    }

}
