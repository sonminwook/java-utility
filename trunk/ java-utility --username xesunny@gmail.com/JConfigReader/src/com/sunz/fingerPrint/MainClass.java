package com.sunz.fingerPrint;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Collections;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;

public class MainClass {

  public static void main(String[] args) throws Exception {

    KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
    kpg.initialize(1024, new SecureRandom());
    KeyPair dsaKeyPair = kpg.generateKeyPair();

    //----------STEP 1-------------------
    XMLSignatureFactory sigFactory = XMLSignatureFactory.getInstance();
   //----------STEP 2-------------------
    Reference ref = sigFactory.newReference("xml_test_files/test.xml", sigFactory.newDigestMethod(DigestMethod.SHA1,
        null));
    //----------STEP 3-------------------
    SignedInfo signedInfo = sigFactory.newSignedInfo(sigFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE_WITH_COMMENTS,(C14NMethodParameterSpec) null),
    												sigFactory.newSignatureMethod(SignatureMethod.DSA_SHA1, null), 
    												Collections.singletonList(ref));    
    
    KeyInfoFactory kif = sigFactory.getKeyInfoFactory();
    KeyValue kv = kif.newKeyValue(dsaKeyPair.getPublic());
    KeyInfo keyInfo = kif.newKeyInfo(Collections.singletonList(kv));

    XMLSignature xmlSig = sigFactory.newXMLSignature(signedInfo, keyInfo);
  }
}
