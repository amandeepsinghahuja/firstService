package com.easystub.controller;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.easystub.model.*;
import com.easystub.service.*;

@Controller
public class SomaController {

	SomaParameters smObject;
	//Values from properties file
	@Value("${requestPrefix}")
	String reqPrefix;

	@Value("${responsePrefix}")
	String respPrefix;
	
	@Value("${domainRestartREQ}")
	String restartReqSuffix;
	
	@Value("${domainRestartRESP}")
	String restartRespSuffix;
	
	@Value("${saveConfigREQ}")
	String saveReqSuffix;
	
	@Value("${saveConfigRESP}")
	String saveRespSuffix;
	
	String pathPrefix = "/EasyDP/src/webapp/XML/getStatus.xml";
	//String localPathPrefix = "C:\\Users\\415907\\Desktop\\";    
	String localPathPrefix = "resources/";
	@RequestMapping(value = "/userHome", method = RequestMethod.POST )
	public ModelMap displayHome(@ModelAttribute("SomaParameters") SomaParameters sp,  @Value("${getStatusRESP}") String suffix )
	{ 
		/*String ipAddress = sp.getIpAdd().substring(0,sp.getIpAdd().indexOf("-"));
		String reqFileURL = localPathPrefix+ "XML/getStatus.xml";
		String respFileURL = localPathPrefix + "XML/getStatusResponse.xml";
		sendSomaRequest ss = new sendSomaRequest();*/
		ModelMap md = new ModelMap("userHome");
		try
		{
			/*String finalURL = "https://"+ipAddress+"/service/mgmt/current";
			String soapReq = ss.createSoapReqTemp(reqFileURL,null,null);
			String soapResponse = ss.sendRequestTemp(finalURL,soapReq,sp.getUsername(),sp.getPassword(),respFileURL);
			String xpathExpr = respPrefix + suffix.substring(0,suffix.indexOf('#'));
			String domains[] = ss.parseResponseTemp(soapResponse,xpathExpr);			
			sp.setDomains(domains);
			SomaParameters.setSpObject(sp);
			smObject = SomaParameters.getSpObject();*/
			return md;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/getFileStore", method = RequestMethod.GET )
	public @ResponseBody 
	String getDirectories(@RequestParam("arguments") List<String> arguments,  @Value("${getFileStoreREQ}") String reqSuffix, @Value("${getFileStoreRESP}") String respSuffix) {

		String ipAddress = smObject.getIpAdd().substring(0,smObject.getIpAdd().indexOf("-"));
		String reqFileURL = localPathPrefix + "XML/getFileStore.xml";
		String respFileURL = localPathPrefix + "XML/getFileStoreResponse.xml";

		HashMap<String,String> hm = new HashMap<String,String>();
		String splitVal[] = reqSuffix.split("#");   
		for( int i = 0 ; i < splitVal.length ; i++ )
		{
			String temp = splitVal[i];
			hm.put(temp.substring(0,temp.indexOf("?")),temp.substring(temp.indexOf("?")+1,temp.length()));
		}

		String values[];
		String xpathArgs[];

		int len = splitVal.length ;
		values = new String[len];
		xpathArgs = new String[len];

		for( int i = 0 ; i < len ; i++ )
		{
			String arg = arguments.get(i).substring(0, arguments.get(i).indexOf(":"));
			values[i] = arguments.get(i).substring(arguments.get(i).indexOf(":")+1,arguments.get(i).length());
			xpathArgs[i] = reqPrefix+hm.get(arg);
		}

		try
		{
			sendSomaRequest ss = new sendSomaRequest();
			String finalURL = "https://"+ipAddress+"/service/mgmt/current";
			String soapReq = ss.createSoapReqTemp(reqFileURL, xpathArgs, values);
			String soapResponse = ss.sendRequestTemp(finalURL,soapReq,smObject.getUsername(),smObject.getPassword(),respFileURL);
			String respXpathExpr = respPrefix + respSuffix.substring(0,respSuffix.indexOf("#"));

			String fileStore[] = ss.parseResponseTemp(soapResponse,respXpathExpr);

			//System.out.println(fileStore[0]);
			String result="";

			for( int i = 0; i < fileStore.length ; i++ )
			{
				result+=fileStore[i]+"#";
			}

			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/getCertStore", method = RequestMethod.GET )
	public @ResponseBody 
	String getCerts(@RequestParam("arguments") List<String> arguments,  @Value("${getCertStoreREQ}") String reqSuffix, @Value("${getCertStoreRESP}") String respSuffix) {

		String ipAddress = smObject.getIpAdd().substring(0,smObject.getIpAdd().indexOf("-"));
		String reqFileURL = localPathPrefix + "XML/getCertStore.xml";
		String respFileURL = localPathPrefix + "XML/getCertStoreResponse.xml";

		HashMap<String,String> hm = new HashMap<String,String>();
		String splitVal[] = reqSuffix.split("#");   
		for( int i = 0 ; i < splitVal.length ; i++ )
		{
			String temp = splitVal[i];
			hm.put(temp.substring(0,temp.indexOf("?")),temp.substring(temp.indexOf("?")+1,temp.length()));
		}

		String values[];
		String xpathArgs[];

		int len = splitVal.length ;
		values = new String[len];
		xpathArgs = new String[len];

		for( int i = 0 ; i < len ; i++ )
		{
			String arg = arguments.get(i).substring(0, arguments.get(i).indexOf(":"));
			values[i] = arguments.get(i).substring(arguments.get(i).indexOf(":")+1,arguments.get(i).length());
			xpathArgs[i] = reqPrefix+hm.get(arg);
		}

		try
		{
			sendSomaRequest ss = new sendSomaRequest();
			String finalURL = "https://"+ipAddress+"/service/mgmt/current";
			String soapReq = ss.createSoapReqTemp(reqFileURL, xpathArgs, values);
			String soapResp = ss.sendRequestTemp(finalURL,soapReq,smObject.getUsername(),smObject.getPassword(),respFileURL);
			String respXpathExpr = respPrefix + respSuffix.substring(0,respSuffix.indexOf("#"));

			String certStore[] = ss.parseResponseTemp(soapResp,respXpathExpr);
			String result="";

			for( int i = 0; i < certStore.length ; i++ )
			{
				result+=certStore[i]+"#";
			}
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/getValCreds", method = RequestMethod.GET )
	public @ResponseBody 
	String getValCreds(@RequestParam("arguments") List<String> arguments,  @Value("${getValCredREQ}") String reqSuffix, @Value("${getValCredRESP}") String respSuffix ) {

		String ipAddress = smObject.getIpAdd().substring(0,smObject.getIpAdd().indexOf("-"));
		String reqFileURL = localPathPrefix + "XML/getValCred.xml";
		String respFileURL = localPathPrefix + "XML/getValCredResponse.xml";

		HashMap<String,String> hm = new HashMap<String,String>();
		String splitVal[] = reqSuffix.split("#");   
		for( int i = 0 ; i < splitVal.length ; i++ )
		{
			String temp = splitVal[i];
			hm.put(temp.substring(0,temp.indexOf("?")),temp.substring(temp.indexOf("?")+1,temp.length()));
		}

		String values[];
		String xpathArgs[];

		int len = splitVal.length ;
		values = new String[len];
		xpathArgs = new String[len];

		for( int i = 0 ; i < len ; i++ )
		{
			String arg = arguments.get(i).substring(0, arguments.get(i).indexOf(":"));
			values[i] = arguments.get(i).substring(arguments.get(i).indexOf(":")+1,arguments.get(i).length());
			xpathArgs[i] = reqPrefix+hm.get(arg);
		}

		try
		{
			sendSomaRequest ss = new sendSomaRequest();
			String finalURL = "https://"+ipAddress+"/service/mgmt/current";
			String soapReq = ss.createSoapReqTemp(reqFileURL, xpathArgs, values);
			String soapResp = ss.sendRequestTemp(finalURL,soapReq,smObject.getUsername(),smObject.getPassword(),respFileURL);
			String respXpathExpr = respPrefix + respSuffix.substring(0,respSuffix.indexOf("#"));
			String valCreds[] = ss.parseResponseTemp(soapResp,respXpathExpr);
			String result="";
			String certObjStr="";
			
			for( int i = 0; i < valCreds.length ; i++ )
			{
				
				String certXPath = respXpathExpr.substring(0,respXpathExpr.lastIndexOf("/")) + "[@name=\"" +valCreds[i]+"\"]/Certificate/text()";
				//System.out.println(certXPath);
				String certObjs[] = ss.parseResponseTemp(soapResp,certXPath);
				//System.out.println(certObjs[0]);
				for( int j = 0; j < certObjs.length ; j++ )
				{
					certObjStr += certObjs[j] + ":"; 
				}
				result+=valCreds[i]+"="+certObjStr+"#";
				certObjStr="";
			}
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/fileUploadSubmit", method = RequestMethod.POST )
	public @ResponseBody  
	String getFileUploadStatus(@RequestParam("arguments") List<String> arguments,  @Value("${uploadFileREQ}") String reqSuffix, @Value("${uploadFileRESP}") String respSuffix)
	{
		String ipAddress = smObject.getIpAdd().substring(0,smObject.getIpAdd().indexOf("-"));
		String reqFileURL = localPathPrefix + "XML/uploadFile.xml";
		String respFileURL = localPathPrefix + "XML/uploadFileResponse.xml";
		sendSomaRequest ss = new sendSomaRequest();

		HashMap<String,String> hm = new HashMap<String,String>();

		String splitVal[] = reqSuffix.split("#");   
		for( int i = 0 ; i < splitVal.length ; i++ )
		{
			String temp = splitVal[i];
			hm.put(temp.substring(0,temp.indexOf("?")),temp.substring(temp.indexOf("?")+1,temp.length()));
		}

		String values[];
		String xpathArgs[];
		
		int len = splitVal.length ;
		values = new String[len];
		xpathArgs = new String[len];

		try
		{   
			String arg[] = arguments.get(0).split("@");
			int size = arg.length - 1;
			byte binData[] = arg[size].substring(arg[size].indexOf(":")+1).getBytes();
			String encodedString = Base64.encodeBase64String(binData);
			arg[size] = "fileContent:"+encodedString;

			for( int i = 0 ; i < arg.length ; i++ )
			{
				values[i] = arg[i].substring(arg[i].indexOf(":")+1);
				xpathArgs[i] = reqPrefix + hm.get(arg[i].substring(0,arg[i].indexOf(":")));
			}
			String finalURL = "https://"+ipAddress+"/service/mgmt/current";
			String soapReq = ss.createSoapReqTemp(reqFileURL,xpathArgs,values);
			String soapResp = ss.sendRequestTemp(finalURL,soapReq,smObject.getUsername(),smObject.getPassword(),respFileURL);
			String respXpathExpr = respPrefix + respSuffix.substring(0,respSuffix.indexOf("#"));
			String result[] = ss.parseResponseTemp(soapResp,respXpathExpr);
			return result[0];
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/mulFileUploadSubmit", method = RequestMethod.POST )
	public @ResponseBody  
	String getMULFileUploadStatus(@RequestParam("arguments") List<String> arguments,  @Value("${uploadFileREQ}") String reqSuffix, @Value("${uploadFileRESP}") String respSuffix)
	{
		String ipAddress = smObject.getIpAdd().substring(0,smObject.getIpAdd().indexOf("-"));
		String reqFileURL = localPathPrefix + "XML/uploadFile.xml";
		String respFileURL = localPathPrefix + "XML/uploadFileResponse.xml";
		sendSomaRequest ss = new sendSomaRequest();

		HashMap<String,String> hm = new HashMap<String,String>();

		String splitVal[] = reqSuffix.split("#");   
		for( int i = 0 ; i < splitVal.length ; i++ )
		{
			String temp = splitVal[i];
			hm.put(temp.substring(0,temp.indexOf("?")),temp.substring(temp.indexOf("?")+1,temp.length()));
		}

		String values[];
		String xpathArgs[];

		int len = splitVal.length ;
		values = new String[len];
		xpathArgs = new String[len];

		try
		{   
			String arg[] = arguments.get(0).split("<EasyDPARG>");
			int size = arg.length - 1;
			String inputFiles[] = arg[size].substring(arg[size].indexOf(":")+1).split("<EOF>");
			//System.out.println(inputFiles[2]);
			int numFiles = inputFiles.length - 1;
			//System.out.println(numFiles);
			String finEncodedString="";
			for( int i = 0 ; i <= numFiles ; i++ ) 
			{
				byte binData[] = inputFiles[i].getBytes();
				finEncodedString += Base64.encodeBase64String(binData) + "<EOF>";
			}
			//System.out.println(finEncodedString);
			arg[size] = "fileContent:"+ finEncodedString;
			
			//System.out.println(finEncodedString);

			for( int i = 0 ; i < arg.length ; i++ )
			{
				//System.out.println(arg[i]);
				values[i] = arg[i].substring(arg[i].indexOf(":")+1);
				xpathArgs[i] = reqPrefix + hm.get(arg[i].substring(0,arg[i].indexOf(":")));
			}
			
			//System.out.println("Before Calling SOAP");
			String finalURL = "https://"+ipAddress+"/service/mgmt/2004";
			//ss.createSoapReq(reqFileURL,xpathArgs,values);
			String soapReq = ss.createSoapReqMulti(reqFileURL,xpathArgs,values);
			String soapResp = ss.sendRequestTemp(finalURL,soapReq,smObject.getUsername(),smObject.getPassword(),respFileURL);
			String respXpathExpr = respPrefix + respSuffix.substring(0,respSuffix.indexOf("#"));
			String result[] = ss.parseResponseTemp(soapResp,respXpathExpr);
			return result[0];
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/cryptoCertUpdateSubmit", method = RequestMethod.GET )
	public @ResponseBody 
	String getCertUpdateStatus(@RequestParam("arguments") List<String> arguments,  @Value("${createCrypCertREQ}") String reqSuffix, @Value("${createCrypCertRESP}") String respSuffix) {

		String ipAddress = smObject.getIpAdd().substring(0,smObject.getIpAdd().indexOf("-"));
		String reqFileURL = localPathPrefix + "XML/createCryptoCertificate.xml";
		String respFileURL = localPathPrefix + "XML/createCryptoCertificateResponse.xml";
		HashMap<String,String> hm = new HashMap<String,String>();
		String splitVal[] = reqSuffix.split("#");   
		int len = splitVal.length;
		
		for( int i = 0 ; i < len ; i++ )
		{
			String temp = splitVal[i];
			hm.put(temp.substring(0,temp.indexOf("?")),temp.substring(temp.indexOf("?")+1,temp.length()));
		}

		String values[];
		String xpathArgs[];
		
		values = new String[len];
		xpathArgs = new String[len];
		String arg[] = arguments.get(0).split("<EasyDPARG>");

		for( int i = 0 ; i < len ; i++ )
		{
			values[i] = arg[i].substring(arg[i].indexOf(":")+1);
			xpathArgs[i] = reqPrefix + hm.get(arg[i].substring(0,arg[i].indexOf(":")));
		}

		try
		{
			sendSomaRequest ss = new sendSomaRequest();
			String finalURL = "https://"+ipAddress+"/service/mgmt/current";
			String soapReq = ss.createSoapReqTemp(reqFileURL, xpathArgs, values);
			String soapResp = ss.sendRequestTemp(finalURL,soapReq,smObject.getUsername(),smObject.getPassword(),respFileURL);
			String respXpathExpr = respPrefix + respSuffix.substring(0,respSuffix.indexOf("#"));
			String result[] = ss.parseResponseTemp(soapResp,respXpathExpr);
			saveConfig(values[0]);
			doRestart(values[0]);
			return result[0];
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/getDomainStatus", method = RequestMethod.GET )
	public @ResponseBody  
	String getDomainStatus(@RequestParam("arguments") List<String> arguments, @Value("${checkDomainStatusRESP}") String respSuffixes)
	{
		String ipAddress = smObject.getIpAdd().substring(0,smObject.getIpAdd().indexOf("-"));
		String reqFileURL = localPathPrefix + "XML/getDomainStatus.xml";
		String respFileURL = localPathPrefix + "XML/getDomainStatusResponse.xml";
		sendSomaRequest ss = new sendSomaRequest();

		try
		{ 
			String finalURL = "https://"+ipAddress+"/service/mgmt/current";
			String soapReq = ss.createSoapReqTemp(reqFileURL,null,null);
			String soapResp = ss.sendRequestTemp(finalURL,soapReq,smObject.getUsername(),smObject.getPassword(),respFileURL);
			String respXpathExprForDomainName = respPrefix + respSuffixes.substring(0,respSuffixes.indexOf("#"));
			String respXpathExprForState = respPrefix + respSuffixes.substring(respSuffixes.indexOf("#")+1);
			String Domains[] = ss.parseResponseTemp(soapResp,respXpathExprForDomainName);
			String States[] = ss.parseResponseTemp(soapResp,respXpathExprForState);
			String result = "";
			for( int i = 0;  i < Domains.length; i++){
				if(States[i].equals("enabled"))
					States[i] = "UP";
				else
					States[i] = "DOWN";
				result = result + Domains[i] + ":" + States[i] + "#";
			}
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/getDomainHealth", method = RequestMethod.GET )
	public @ResponseBody  
	String getDomainHealth(@RequestParam("arguments") List<String> arguments, @Value("${getDomainHealthREQ}") String reqSuffix, @Value("${getDomainHealthRESP}") String respSuffixes)
	{
		String ipAddress = smObject.getIpAdd().substring(0,smObject.getIpAdd().indexOf("-"));
		String domains[] = smObject.getDomains();
		int numDomains = domains.length;
		String reqFileURL = localPathPrefix + "XML/getDomainHealth.xml";
		String respFileURL = localPathPrefix + "XML/getDomainHealthResponse.xml";
		sendSomaRequest ss = new sendSomaRequest();

		String values[] = new String [1];
		String xpathArgs [] = new String[1];
		xpathArgs[0] = reqPrefix + reqSuffix;
		
		String args[] = arguments.get(0).split("#");
		int first = Integer.parseInt(args[0].substring(args[0].indexOf(":") + 1));
		int last = Integer.parseInt(args[1].substring(args[1].indexOf(":") + 1));
		System.out.println(first + " : " + last + " : " + numDomains);
		if (last > numDomains)
			last = numDomains;
		
		String result = "";
		
		try
		{ 
			String finalURL = "https://"+ipAddress+"/service/mgmt/current";
			for (int i = first; i <= last; i++)
			{	
				//if (i < 1)
					//continue;
				values[0] = domains[i];
				String soapReq = ss.createSoapReqTemp(reqFileURL,xpathArgs,values);
				System.setProperty("https.protocols", "TLSv1.1");
				String soapResp = ss.sendRequestTemp(finalURL,soapReq,smObject.getUsername(),smObject.getPassword(),respFileURL);
				String respXpathExprForFSHName = respPrefix + respSuffixes.substring(0,respSuffixes.indexOf("#"));
				String respXpathExprForFSHOpState = respPrefix + respSuffixes.substring(respSuffixes.indexOf("#")+1);
				String HTTPSFSHs[] = ss.parseResponseTemp(soapResp,respXpathExprForFSHName);
				String FSHOpStates[] = ss.parseResponseTemp(soapResp,respXpathExprForFSHOpState);
				result += domains[i] + "<";
				for (int j = 0; j < HTTPSFSHs.length; j++)
				{
					result = result + HTTPSFSHs[j] +":" + FSHOpStates[j] + ",";
				}
				result = result + ">";
				System.out.println(i + " : " + domains[i]);
				//if (i == 20)
					//break;
			}
			return result;			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	void saveConfig( String domain )
	{
		String ipAddress = smObject.getIpAdd().substring(0,smObject.getIpAdd().indexOf("-"));
		String reqFileURL = localPathPrefix + "XML\\domainSaveConfig.xml";
		String respFileURL = localPathPrefix + "XML\\domainSaveConfigResponse.xml";
		String values[] = new String[1];
		String xpathArgs[] = new String[1];
		
		xpathArgs[0] = reqPrefix + saveReqSuffix.substring(0,saveReqSuffix.indexOf("#"));   
		values[0] = domain;

		try
		{
			sendSomaRequest ss = new sendSomaRequest();
			String finalURL = "https://"+ipAddress+"/service/mgmt/current";
			String soapReq = ss.createSoapReqTemp(reqFileURL, xpathArgs, values);
			String soapResp = ss.sendRequestTemp(finalURL,soapReq,smObject.getUsername(),smObject.getPassword(),respFileURL);
			String respXpathExpr = respPrefix + saveRespSuffix.substring(0,saveRespSuffix.indexOf("#"));
			ss.parseResponseTemp(soapResp,respXpathExpr);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	void doRestart( String domain )
	{
		String ipAddress = smObject.getIpAdd().substring(0,smObject.getIpAdd().indexOf("-"));
		String reqFileURL = localPathPrefix + "XML\\domainRestart.xml";
		String respFileURL = localPathPrefix + "XML\\domainRestartResponse.xml";
		String values[] = new String[1];
		String xpathArgs[] = new String[1];
		
		xpathArgs[0] = reqPrefix + restartReqSuffix.substring(0,restartReqSuffix.indexOf("#"));   
		values[0] = domain;

		try
		{
			sendSomaRequest ss = new sendSomaRequest();
			String finalURL = "https://"+ipAddress+"/service/mgmt/current";
			String soapReq = ss.createSoapReqTemp(reqFileURL, xpathArgs, values);
			String soapResp = ss.sendRequestTemp(finalURL,soapReq,smObject.getUsername(),smObject.getPassword(),respFileURL);
			String respXpathExpr = respPrefix + restartRespSuffix.substring(0,restartRespSuffix.indexOf("#"));
			ss.parseResponseTemp(soapResp,respXpathExpr);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}