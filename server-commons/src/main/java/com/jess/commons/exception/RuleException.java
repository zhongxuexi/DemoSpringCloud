package com.jess.commons.exception;

import com.svw.tbox.tcloud.commons.rule.Rule;

/**
 * 规则异常类
 * <p>ClassName: RuleException</p>
 * <p>Description: 表示规则异常对象</p>
 * <p>Author: liuyunlong</p>
 * <p>Date: 2017年11月2日</p>
 */
public class RuleException extends RuntimeException {

  
    private static final long serialVersionUID = 1418133313848516191L;

    @Override
    public String getMessage() {
       return this.getRule().brief();
    }
    
    private final Rule rule;
   
    public Rule getRule() {
        return rule;
    }

    public RuleException(Rule rule) {
       this.rule=rule;
    }

}