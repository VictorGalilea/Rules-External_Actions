package fiwoo.microservices.rules_External_Actions.fiwoo_rules_External_Actions;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import fiwoo.microservices.rules_External_Actions.model.RuleDB;

public class LogicTest {

	private static Logic logic;
	
	//It runs before any test
	@BeforeClass
	public static void initialize()
	{
		logic = new Logic();
	}
	
	@Test
	public void dataBaseTest() {
		// Insertion
		String result = logic.getRuleDAO().insert(new RuleDB("id_test", "id_test", "id_test", "id_test", "id_test", "id_test"));
		assertTrue("error inserting subscription in db. Check database connection and table", result.equals("{\"201\":\"created\"}"));
		
		// Delete
		int result3 = logic.getRuleDAO().delete("id_test","id_test");
		assertTrue("error deleting rule from db",result3 > 0);
	}
	
	@Test
	public void orionSubscriptionTest() {
		String result = "{";
		Gson gson = new Gson();
		gson.serializeNulls();
		
		// creation
		List<String> attributes = new ArrayList<String>();
		List<Entity> entities = new ArrayList<Entity>();
        attributes.add("BloodPressure");
        entities.add(new Entity("entity_type", true, "entity_id"));
		OrionSubscription s = logic.createOrionSubscription("entity_id", "entity_type", attributes, "", "", "");
		assertTrue(s.getEntities().get(0).getId().equals(entities.get(0).getId()));
		
		// send Subscription
		String subscription_result = logic.sendSubscription(s);
		LinkedTreeMap<Object, Object> subscriptionResultMap = (LinkedTreeMap<Object, Object>) gson.fromJson(subscription_result, Object.class);
		String subscriptionId = "";
		if (subscriptionResultMap.get("subscribeError") == null) {
			// No error
			subscriptionId = ((LinkedTreeMap<Object, Object>)subscriptionResultMap.get("subscribeResponse")).get("subscriptionId").toString();
			result += "\"subscription\" : " + subscription_result + ", \n";
		} else {
			// Error
			assertTrue("subscription id not found",false);
		}
		
		assertTrue("subscription id found",!subscriptionId.equals(""));
		
		// delete subscription
		String result2 = logic.deleteSubscription(subscriptionId);
		assertTrue("subscription deleted", result2.equals("{\"deleted subscription\":\""+ subscriptionId + "\"}"));
	}
}
