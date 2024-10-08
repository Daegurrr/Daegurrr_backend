package com.daegurrr.daefree.dto.post;

import com.daegurrr.daefree.entity.Comment;
import com.daegurrr.daefree.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static com.daegurrr.daefree.utils.Formatter.dateToString;
import java.util.List;

public class PostResponse {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Summary {
        private Long id;
        private String title;
        private String author;
        private String createAt;
        private int viewCount;
        private int commentCount;

        public static Summary from(Post post) {
            return Summary.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .author(post.getAccount().getName())
                    .createAt(dateToString(post.getCreateAt()))
                    .viewCount(post.getViewCount())
                    .commentCount(post.getCommentCount())
                    .build();
        }
    }


    @Getter
    @Builder
    @AllArgsConstructor
    public static class Detail{
        private Long id;
        private String title;
        private String author;
        private int viewCount;
        private int commentCount;
        private String description;
        private String date;
        private String place;
        private String content;
        private String target;
        private List<CommentInfo> comments;

        public static Detail from(Post post) {
            return Detail.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .author(post.getAccount().getName())
                    .viewCount(post.getViewCount())
                    .commentCount(post.getCommentCount())
                    .description(post.getDescription())
                    .date(post.getDate())
                    .place(post.getPlace())
                    .content(post.getContent())
                    .target(post.getTarget())
                    .comments(post.getComments().stream()
                            .map(CommentInfo::from)
                            .toList())
                    .build();
        }

        @Getter
        @Builder
        @AllArgsConstructor
        public static class CommentInfo{
            private String name;
            private String profileUrl;
            private String comment;

            public static CommentInfo from(Comment comment){
                return CommentInfo.builder()
                        .name(comment.getAuthor())
                        .profileUrl(comment.getProfileUrl())
                        .comment(comment.getContent())
                        .build();
            }
        }
    }
}
