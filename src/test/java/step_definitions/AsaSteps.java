package step_definitions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@SuppressWarnings("restriction")
public class AsaSteps {

	static String responce;
	static Path currentRelativePath = Paths.get("");
	static String currentPath = currentRelativePath.toAbsolutePath().toString();
	static SOAPMessage soapResponse;
	static String createVersionxml;
	static String clientId;
	static String editorialVersionId;

	@Given("^I create an Editorial Version$")
	public void setEditorialVersion() throws Exception {
		String url = "http://ukwcc4jb4d03:8080/assetAuthority/EditorialVersionManagementService";
		createVersionxml = readFile(currentPath
				+ "/src/test/resources/testdata/createEditorialVersion.xml",
				StandardCharsets.UTF_8);
		replaceClientIdAndEditorialID();
		soapResponse = makeSoapRequestCall(url, createVersionxml);
		responce = printSOAPResponse(soapResponse);
	}

	@When("^I make a get request for that Editorial Version$")
	public void getEditorialVersion() throws Exception {

		// Send SOAP Message to SOAP Server
		String url = "http://ukwcc4jb4d03:8080/assetAuthority/EditorialVersionManagementService";

		createVersionxml = readFile(currentPath
				+ "/src/test/resources/testdata/getEditorialVersion.xml",
				StandardCharsets.UTF_8);
		replaceGetRequestDetails();
		soapResponse = makeSoapRequestCall(url, createVersionxml);
		responce = printSOAPResponse(soapResponse);

	}

	@Then("^I should get the Editorial Version with the required details$")
	public void verifyEditorialVersion() {
		// verify responce
		Assert.assertTrue(responce.contains("<clientExtIdentifier>"
				+ editorialVersionId + "</clientExtIdentifier>"));
		Assert.assertTrue(responce.contains("<clientID>" + clientId
				+ "</clientID>"));
		Assert.assertTrue(responce
				.contains("<editorialRevisionNumber>22</editorialRevisionNumber>"));
		Assert.assertTrue(responce
				.contains("<additionalClockInfo>No</additionalClockInfo>"));
		Assert.assertTrue(responce
				.contains("<audioDescriptionLanguage>Yes</audioDescriptionLanguage>"));
		Assert.assertTrue(responce
				.contains("<description>TestDescription</description>"));
		Assert.assertTrue(responce
				.contains("<editorialVersionDescription>TestEditorialVersionDes</editorialVersionDescription>"));
		Assert.assertTrue(responce.contains("<guidance>YesGuidance</guidance>"));
		Assert.assertTrue(responce.contains("<mediaSet>YesMediaSet</mediaSet>"));
		Assert.assertTrue(responce.contains("<notes>Notes</notes>"));
		Assert.assertTrue(responce.contains("<title>TestTitle</title>"));
		Assert.assertTrue(responce
				.contains("<txAnnouncementRecommended>YesRecommended</txAnnouncementRecommended>"));
		Assert.assertTrue(responce.contains("<warning>NoWarning</warning>"));

	}

	@Given("^I have data for new editorial version$")
	public void generateEditorialVersionData() throws IOException {

		createVersionxml = readFile(currentPath
				+ "/src/test/resources/testdata/createEditorialVersion.xml",
				StandardCharsets.UTF_8);
		genereateNewClientIDandVersionID();

		replaceClientIdAndEditorialID();

	}

	private void replaceClientIdAndEditorialID() {
		Pattern p1 = Pattern.compile("<clientID>TestC10</clientID>");
		Pattern p2 = Pattern
				.compile("<clientExtIdentifier>TestV10</clientExtIdentifier>");
		createVersionxml = p1.matcher(createVersionxml).replaceAll(
				"<clientID>" + clientId + "</clientID>");
		createVersionxml = p2.matcher(createVersionxml).replaceAll(
				"<clientExtIdentifier>" + editorialVersionId
						+ "</clientExtIdentifier>");
	}

	private void genereateNewClientIDandVersionID() {
		String uniqueID = UUID.randomUUID().toString();
		clientId = "TestC" + uniqueID;
		editorialVersionId = "TestV" + uniqueID;
	}

	private void replaceGetRequestDetails() {
		Pattern p1 = Pattern.compile("<clientID>TestC10</clientID>");
		Pattern p2 = Pattern
				.compile("<clientEditorialVersionID>TestV10</clientEditorialVersionID>");
		createVersionxml = p1.matcher(createVersionxml).replaceAll(
				"<clientID>" + clientId + "</clientID>");
		createVersionxml = p2.matcher(createVersionxml).replaceAll(
				"<clientEditorialVersionID>" + editorialVersionId
						+ "</clientEditorialVersionID>");
	}

	@When("^I make a create request for new editorial version$")
	public void callCreateEditorialVersion() throws Exception {

		// Send SOAP Message to SOAP Server
		String url = "http://ukwcc4jb4d03:8080/assetAuthority/EditorialVersionManagementService";

		soapResponse = makeSoapRequestCall(url, createVersionxml);
		responce = printSOAPResponse(soapResponse);
	}

	@Then("^I should get response with created action$")
	public void verifyCreateEditorialVersion() {
		Assert.assertTrue(responce
				.contains("<actionTaken>CREATED</actionTaken>"));
	}

	public static SOAPMessage getSoapMessageFromString(String xml)
			throws SOAPException, IOException {
		MessageFactory factory = MessageFactory.newInstance();
		SOAPMessage message = factory
				.createMessage(
						new MimeHeaders(),
						new ByteArrayInputStream(xml.getBytes(Charset
								.forName("UTF-8"))));
		return message;
	}

	private static SOAPMessage makeSoapRequestCall(String url, String xml)
			throws SOAPException, IOException {
		SOAPConnectionFactory soapConnectionFactory;
		SOAPMessage soapResponse;
		soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory
				.createConnection();

		// Send SOAP Message to SOAP Server
		soapResponse = soapConnection.call(getSoapMessageFromString(xml), url);
		return soapResponse;
	}

	/**
	 * Method used to print the SOAP Response
	 */
	private static String printSOAPResponse(SOAPMessage soapResponse)
			throws Exception {
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		Source sourceContent = soapResponse.getSOAPPart().getContent();
		StreamResult result = new StreamResult(System.out);
		transformer.transform(sourceContent, result);

		StringWriter writer = new StringWriter();
		StreamResult result1 = new StreamResult(writer);
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer1 = tFactory.newTransformer();
		transformer1.transform(sourceContent, result1);
		return writer.toString();
	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

}
