package com.app.BullyBeGone.service;

import com.app.BullyBeGone.domain.entity.Rule;
import com.app.BullyBeGone.domain.model.RulePatch;

import java.util.List;

public interface RulesService {

    public Rule getRuleById(String id);

    public List<String> createRule(Rule rule);

    public List<Rule> getAllRules();

    public List<String> updateRule(RulePatch rulePatch);

    public String deleteRule(String id);

}
