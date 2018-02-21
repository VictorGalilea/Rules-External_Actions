package fiwoo.microservices.rules_External_Actions.dao;

import java.util.List;

import fiwoo.microservices.rules_External_Actions.model.RuleDB;

// DAO = Data Access Object

public interface RuleDBDAO{

	public void createTable() ;
	public String insert(RuleDB rule);
	public List<RuleDB> findByUser(String user_id);
	public int delete(String rule_id, String user_id);
	public boolean existsRule(String rule_id, String user_id);
	public String getSubscriptionId(String rule_id);
}
