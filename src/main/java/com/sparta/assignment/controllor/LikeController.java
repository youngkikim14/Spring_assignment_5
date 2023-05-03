package com.sparta.assignment.controllor;

import com.sparta.assignment.security.UserDetailsImpl;
import com.sparta.assignment.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class LikeController {

    private final LikeService likeService;

    @PostMapping("/like/memoid/{memoid}")
    public boolean likeMemo(@PathVariable Long memoid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.likeMemo(memoid, userDetails.getUser());
    }

    @PostMapping("/like/memoid/{memoid}/commentid/{commentid}")
    public boolean likeComment(@PathVariable Long memoid ,@PathVariable Long commentid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.likeComment(memoid ,commentid, userDetails.getUser());
    }
}
