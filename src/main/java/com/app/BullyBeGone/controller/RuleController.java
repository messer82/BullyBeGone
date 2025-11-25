package com.app.BullyBeGone.controller;

import com.app.BullyBeGone.domain.entity.Rule;
import com.app.BullyBeGone.domain.model.RulePatch;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.app.BullyBeGone.service.RulesService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/rules")
public class RuleController {

    @Autowired
    private final RulesService rulesService;

    @GetMapping("/ruleId/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Rule getRuleById(@RequestParam("ruleId") String id) {
        return rulesService.getRuleById(id);
    }

    @PostMapping("/rule")
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> createRule(@RequestBody @Valid Rule rule) {
        return rulesService.createRule(rule);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Rule> getAllRules() {
        return rulesService.getAllRules();
    }

    @PostMapping("/rulePatch")
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> updateRule(@RequestBody @Valid RulePatch rulePatch) {
        return rulesService.updateRule(rulePatch);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public String deleteRule(@RequestParam(required = true) String id) {
        return rulesService.deleteRule(id);
    }

}
