package org.emmek.beu2w2d4.controller;

import org.emmek.beu2w2d4.entities.BlogPost;
import org.emmek.beu2w2d4.services.BlogpostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blogposts")
public class BlogPostController {

    @Autowired
    private BlogpostService bpService;

    @GetMapping("")
    public Page<BlogPost> getBlogPosts(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String sort) {
        return bpService.getBlogposts(page, size, sort);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPost postBlogPosts(@RequestBody BlogPost blogpost) {
        return bpService.save(blogpost);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public BlogPost getBlogPostById(@PathVariable int id) {
        try {
            return bpService.findById(id);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/{id}")
    public BlogPost findByIdAndUpdate(@PathVariable int id, @RequestBody BlogPost blogpost) {
        return bpService.findByIdAndUpdate(id, blogpost);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable int id) {
        bpService.findByIdAndDelete(id);
    }

    @GetMapping("author/{id}")
    public Page<BlogPost> getBlogPostsByAuthorId(@PathVariable int id,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(defaultValue = "id") String sort) {
        return bpService.getBlogPostsByAuthorId(id, page, size, sort);
    }

}
