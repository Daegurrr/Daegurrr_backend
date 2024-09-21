package com.daegurrr.daefree.service;

import com.daegurrr.daefree.dto.PagingResponse;
import com.daegurrr.daefree.dto.post.PostRequest;
import com.daegurrr.daefree.dto.post.PostResponse;
import com.daegurrr.daefree.entity.Account;
import com.daegurrr.daefree.entity.Comment;
import com.daegurrr.daefree.entity.Post;
import com.daegurrr.daefree.repository.AccountRepository;
import com.daegurrr.daefree.repository.CommentRepository;
import com.daegurrr.daefree.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public void createComment(Long postId, String content, String accountId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시글입니다."));
        Account account = accountRepository.findById(Long.valueOf(accountId))
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


    @Transactional(readOnly = true)
    public PagingResponse<PostResponse.Summary> getPosts(int page, int size) {
        Page<Post> postPage = postRepository.findAll(PageRequest.of(page, size));
        return PagingResponse.from(postPage, PostResponse.Summary::from);
    }


}
