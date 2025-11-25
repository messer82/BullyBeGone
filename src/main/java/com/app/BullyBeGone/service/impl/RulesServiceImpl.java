package com.app.BullyBeGone.service.impl;

import com.app.BullyBeGone.domain.entity.Rule;
import com.app.BullyBeGone.domain.model.RulePatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.app.BullyBeGone.service.RulesService;

import java.util.ArrayList;
import java.util.List;

@Component
public class RulesServiceImpl implements RulesService {

    private static final String sqlSearchRuleById = "SELECT * FROM information_schema.rules_table WHERE rule_id=?";
    private static final String sqlCreateRule = "insert into information_schema.rules_table (rule_id, rule_description, rule_weight) values (?, ?, ?)";
    private static final String sqlGetAllRules = "SELECT * FROM information_schema.rules_table";
    private static final String sqlUpdateRuleDescription = "UPDATE information_schema.rules_table SET rule_description = ? WHERE rule_id = ?";
    private static final String sqlUpdateRuleWeight = "UPDATE information_schema.rules_table SET rule_weight = ? WHERE rule_id = ?";
    private static final String sqlDeleteRuleById = "DELETE FROM information_schema.rules_table WHERE rule_id=?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<String> createRule(Rule rule) {
        List<String> result = new ArrayList<>();
        Rule existingRule = getRuleById(rule.getId());
        if (existingRule != null) {
            result.add("There already is a registry of a rule with this national id!");
        } else {
            try {
                jdbcTemplate.update(sqlCreateRule, rule.getId(), rule.getDescription(), rule.getWeight());
                result.add("Rule was added to database!");
            } catch (Exception e) {
                result.add("Rule could not be added to database because " + e.getMessage());
            }
        }
        return result;
    }

    @Override
    public Rule getRuleById(String id) {
        Rule rule = null;

        try {
            rule = jdbcTemplate.queryForObject(sqlSearchRuleById, getRuleRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            e.getMessage();
        }

        return rule;
    }

    @Override
    public List<Rule> getAllRules() {
        List<Rule> rules = new ArrayList<>();

        try {
            rules = jdbcTemplate.query(sqlGetAllRules, getRuleRowMapper());
        } catch (EmptyResultDataAccessException e) {
            e.getMessage();
        }

        return rules;
    }

    @Override
    public List<String> updateRule(RulePatch rulePatch) {
        List<String> result = new ArrayList();
        Rule rule = null;

        try {
            rule = jdbcTemplate.queryForObject(sqlSearchRuleById, getRuleRowMapper(), rulePatch.getId());
        } catch (EmptyResultDataAccessException e) {
            e.getMessage();
        }

        if (rule != null) {
            if (!rulePatch.getDescription().equals(rule.getDescription())) {
                jdbcTemplate.update(sqlUpdateRuleDescription, rulePatch.getDescription(), rulePatch.getId());
                result.add("Rule description was updated!");
            }
            if (rulePatch.getWeight() != rule.getWeight()) {
                jdbcTemplate.update(sqlUpdateRuleWeight, rulePatch.getWeight(), rulePatch.getId());
                result.add("Rule weight was updated!");
            }
            if (rulePatch.getDescription().equals(rule.getDescription()) && rulePatch.getWeight() == rule.getWeight()) {
                result.add("Rule patch is equal to the existing rule!");
            }
        } else {
            result.add("There is no rule with the provided id!");
        }

        return result;
    }

    @Override
    public String deleteRule(String id) {
        String result = null;

        Rule rule = jdbcTemplate.queryForObject(sqlSearchRuleById, getRuleRowMapper(), id);

        if (rule != null) {
            jdbcTemplate.update(sqlDeleteRuleById, id);
            result = "Rule was deleted!";
        } else {
            result = "No corresponding rule was found!";
        }

        return result;
    }

    private RowMapper<Rule> getRuleRowMapper() {
        return ((rs, rowNum) -> {
            Rule rule = new Rule();
            rule.setId(rs.getString("rule_id"));
            rule.setDescription(rs.getString("rule_description"));
            rule.setWeight(rs.getInt("rule_weight"));
            return rule;
        });
    }

}
