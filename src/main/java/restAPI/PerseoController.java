package restAPI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import fiwoo.microservices.rules_External_Actions.fiwoo_rules_External_Actions.Logic;


@RestController
public class PerseoController {

	private static Logic logic;
	
	public PerseoController() {
		logic = new Logic();
	}
	
	// Get Methods
	@RequestMapping(method = RequestMethod.GET, value = "/rules/statements/{user_id}", headers="Accept=application/json")
	public String getRules(@PathVariable("user_id") String user_id) {
		return logic.getRulesOfUser(user_id);
	}	
	
	// Post Methods
	/*
	 * ENTRY JSON:
	 * { "user_id": "user_id",
	 * 	 "rule" : {rule_JSON} }	* 
	 * 
	 */
	@RequestMapping(value = "/rules/statements/advanced/add", method = RequestMethod.POST, headers="Accept=application/json", consumes = {"application/json"})
	@ResponseBody
	public ResponseEntity addRule(@RequestBody String body) {
		Gson gson = new GsonBuilder().serializeNulls().create();
		gson.serializeNulls();
		Object body_aux = gson.fromJson(body, Object.class);
		LinkedTreeMap<Object, Object> body_map = (LinkedTreeMap<Object, Object>) body_aux;
		String ruleJson = gson.toJson(body_map.get("rule"),LinkedTreeMap.class);
		String response = logic.parseAdvancedRule(ruleJson, body_map.get("user_id").toString());
		System.out.println(response);
		if (response.contains("\"201\":\"created\""))
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
		
//	@RequestMapping(value = "/rules/host", method = RequestMethod.POST, consumes = {"text/plain"})
//	@ResponseBody
//	public ResponseEntity setHost(@RequestBody String body) {
//		String response = logic.setHost(body);
//		String responseCode = response.substring(0, 3);
//		if (responseCode.equals("201"))
//			return ResponseEntity.status(HttpStatus.CREATED).body(response);
//		else
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); 
//	}
//	
//	@RequestMapping(value = "/rules/service", method = RequestMethod.POST, consumes = {"text/plain"})
//	@ResponseBody
//	public ResponseEntity setService(@RequestBody String body) {
//		String response = logic.setService(body);
//		String responseCode = response.substring(0, 3);
//		if (responseCode.equals("201"))
//			return ResponseEntity.status(HttpStatus.CREATED).body(response);
//		else
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); 
//	}
//	
//	@RequestMapping(value = "/rules/servicePath", method = RequestMethod.POST, consumes = {"text/plain"})
//	@ResponseBody
//	public ResponseEntity setServicePath(@RequestBody String body) {
//		String response = logic.setServicePath(body);
//		String responseCode = response.substring(0, 3);
//		if (responseCode.equals("201"))
//			return ResponseEntity.status(HttpStatus.CREATED).body(response);
//		else
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); 
//	}
	
	// Delete Methods 
	@RequestMapping(value = "/rules/statements/{user_id}", method = RequestMethod.DELETE, headers= {"Accept=application/json"})
	@ResponseBody
	public ResponseEntity deleteRule(@PathVariable("user_id") String user_id,  @RequestParam("rule_id") String rule_id) {
		String response = logic.deleteRuleAndSubscription(user_id, rule_id);
		if (response.contains("\"error\" : \"Rule does not exist\""))
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity(response, HttpStatus.OK);
	}
}
