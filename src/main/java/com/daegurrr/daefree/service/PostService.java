package com.daegurrr.daefree.service;

import com.daegurrr.daefree.dto.post.PostRequest;
import com.daegurrr.daefree.dto.post.PostResponse;
import com.daegurrr.daefree.entity.Account;
import com.daegurrr.daefree.entity.Comment;
import com.daegurrr.daefree.entity.Post;
import com.daegurrr.daefree.repository.AccountRepository;
import com.daegurrr.daefree.repository.CommentRepository;
import com.daegurrr.daefree.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final AccountRepository accountRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void create(PostRequest.Create request, String id) {
        Account account = accountRepository.findById(Long.valueOf(id))
                        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
        Post post = request.toEntity().setAuthor(account);
        postRepository.save(post);
    }

    @Transactional
    public PostResponse.Detail getDetail(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시글입니다."));
        post.addViews();
        return PostResponse.Detail.from(post);
    }

    @Transactional
    public void createComment(Long postId, String content, Long accountId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시글입니다."));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));

        Comment comment = Comment.builder()
                .content(content)
                .author(account.getName())
                .profileUrl(account.getProfileUrl())
                .post(post)
                .build();
        post.addComments(comment);
        commentRepository.save(comment);
    }


}
