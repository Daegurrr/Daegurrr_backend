package com.daegurrr.daefree.dto.post;

import com.daegurrr.daefree.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

public class PostRequest {

    @Getter
    public static class Create {
        private String title;

        private String description;

        private String date;

        private String place;

        private String content;

        private String target;

        public Post toEntity() {
            return Post.builder()
                    .title(title)
                    .description(description)
                    .date(date)
                    .place(place)
                    .content(content)
                    .target(target)
                    .createAt(LocalDateTime.now())
                    .build();
        }
    }

    @Getter
    public static class CreateComment {
        private String comment;

    }
}
