package org.emmek.beu2w2d4.services;

import org.emmek.beu2w2d4.entities.Author;
import org.emmek.beu2w2d4.entities.BlogPost;
import org.emmek.beu2w2d4.exceptions.NotFoundException;
import org.emmek.beu2w2d4.repositories.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Service
public class BlogpostService {

    @Autowired
    BlogPostRepository bpRepository;
    @Autowired
    AuthorService authorService;

    public BlogPost save(BlogPost blogpost) {
        Author author = authorService.findById(blogpost.getAuthor().getId());
        if (author != null) {
            blogpost.setAuthor(author);
        }
        bpRepository.save(blogpost);
        return blogpost;
    }

    public Page<BlogPost> getBlogposts(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return bpRepository.findAll(pageable);
    }

    public BlogPost findById(int id) throws NotFoundException {
        return bpRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(int id) throws MethodArgumentTypeMismatchException {
        bpRepository.deleteById(id);
    }

    public BlogPost findByIdAndUpdate(int id, BlogPost blogpost) throws NotFoundException {
        BlogPost bp = bpRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        bp.setCategory(blogpost.getCategory());
        bp.setTitle(blogpost.getTitle());
        bp.setPicture(blogpost.getPicture());
        bp.setContent(blogpost.getContent());
        bp.setAuthor(blogpost.getAuthor());
        return bpRepository.save(bp);
    }

    public Page<BlogPost> getBlogPostsByAuthorId(int id, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return bpRepository.getBlogPostsByAuthorId(id, pageable);
    }
}
