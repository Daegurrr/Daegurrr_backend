package com.daegurrr.daefree.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Account {
    @Id
    private Long id;
    private String name;
    private String profileUrl;
    @OneToMany(mappedBy = "account")
    private List<Post> posts = new ArrayList<>();
}
