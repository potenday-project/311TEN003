package com.bside.bside_311;

import com.bside.bside_311.entity.Post;

public class PostDomain extends Post {

    public static PostDomain of(Post post) throws CloneNotSupportedException {
        return (PostDomain) Post.of(post);
    }
}
