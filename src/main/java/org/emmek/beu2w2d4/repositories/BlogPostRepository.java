package org.emmek.beu2w2d4.repositories;


import org.emmek.beu2w2d4.entities.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Integer> {
    Page<BlogPost> getBlogPostsByAuthorId(int id, Pageable pageable);
}
