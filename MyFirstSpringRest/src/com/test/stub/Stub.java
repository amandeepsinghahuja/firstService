package com.test.stub;

 /*import org.arpit.java2blog.bean.Country;*/
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/*import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;*/
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Parser;

@JsonSerialize	
@RestController
public class Stub {
	
 @RequestMapping(value = "/json/stub", method = RequestMethod.GET,produces = "application/json")	
 public String getJSONStubResponse() throws FileNotFoundException, IOException, ParseException
 {
	 String jsonResp = "{\"name\":\"John\", \"age\":31, \"city\":\"New York\" }";
	 String jsonResp1 = "{\"name\":\"John\", \"age\":31, \"cars\":[ \"Ford\", \"BMW\", \"Fiat\" ]";
	 /*return jsonResp1;*/
	 JSONParser parser = new JSONParser();
	 JSONObject j = (JSONObject) parser.parse((new FileReader("C:\\Users\\Amandeep\\Desktop\\sample.json")));
	 return j.toString();
 }
 
 @RequestMapping(value = "/soap/stub", method = RequestMethod.GET,produces = "application/xml")	
 public String geXMLStubResponse()
 {
	 String soapResp = "<SOAP-ENV:Envelope xmlns:SOAP-ENV = \"http://www.w3.org/2001/12/soap-envelope\">" +
	 		"<SOAP-ENV:Body xmlns:m = \"http://www.xyz.org/quotations\">" +
	 		"<m:GetQuotation><m:QuotationsName>MiscroSoft</m:QuotationsName>" +
	 		"</m:GetQuotation>" +
	 		"</SOAP-ENV:Body>" +
	 		"</SOAP-ENV:Envelope>";
	 
	 return soapResp;
 }
 

}