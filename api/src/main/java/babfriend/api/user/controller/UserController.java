package babfriend.api.user.controller;

import babfriend.api.common.ResponseDto;
import babfriend.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

//    private final UserService userService;
//
//    @GetMapping("/{id}/info")
//    public ResponseDto<?> userInfo(@PathVariable("id") String id) {
//
//    }
}
