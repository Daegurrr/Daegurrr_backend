package com.daegurrr.daefree.dto.post;

import com.daegurrr.daefree.entity.Post;
import jakarta.persistence.Column;
import lombok.Getter;

public class PostRequest {

    @Getter
    public static class Create {
        private String title;

        @Column(length = 2048)
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
                    .build();
        }
    }
}
