package fiwoo.microservices.rules_External_Actions.dao;

import fiwoo.microservices.rules_External_Actions.model.RuleDB;

// DAO = Data Access Object

public interface RuleDBDAO {

	public void insert(RuleDB rule);
	public RuleDB findByUser(String user_id);
	public int delete(String rule_id, String user_id);
	public boolean existsRule(String rule_id);
	public String getSubscriptionId(String rule_id);
}
