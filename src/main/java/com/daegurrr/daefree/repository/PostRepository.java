package com.daegurrr.daefree.repository;

import com.daegurrr.daefree.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
