package com.amazon.portaldevelopment.services;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazon.portaldevelopment.configuration.ConfigurationPropertiesClass;
import com.amazon.portaldevelopment.constants.ConstantValues;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AdminService implements Administrator {
	
	@Autowired
	AdminServiceImpl adminServiceImpl;
	
	@Autowired
	ConfigurationPropertiesClass configurationPorperty;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	CommonServiceImpl commonServiceImpl;

	public String getApprovalPendingCount(String userEmail) throws JsonProcessingException {
		String userId = commonService.trimUserEmail(userEmail);
		String response = commonServiceImpl.getAdminPendingApprovals(userId);
		Map<String, Integer> pendingCount = new HashMap<>();

		int pendingTaskCount = 0;
		JSONArray jsonArray = commonService.parseResponseValue(response);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject obj = (JSONObject) jsonArray.get(i);
			JSONObject taskInfo = (JSONObject) obj.get(ConstantValues.taskInfo);
			if (taskInfo != null) {
				pendingTaskCount++;
			}
		}
		pendingCount.put(ConstantValues.COUNT, pendingTaskCount);
		String output = new ObjectMapper().writeValueAsString(pendingCount);
		return output;
	}
	
	public String getWFLicenseCount() throws JsonProcessingException, ParseException {

		String licenseResponse = adminServiceImpl.getWFLicenseCount();
		Map<String, String> totalValue = getTotalLicenseCountForWF();
		int totalInt = Integer.parseInt(totalValue.get(ConstantValues.PLAN)) + Integer.parseInt(totalValue.get(ConstantValues.WORK));
		String total = String.valueOf(totalInt);

		JSONParser parser = new JSONParser();
		String json = null;

		Map<String, Map<String, Map<String, String>>> map1 = new HashMap<>();

		Map<String, String> plan1 = new HashMap<>();
		plan1.put("totalPlanLicense", totalValue.get(ConstantValues.PLAN));
		Map<String, String> work1 = new HashMap<>();
		work1.put("totalWorkLicense", totalValue.get(ConstantValues.WORK));
		Map<String, String> review1 = new HashMap<>();
		review1.put("totalReviewLicense", totalValue.get(ConstantValues.REVIEW));

		Map<String, String> totalLicense = new HashMap<>();
		totalLicense.put("license", total);

		Map<String, Map<String, String>> allIn = new HashMap<>();
		allIn.put("totalLicense", totalLicense);

			JSONObject jsonObj = (JSONObject) parser.parse(licenseResponse);
			JSONArray array = (JSONArray) jsonObj.get(ConstantValues.DATA);

			for (int i = 0; i < array.size(); i++) {

				JSONObject licenseTypeLimit = (JSONObject) array.get(i);
				JSONObject limitObj = (JSONObject) licenseTypeLimit.get(ConstantValues.licenseTypeLimit);
				JSONObject usedLicenses = (JSONObject) limitObj.get(ConstantValues.usedLicenses);

				for (Iterator<Map.Entry<String, Integer>> entries = usedLicenses.entrySet().iterator(); entries.hasNext();) {
					Map.Entry<String, Integer> entry = entries.next();
					String key = entry.getKey();
					if (licenseTypeLimit.get(ConstantValues.NAME).toString().startsWith("Amazon EU")
							|| licenseTypeLimit.get(ConstantValues.NAME).toString().startsWith("Amazon IN")) {
						
						if (key.equalsIgnoreCase(ConstantValues.PLAN)) {
							String name = licenseTypeLimit.get(ConstantValues.NAME).toString();
							Long plan = (Long) usedLicenses.get(ConstantValues.PLAN);
							String planValue = String.valueOf(plan);
							plan1.put(name, planValue);

						} else if (key.equalsIgnoreCase(ConstantValues.WORK)) {
							String name = licenseTypeLimit.get(ConstantValues.NAME).toString();
							Long plan = (Long) usedLicenses.get(ConstantValues.WORK);
							String planValue = String.valueOf(plan);
							work1.put(name, planValue);

						} else if (key.equalsIgnoreCase(ConstantValues.REVIEW)) {
                            String name = licenseTypeLimit.get(ConstantValues.NAME).toString();
							Long plan = (Long) usedLicenses.get(ConstantValues.REVIEW);
							String planValue = String.valueOf(plan);
							review1.put(name, planValue);
						}
					}

				}
			}
			allIn.put(ConstantValues.PLAN, plan1);
			allIn.put(ConstantValues.WORK, work1);
			allIn.put(ConstantValues.REVIEW, review1);
			map1.put(ConstantValues.DATA, allIn);
			json = new ObjectMapper().writeValueAsString(map1);

		return json;
	}

	@Override
	public String getPendingApprovalForSelectedDate(String userEmail, LocalDate date) throws JsonProcessingException, ParseException {
		return commonService.approvalsByDate(userEmail, date.toString(), ConstantValues.OWNER_ID);
	}
		
	private Map<String, String> getTotalLicenseCountForWF() {
		Map<String, String> map = new HashMap<>();

		String totalCount = adminServiceImpl.getWfLicenseAndSize();
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) parser.parse(totalCount);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONArray array = (JSONArray) jsonObject.get(ConstantValues.DATA);
		JSONObject licenseList = (JSONObject) array.get(0);
		map.put(ConstantValues.PLAN, licenseList.get(ConstantValues.fullUsers).toString());
		map.put(ConstantValues.WORK, licenseList.get(ConstantValues.teamUsers).toString());
		map.put(ConstantValues.REVIEW, licenseList.get(ConstantValues.reviewUsers).toString());

		return map;
	}

	@Override
	public String getNuxeoStorageSize() throws ParseException, JsonProcessingException {
		String queries = "{\"query\":{\"bool\":{\"must\":{\"match_all\":{}},\"must_not\":[{\"term\":{\"ecm:mixinType\":\"HiddenInNavigation\"}},"
				+ "{\"term\":{\"ecm:isTrashed\":true}}],\"filter\":[{\"term\":{\"ecm:isVersion\":false}},{\"term\":{\"ecm:path@level1\":\"default-domain\"}}]}},"
				+ "\"size\":0,\"aggs\":{\"subLevel\":{\"terms\":{\"field\":\"ecm:path@level3\",\"size\":1000},"
				+ "\"aggs\":{\"size\":{\"sum\":{\"field\":\"file:content.length\"}}}}}}";

		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(queries);
		String result = adminServiceImpl.getNuxeoStorageSize(jsonObject);
		JSONObject jsonObj = (JSONObject) parser.parse(result);
		JSONObject aggregation = (JSONObject) jsonObj.get(ConstantValues.aggregations);
		JSONObject subLevels = (JSONObject) aggregation.get(ConstantValues.subLevel);
		JSONArray storageArray = (JSONArray) subLevels.get(ConstantValues.buckets);
		String response = adminServiceImpl.getTotalNuxeoLicenseCount();
		List<String> userNameList = new ArrayList<>();
		JSONArray jsonArr = commonService.parseResponseValue(response);
	    for(int i=0; i < jsonArr.size(); i++) {
	    	JSONObject obj = (JSONObject) jsonArr.get(i);
	    	if(obj.get("id").toString()!=null || !obj.get("id").toString().isEmpty()) {
	    		userNameList.add(obj.get("id").toString());
	    	}
	    }
		Map<String, Double> nuxeoStorage = new HashMap<>();
		Double amazonEUValue = 0.0;
		Double amazonINValue = 0.0;
		for (int i = 0; i < storageArray.size(); i++) {
			JSONObject key = (JSONObject) storageArray.get(i);
			String keyValue = key.get(ConstantValues.key).toString();
			if(keyValue.equalsIgnoreCase("Finalized") || keyValue.equalsIgnoreCase("Archived")
					|| keyValue.equalsIgnoreCase("Available") || keyValue.equalsIgnoreCase("Administrator")) {
				amazonEUValue = sizeCalculationInGB(key, amazonEUValue);
			} else if (keyValue.equalsIgnoreCase("Amazon_IN")) {
				amazonINValue = sizeCalculationInGB(key, amazonINValue);
			} else {
				if(userNameList.contains(keyValue)) {
				String res = adminServiceImpl.getUserGroupFromNuxeo(keyValue);
				JSONObject obj = (JSONObject) parser.parse(res);
				JSONObject properties = (JSONObject) obj.get(ConstantValues.PROPERTIES);
				JSONArray arr = (JSONArray) properties.get(ConstantValues.GROUPS);
		    	if(arr.size() == 1) {
		    		String value = (String) arr.get(0);
		    			if(value.contains("_IN") || value.contains("-IN")) {
		    				amazonINValue = sizeCalculationInGB(key, amazonINValue);
				    	} else {
				    		amazonEUValue = sizeCalculationInGB(key, amazonEUValue);
				    	}
		    		}
		    	}
			}
		}
		nuxeoStorage.put("totalNuxeoSize", configurationPorperty.getTotalNuxeoStorage());
		nuxeoStorage.put("amazon_EU", amazonEUValue);
		nuxeoStorage.put("amazon_IN", amazonINValue);
		String StorageNX = new ObjectMapper().writeValueAsString(nuxeoStorage);
		return StorageNX;
	}

	private Double sizeCalculationInGB(JSONObject key, Double value) {
		Double resultValue = 0.0;
		JSONObject size = (JSONObject) key.get(ConstantValues.size);
		double doubleValue = (Double) size.get(ConstantValues.value);
	    double g = (((doubleValue/1024.0)/1024.0)/1024.0);
	    DecimalFormat dec = new DecimalFormat("0.00");
	    resultValue = value + Double.parseDouble(dec.format(g));
	    return resultValue;
	}

	@Override
	public String getAssetStatus() throws ParseException, JsonProcessingException {
		Map<String, Map<String, Integer>> data = new HashMap<>();
		Map<String, Integer> assets = new HashMap<>();
		String asset[] = new String[] { "Available", "Draft" };
		for (int i = 0; i < asset.length; i++) {
			String assetResponse = adminServiceImpl.getAssetStatusFromWF("MA:AssetLifecycleStatus", asset[i]);
			int assetCount = commonService.assetStatusParse(assetResponse);
			assets.put(asset[i], assetCount);
		}
		data.put(ConstantValues.DATA, assets);
		String assetsJson = new ObjectMapper().writeValueAsString(data);
		return assetsJson;
	}

	@Override
	public String getAssetTypeCount() throws ParseException, JsonProcessingException {
		Map<String, Map<String, Integer>> data = new HashMap<>();
		Map<String, Integer> assetTypeCount = new HashMap<>();
		String arr[] = new String[] { "TV", "OLV", "Radio", "Social", "Digital Display", "Print", "OOH", "Cinema",
				"Style Guide", "TBC", "Onsite", "Other"};
		String assetForNonMetadata = adminServiceImpl.getAssetStatusForNonMetadata();
		int count = commonService.assetStatusParse(assetForNonMetadata);
		assetTypeCount.put("NULL", count);
		for (int i = 0; i < arr.length; i++) {
			String asset = adminServiceImpl.getAssetStatusFromWF("MA:Assettype", arr[i]);
			String AssetValue = arr[i].replaceAll("\\s", "");
			int assetCount = commonService.assetStatusParse(asset);
			assetTypeCount.put(AssetValue, assetCount);
		}
		data.put(ConstantValues.DATA, assetTypeCount);
		String json = new ObjectMapper().writeValueAsString(data);
		return json;
	}

	@Override
	public String getNuxeoLicenseCount() throws JsonProcessingException {
		
		int amazonIN = 0;
		int amazonEU = 0;
		Map<String, Integer> nuxeoLicense = new HashMap<>();
	    String response = adminServiceImpl.getTotalNuxeoLicenseCount();
	    JSONArray jsonArr = commonService.parseResponseValue(response);
	    for(int i=0; i < jsonArr.size(); i++) {
	    	JSONObject obj = (JSONObject) jsonArr.get(i);
	    	JSONObject prop = (JSONObject) obj.get(ConstantValues.PROPERTIES);
	    	JSONArray arr = (JSONArray) prop.get(ConstantValues.GROUPS);
	    	if(arr.size() == 1) {
	    		String value = (String) arr.get(0);
		    	if(value.contains("_IN") || value.contains("-IN")) {
		    		amazonIN++;
		    	} else {
		    		amazonEU++;
		    	}
	    	}
	    }
	    nuxeoLicense.put("totalNuxeoLicense", configurationPorperty.getTotalNuxeoLicense());
	    nuxeoLicense.put("amazonIN", amazonIN);
	    nuxeoLicense.put("amazonEU", amazonEU);
	    String json = new ObjectMapper().writeValueAsString(nuxeoLicense);
		return json; 
	}

	@Override
	public String getWorkFrontStorage() throws ParseException, JsonProcessingException {
		
		Map<String, Double> map = new HashMap<>();
		String response = adminServiceImpl.getWfLicenseAndSize();
		JSONArray array = commonService.parseJsonArray(response);
		JSONObject licenseList = (JSONObject) array.get(0);
		Long value = (Long) licenseList.get("externalDocumentStorage");
		Double totalValue = value.doubleValue();
		map.put("totalStorageValue", totalValue);
		String result = adminServiceImpl.getWfStorageByGroup();
		JSONObject data = commonService.parseJsonObject(result);
		Double amazonIN = 0.0;
		Double amazonEU = 0.0;
		ArrayList<String> list = new ArrayList<String>(data.keySet());
		for(int i = 0; i < list.size(); i++) {
			if(!list.get(i).isEmpty()) {
				JSONObject jsonObj = (JSONObject) data.get(list.get(i));
			if(jsonObj.get("owner_homeGroupID").toString() != null) {
				String res = adminServiceImpl.getHomeGroupName(jsonObj.get("owner_homeGroupID").toString());
				JSONObject groupNameObj = commonService.parseJsonObject(res);
				String groupName = groupNameObj.get(ConstantValues.NAME).toString();
				if(groupName.contains("IN")) {
					amazonIN = (Double) jsonObj.get("sum_currentVersion_docSize");
					 double amazonINValue = (((amazonIN/1024.0)/1024.0)/1024.0);
					    DecimalFormat dec = new DecimalFormat("0.00");
					    amazonIN = Double.parseDouble(dec.format(amazonINValue));
					    map.put(groupName, amazonIN);
				} else if(groupName.contains("EU")) {
					amazonEU = (Double) jsonObj.get("sum_currentVersion_docSize");
					double amazonEUValue = (((amazonEU/1024.0)/1024.0)/1024.0);
				    DecimalFormat dec = new DecimalFormat("0.00");
				    amazonEU = Double.parseDouble(dec.format(amazonEUValue));
				    map.put(groupName, amazonEU);
				}
			}
		}		
	}
		 String json = new ObjectMapper().writeValueAsString(map);
		 return json; 
	}

}