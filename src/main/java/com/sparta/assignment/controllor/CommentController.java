package com.sparta.assignment.controllor;

import com.sparta.assignment.dto.CommentRequestDto;
import com.sparta.assignment.dto.CommentResponseDto;
import com.sparta.assignment.security.UserDetailsImpl;
import com.sparta.assignment.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments/memoid/{memoid}")
    public CommentResponseDto createComment(@PathVariable Long memoid , @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(memoid, commentRequestDto, userDetails.getUser());
    }

    @PutMapping("/comments/memoid/{memoid}/commentid/{id}")
    public CommentResponseDto updateComment(@PathVariable Long memoid, @PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment(id, commentRequestDto, memoid, userDetails.getUser());
    }

    @DeleteMapping("/comments/memoid/{memoid}/commentid/{id}")
    public String deleteComment(@PathVariable Long memoid, @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(id, memoid, userDetails.getUser());
    }
}
