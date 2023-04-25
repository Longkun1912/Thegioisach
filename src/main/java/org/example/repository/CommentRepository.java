package org.example.repository;

import org.example.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("SELECT c from Comment c WHERE c.parent =:parent_id ORDER BY c.created_time ASC")
    List<Comment> getRepliesByComment(@Param("parent_id") Integer parent_id);

    @Query("SELECT c from Comment c WHERE c.post.id =:post_id ORDER BY c.created_time ASC")
    List<Comment> getCommentsForPost(@Param("post_id") UUID post_id);
}
