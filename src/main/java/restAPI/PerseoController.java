package restAPI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import auth.AuthUtils;
import fiwoo.microservices.rules_External_Actions.fiwoo_rules_External_Actions.Logic;


@RestController
public class PerseoController {

	private static Logic logic;
	
	public PerseoController() {
		logic = new Logic();
	}
	
	// Get Methods
	@RequestMapping(method = RequestMethod.GET, value = "/statements", headers="Accept=application/json")
	public ResponseEntity getRules() {
		auth.model.User loggedUser = AuthUtils.getUser();	
		String result = logic.getRulesOfUser(loggedUser.getId().toString()); 
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}	
	
	// Post Method
	/*
	 * ENTRY JSON:
	 * { "user_id": "user_id",
	 * 	 "rule" : {rule_JSON} }	* 
	 * 
	 */
	@RequestMapping(value = "/statements/advanced/add", method = RequestMethod.POST, headers="Accept=application/json", consumes = {"application/json"})
	@ResponseBody
	public ResponseEntity addRule(@RequestBody String body) {
		auth.model.User loggedUser = AuthUtils.getUser();	
		Gson gson = new GsonBuilder().serializeNulls().create();
		gson.serializeNulls();
		Object body_aux = gson.fromJson(body, Object.class);
		LinkedTreeMap<Object, Object> body_map = (LinkedTreeMap<Object, Object>) body_aux;
		if (body_map.get("rule") ==  null) 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"A rule must be sent\"}");
		String ruleJson = gson.toJson(body_map.get("rule"),LinkedTreeMap.class);
//		if (body_map.get("user_id") == null) 
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"A user_id must be sent\"}");
//		String user_id =  body_map.get("user_id").toString();
		String description = "no description";
		if (body_map.get("description") != null)
			description = body_map.get("description").toString();
		String response = logic.parseAdvancedRule(ruleJson, loggedUser.getId().toString(), description);
		System.out.println(response);
		if (response.contains("\"201\":\"created\""))
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	// Delete Methods 
	@RequestMapping(value = "/statements", method = RequestMethod.DELETE, headers= {"Accept=application/json"})
	@ResponseBody
	public ResponseEntity deleteRule(@RequestParam("rule_name") String rule_name) {
		auth.model.User loggedUser = AuthUtils.getUser();	
		String response = logic.deleteRuleAndSubscription(loggedUser.getId().toString(), rule_name);
		if (response.contains("\"error\" : \"Rule does not exist\""))
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity(response, HttpStatus.OK);
	}
}
