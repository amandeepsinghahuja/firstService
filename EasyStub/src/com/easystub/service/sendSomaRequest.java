package com.easystub.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.annotation.Resource;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;        //check for this library
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.easystub.controller.SomaController;

/**
 *
 * @author 415907
 */
public class sendSomaRequest{

	static
	{
		try{

			TrustManager[] trustAllCerts = { new X509TrustManager() {

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs,
						String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs,
						String authType) {
				}
			} };
			SSLContext sc = SSLContext.getInstance("SSL");

			HostnameVerifier hv = new HostnameVerifier() {
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			};
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
		}catch(Exception exception){
			System.err.println(exception);
		}
	}
	public void write2File(String content,String fileName) throws IOException
	{
		//System.out.println(content);
		File file = new File(fileName);

		// if file doesn't exists, then create it

		if (!file.exists()) 
		{
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();

	}
	
	public void write2StubFile(String configPath,String stubURI,String content,String stubFilePath) throws IOException, URISyntaxException
	{
		//Update Config File 
		try {
			String newpath = "E:\\Soma Deployment\\EasyStub\\WebContent\\WEB-INF\\stubs\\StubConfig.xml";
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = dBuilder.parse(newpath);
			doc.normalize();

			//System.out.println(doc.getElementsByTagName("st:respFile").item(0).getNodeName());
			Node newStub = doc.getElementsByTagName("Stub").item(0).cloneNode(true);
			((org.w3c.dom.Element)newStub).setAttribute("uri",stubURI);
			NodeList n = newStub.getChildNodes();
			for ( int i = 0; i < n.getLength(); i++)
			{
				if (  n.item(i).getNodeName().toString() == "respFile")
				{
					n.item(i).setTextContent(stubFilePath);
				}
			}
			doc.getDocumentElement().appendChild(newStub);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			//System.out.println(doc.getDocumentElement().getChildNodes().item(0).getTextContent());
			//StreamResult result = new StreamResult(new File(filePath));
			Writer out = new StringWriter();
			StreamResult result = new StreamResult(out);
			transformer.transform(source, result);
			write2File(out.toString(),configPath);
			write2File(content,stubFilePath);
		} 
		catch (Exception ex) 
		{
			System.out.println(ex.getMessage());
		}
		// Upload Stub Resp File
	}

	
	public String base64Encode(String filePath) throws IOException
	{

		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();

		String encodedString = Base64.encodeBase64String(data);

		return encodedString;
	}

	public void extractContent(String fXmlFile,String filename) throws IOException
	{
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList n = doc.getElementsByTagName("dp:file");


			for(int i = 0 ;i < n.getLength() ; i++ )
			{
				write2File(n.item(i).getTextContent(),filename);
			}
		} 
		catch (Exception ex) 
		{
			System.out.println(ex.getMessage());
		}
	}

	public void createSoapReq(String filePath,String xpathArgs[], String values[])
	{		
		try
		{
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = dBuilder.parse(filePath);
			doc.normalize();
			XPathFactory factory = XPathFactory.newInstance();
			XPath xPath = factory.newXPath();
			XPathExpression expr;

			if( values[xpathArgs.length - 1 ].contains("<EOF>"))
			{
				for( int i = 0; i < xpathArgs.length - 1 ; i++ )
				{
					expr = xPath.compile(xpathArgs[i]);
					NodeList n = (NodeList) expr.evaluate(doc,XPathConstants.NODESET);
					n.item(0).setTextContent(values[i]);
				}
				expr = xPath.compile(xpathArgs[xpathArgs.length - 1]);
				NodeList fileNode = (NodeList) expr.evaluate(doc,XPathConstants.NODESET);
				String files[] = values[xpathArgs.length - 1].split("<EOF>");
				fileNode.item(0).setTextContent(files[0]);
				for ( int i = 1 ; i < files.length ; i++ )
				{
					Node newChild = doc.createElement("file"+i);
					newChild.setTextContent(files[i]);
					fileNode.item(0).getParentNode().appendChild(newChild);
				}
			}
			else
			{
				for( int i = 0; i < xpathArgs.length ; i++ )
				{
					expr = xPath.compile(xpathArgs[i]);
					NodeList n = (NodeList) expr.evaluate(doc,XPathConstants.NODESET);
					n.item(0).setTextContent(values[i]);
				}
			}
			//Transform SOAP Request
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			//System.out.println(doc.getDocumentElement().getChildNodes().item(0).getTextContent());
			//StreamResult result = new StreamResult(new File(filePath));
			Writer out = new StringWriter();
			StreamResult result = new StreamResult(out);
			transformer.transform(source, result);
			//System.out.println(out.toString());

		}

		catch(Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public String createSoapReqTemp(String filePath,String xpathArgs[], String values[]) throws IOException
	{
		String outReq ="";
		
		if( xpathArgs == null )
		{
			ClassPathResource resource = new ClassPathResource(filePath);
			BufferedReader in = new BufferedReader(new InputStreamReader(resource.getInputStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null){
				outReq = outReq+inputLine;
			}
			System.out.println("FILE TEXT :"+outReq);
		}
		else
		{
			try
			{
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				ClassPathResource resource = new ClassPathResource(filePath);
				org.w3c.dom.Document doc = dBuilder.parse(resource.getFile());
				doc.normalize();
				XPathFactory factory = XPathFactory.newInstance();
				XPath xPath = factory.newXPath();
				XPathExpression expr;
	
				if( values[xpathArgs.length - 1 ].contains("<EOF>"))
				{
					for( int i = 0; i < xpathArgs.length - 1 ; i++ )
					{
						expr = xPath.compile(xpathArgs[i]);
						NodeList n = (NodeList) expr.evaluate(doc,XPathConstants.NODESET);
						n.item(0).setTextContent(values[i]);
					}
					expr = xPath.compile(xpathArgs[xpathArgs.length - 1]);
					NodeList fileNode = (NodeList) expr.evaluate(doc,XPathConstants.NODESET);
					String files[] = values[xpathArgs.length - 1].split("<EOF>");
					fileNode.item(0).setTextContent(files[0]);
					for ( int i = 1 ; i < files.length ; i++ )
					{
						Node newChild = doc.createElement("file"+i);
						newChild.setTextContent(files[i]);
						fileNode.item(0).getParentNode().appendChild(newChild);
					}
				}
				else
				{
					for( int i = 0; i < xpathArgs.length ; i++ )
					{
						expr = xPath.compile(xpathArgs[i]);
						NodeList n = (NodeList) expr.evaluate(doc,XPathConstants.NODESET);
						n.item(0).setTextContent(values[i]);
					}
				}
				//Transform SOAP Request
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				Writer out = new StringWriter();
				StreamResult result = new StreamResult(out);
				transformer.transform(source, result);
				outReq = out.toString();
			}
	
			catch(Exception e )
			{
				e.printStackTrace();
			}
		}
		return outReq;
	}
	public String createSoapReqMulti(String filePath,String xpathArgs[], String values[])
	{
		String outReq="";
		try
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			//Change Start
			ClassPathResource resource = new ClassPathResource(filePath);
			org.w3c.dom.Document doc = dBuilder.parse(resource.getFile());
			doc.normalize();
			XPathFactory factory = XPathFactory.newInstance();
			XPath xPath = factory.newXPath();
			XPathExpression expr;
			XPathExpression exprName;
			XPathExpression exprFile;
			for( int i = 0; i < 1 ; i++ )
			{
				expr = xPath.compile(xpathArgs[i]);				
				NodeList n = (NodeList) expr.evaluate(doc,XPathConstants.NODESET);
				n.item(0).setTextContent(values[i]);
			}
			//System.out.println("INSIDE soma");
			//System.out.println(values[2]);
			String splitNames[] = values[1].split("<EOF>");
			String splitValues[] = values[2].split("<EOF>");
			//System.out.println(splitNames.length);
			//System.out.println(splitValues.length);
			
			String nameXpath = xpathArgs[1];
			String fileXpath = xpathArgs[2];
			NodeList parent = doc.getElementsByTagName("dp:request");
			for( int j = 0 ; j < splitNames.length ; j++ )
			{
				//Set File Name
				exprName = xPath.compile(nameXpath.substring(0,nameXpath.indexOf('@')-1)+"["+(j+1)+"]/"+nameXpath.substring(nameXpath.indexOf('@')));
				NodeList nameNode = (NodeList) exprName.evaluate(doc,XPathConstants.NODESET);
				nameNode.item(0).setTextContent(splitNames[j]);
				//Set File Content
				exprFile = xPath.compile(fileXpath+"["+(j+1)+"]");
				NodeList fileNode = (NodeList) exprFile.evaluate(doc,XPathConstants.NODESET);
				fileNode.item(0).setTextContent(splitValues[j]);
				
				//Add new set-file node ( Accommodating for one already present )
				if ( j < splitNames.length - 1 )
				{
					Node n = doc.getElementsByTagName("dp:set-file").item(0).cloneNode(false);
					parent.item(0).appendChild(n);
				}
			}
			
			//Transform SOAP Request
	/*		TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filePath.substring(0,filePath.indexOf('.'))+"Multi"+filePath.substring(filePath.indexOf('.'))));
			transformer.transform(source, result);*/
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			Writer out = new StringWriter();
			StreamResult result = new StreamResult(out);
			transformer.transform(source, result);
			outReq = out.toString();
			
		}

		catch(Exception e )
		{
			System.out.println("Inside Catch");
			e.printStackTrace();
		}
		return outReq;
	}
	public void sendRequest(String pUrl, String pXmlFile2Send, String pUsername, String pPassword, String respFileURL) throws Exception
	{
		String SOAPUrl      = pUrl;
		String xmlFile2Send = pXmlFile2Send;
		String SOAPAction = "";


		// Create the connection where we're going to send the file.
		URL url = new URL(SOAPUrl);
		URLConnection connection = url.openConnection();
		HttpsURLConnection httpConn = (HttpsURLConnection) connection;

		// Open the input file. After we copy it to a byte array, we can see
		// how big it is so that we can set the HTTP Content-Length
		// property. (See complete e-mail below for more on this.)
		FileInputStream fin = new FileInputStream(xmlFile2Send);
		ByteArrayOutputStream bout = new ByteArrayOutputStream();

		// Copy the SOAP file to the open connection.
		copy(fin,bout);
		fin.close();

		//Replace domainName in Request
		String soapRequest = bout.toString();
		//System.out.println(soapRequest);

		//Convert into bytes
		byte[] b = soapRequest.getBytes();

		// Set the appropriate HTTP parameters.
		httpConn.setRequestProperty( "Content-Length",String.valueOf( b.length ) );
		httpConn.setRequestProperty("Content-Type","text/xml; charset=utf-8");
		httpConn.setRequestProperty("SOAPAction",SOAPAction);

		//Create UsernamePassword 
		//To Base64 decoding, Apache common-codec is used.
		String authString = pUsername + ":" + pPassword;
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);
		httpConn.setRequestProperty("Authorization", "Basic " + authStringEnc);

		//httpConn.setRequestProperty("Authorization","Basic Z295YWxyYWRtaW46VHJhbnNmZXIxMiM=");
		httpConn.setRequestMethod( "POST" );
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);

		// Everything's set up; send the XML that was read in to b.
		OutputStream out = httpConn.getOutputStream();
		out.write(b);    
		out.close();

		// Read the response and write it to standard out.
		InputStreamReader isr =    new InputStreamReader(httpConn.getInputStream());
		BufferedReader in = new BufferedReader(isr);

		String inputLine;
		String output = "";
		while ((inputLine = in.readLine()) != null){
			output = output+inputLine;
		}

		in.close();

		File file = new File(respFileURL);


		if (!file.exists()) 
		{
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(output);
		bw.close();
	}

	public static void copy(InputStream in, OutputStream out) throws IOException 
	{
		// do not allow other threads to read from the
		// input or write to the output while copying is
		// taking place
		synchronized (in) {
			synchronized (out) {

				byte[] buffer = new byte[256];
				while (true) {
					int bytesRead = in.read(buffer);
					if (bytesRead == -1) break;
					out.write(buffer, 0, bytesRead);
				}
			}
		}
	}
	
	public String sendRequestTemp(String pUrl, String request, String pUsername, String pPassword, String respFileURL) throws Exception
	{
		String SOAPUrl      = pUrl;
		String xmlFile2Send = request;
		String SOAPAction = "";


		// Create the connection where we're going to send the file.
		URL url = new URL(SOAPUrl);
		URLConnection connection = url.openConnection();
		HttpsURLConnection httpConn = (HttpsURLConnection) connection;

		// Open the input file. After we copy it to a byte array, we can see
		// how big it is so that we can set the HTTP Content-Length
		// property. (See complete e-mail below for more on this.)
		/*FileInputStream fin = new FileInputStream(xmlFile2Send);
		ByteArrayOutputStream bout = new ByteArrayOutputStream();

		// Copy the SOAP file to the open connection.
		copy(fin,bout);
		fin.close();

		//Replace domainName in Request*/
		String soapRequest = request;

		//Convert into bytes
		byte[] b = soapRequest.getBytes();

		// Set the appropriate HTTP parameters.
		httpConn.setRequestProperty( "Content-Length",String.valueOf( b.length ) );
		httpConn.setRequestProperty("Content-Type","text/xml; charset=utf-8");
		httpConn.setRequestProperty("SOAPAction",SOAPAction);

		//Create UsernamePassword 
		//To Base64 decoding, Apache common-codec is used.
		String authString = pUsername + ":" + pPassword;
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);
		httpConn.setRequestProperty("Authorization", "Basic " + authStringEnc);

		//httpConn.setRequestProperty("Authorization","Basic Z295YWxyYWRtaW46VHJhbnNmZXIxMiM=");
		httpConn.setRequestMethod( "POST" );
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);

		// Everything's set up; send the XML that was read in to b.
		OutputStream out = httpConn.getOutputStream();
		out.write(b);    
		out.close();

		// Read the response and write it to standard out.
		InputStreamReader isr =    new InputStreamReader(httpConn.getInputStream());
		BufferedReader in = new BufferedReader(isr);

		String inputLine;
		String output = "";
		while ((inputLine = in.readLine()) != null){
			output = output+inputLine;
		}

		in.close();
		
		return output;

		/*File file = new File(respFileURL);


		if (!file.exists()) 
		{
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(output);
		bw.close();*/
	}


	//Display Local file list from server
	public String[] parseResponse(String fileURL, String xpathExpr ) 
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(fileURL);
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression expr = xpath.compile(xpathExpr);
			NodeList nl = (NodeList)expr.evaluate(doc,XPathConstants.NODESET);
			int i = 0;
			String s[] = new String[nl.getLength()];
			while ( i < nl.getLength() )
			{
				s[i] =  nl.item(i++).getNodeValue();
			}
			return s;
		}
		catch( Exception e )
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public String[] parseResponseTemp(String response, String xpathExpr ) 
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(response));
			Document doc = builder.parse(is);
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression expr = xpath.compile(xpathExpr);
			NodeList nl = (NodeList)expr.evaluate(doc,XPathConstants.NODESET);
			int i = 0;
			String s[] = new String[nl.getLength()];
			while ( i < nl.getLength() )
			{
				s[i] =  nl.item(i++).getNodeValue();
			}
			return s;
		}
		catch( Exception e )
		{
			System.out.println(e.getMessage());
		}
		return null;
	}

}