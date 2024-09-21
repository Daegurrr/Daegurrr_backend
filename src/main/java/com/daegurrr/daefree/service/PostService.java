package com.daegurrr.daefree.service;

import com.daegurrr.daefree.dto.post.PostRequest;
import com.daegurrr.daefree.entity.Account;
import com.daegurrr.daefree.entity.Post;
import com.daegurrr.daefree.repository.AccountRepository;
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

    @Transactional
    public void create(PostRequest.Create request, String id) {
        Account account = accountRepository.findById(Long.valueOf(id))
                        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
        Post post = request.toEntity().setAuthor(account);
        postRepository.save(post);
    }


}
