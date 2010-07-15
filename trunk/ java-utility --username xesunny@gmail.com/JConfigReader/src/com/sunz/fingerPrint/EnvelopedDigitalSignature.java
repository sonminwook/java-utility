package com.sunz.fingerPrint;


import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.spec.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.crypto.dsig.keyinfo.*;
import java.util.List;
import java.util.ArrayList;

public class EnvelopedDigitalSignature {

    public static void main(String[] args) throws Exception {
  
        //STEP-1 :Create a XMLSigantureFactory object
	XMLSignatureFactory xmlSignatureFactory =	XMLSignatureFactory.getInstance("DOM");

	//---------------------------STEP-2--------------------------
        //Create the digest method : digest means - Hashing the Data + Encryption using public/private Key
        DigestMethod digestMethod = xmlSignatureFactory.newDigestMethod(DigestMethod.SHA1, null);
        
        //Create a Tranform object : Transfrom simple XML file to signed XML file --> Type of output wil be ENEVELOPED
        Transform transform = xmlSignatureFactory.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null);
        List < Transform >  transformList = new ArrayList < Transform >  () ; 
        transformList.add(transform);
        
        //Create the Reference object : by passing the Digest method and Transform Method to xmlSignationFactory
        Reference reference = xmlSignatureFactory.newReference("", digestMethod, transformList, null, null);
        List < Reference >  referenceList = new ArrayList < Reference >  () ; 
        referenceList.add(reference);
        
        //-------------------STEP-2 ENDS----------------------------------
        
        //-------------------STEP-3-----------------------------------
        /*
         * Getting a Signed Info Object - It depends upon CanonicalizationMethod : simple form of Data
         * 									SignatureMethod : Similar to digest ( Encrypted Hash value of Data), we will use SHA1 algo to Encrypt the signature
         * 									Reference Object - Referce to xmlSignedFactory which in turn has referece to Digest and Transform Object
         */
        //Create the canonicalization method
        CanonicalizationMethod canonicalizationMethod =	xmlSignatureFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,(C14NMethodParameterSpec)null);
        
        //Create the signature method
        SignatureMethod signatureMethod =  xmlSignatureFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null);
        
        //Create the SignedInfo object
        SignedInfo signedInfo = xmlSignatureFactory.newSignedInfo(canonicalizationMethod,signatureMethod, referenceList, null); 
        
        //------------------STEP-3 ENDS-----------------------------
        
	//Create a KeyPairGenerator with 512 key size
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        
        //Create a KeyPair, based on the above KeyPairGenerator
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        //Create a KeyInfoFactory
        KeyInfoFactory keyInfoFactory = xmlSignatureFactory.getKeyInfoFactory();
        
        //Create a KeyValue, based on the above KeyInfoFactory
        KeyValue keyValue = keyInfoFactory.newKeyValue(keyPair.getPublic());
        List < KeyValue >  keyvalueList =	new ArrayList < KeyValue >  () ; 
        keyvalueList.add(keyValue);
       
        //Create a KeyInfo, based on the above KeyValue
        KeyInfo keyInfo = keyInfoFactory.newKeyInfo(keyvalueList);
        
        //Create a classic DOM factory instance 
		//(DocumentBuilderFactory)
        DocumentBuilderFactory documentBuilderFactory =
		 DocumentBuilderFactory.newInstance();
        
        //Make namespace aware
        documentBuilderFactory.setNamespaceAware(true);
        
        //Create a DocumentBuilder
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        
        //Create a Document
        Document document = documentBuilder.parse( new File("xml_test_files/test.xml"));
        
        //Create a DOMSignContext
        DOMSignContext domSignContext = new DOMSignContext(keyPair.getPrivate(), document.getDocumentElement());

        //Finally, create the XMLSignature
        XMLSignature xmlSignature =	 xmlSignatureFactory.newXMLSignature(signedInfo,keyInfo);
        
        //Sign the document
        xmlSignature.sign(domSignContext);
        
        //Write the resulted document
        OutputStream outputStream = 	new FileOutputStream("xml_test_files/outEnveloped.xml");
        
        //Create a TransformerFactory
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        
        //Create a Tranformer
        Transformer transformer = transformerFactory.newTransformer();
        
        //Write the result into the out.xml document
        transformer.transform(new DOMSource(document),	new StreamResult(outputStream));
    }
}
