package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.AuthGroup;
import com.RulesTV.RulesTV.entity.AuthLogin;
import com.RulesTV.RulesTV.entity.AuthLogout;
import com.RulesTV.RulesTV.services.AuthGroupService;
import com.RulesTV.RulesTV.services.LogService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("login/all")
    public List<AuthLogin> getAllLogins() {
        return logService.getAllLogins();
    }

    @GetMapping("logout/all")
    public List<AuthLogout> getAllLogouts() {
        return logService.getAllLogouts();
    }

}
