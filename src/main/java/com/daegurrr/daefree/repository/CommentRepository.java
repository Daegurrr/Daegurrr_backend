package com.daegurrr.daefree.repository;

import com.daegurrr.daefree.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
