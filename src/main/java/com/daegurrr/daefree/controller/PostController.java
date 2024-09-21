package com.daegurrr.daefree.controller;

import com.daegurrr.daefree.dto.post.PostRequest;
import com.daegurrr.daefree.dto.post.PostResponse;
import com.daegurrr.daefree.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post", description = "게시판 관련 API")
@RestController
@RequestMapping("api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    @Operation(summary = "게시글 작성 API")
    public ResponseEntity<String> create(@RequestBody PostRequest.Create request,
                                         Authentication authentication) {
        postService.create(request, authentication.getName());
        return ResponseEntity.ok().body("게시글이 등록되었습니다.");
    }

    @GetMapping("{postId}")
    @Operation(summary = "게시글 한개 조회 API")
    public ResponseEntity<PostResponse.Detail> getDetail(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok().body(postService.getDetail(postId));
    }

    @PostMapping("{postId}/comment")
    @Operation(summary = "게시글 댓글 작성")
    public ResponseEntity<String> createComment(@PathVariable("postId") Long postId,
                                                @RequestBody PostRequest.CreateComment request,
                                                Authentication authentication) {
        postService.createComment(postId, request.getComment(), authentication.getName());
        return ResponseEntity.ok().body("댓글이 등록되었습니다.");
    }
}
