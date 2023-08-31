package AlarmService.controller;

import AlarmService.service.MemberTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlarmController {
    private final MemberTokenService memberTokenService;

}
