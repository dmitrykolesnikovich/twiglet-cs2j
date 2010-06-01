/*
   Copyright 2007-2010 Rustici Software, LLC

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

   Author(s):

   Kevin Glynn (kevin.glynn@scorm.com)
*/

package RusticiSoftware.System.Xml.Xsl;
//
// This is now a wrapper for XslCompiledTransform because that has superseded XslTransform going to .Net 2.0

import java.io.StringWriter;
import java.io.StringReader;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import RusticiSoftware.System.Xml.XmlDocument;
import RusticiSoftware.System.Xml.XmlTextReader;

public class XslTransform {
	protected Transformer transformer;
	
	public XslTransform() {
		
	}
	
	public void load(XmlTextReader xmlTextReader, String ignore, String ignore2) throws TransformerConfigurationException {
		String xmlString = xmlTextReader.ToString();
		StringReader stringReader = new StringReader(xmlString);
		StreamSource streamSource = new StreamSource(stringReader);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		this.transformer = transformerFactory.newTransformer(streamSource);
	}
	
	public void transform(XmlDocument xmlDocument, String ignored, StringWriter stringWriter) throws TransformerException	{
		String inXml = xmlDocument.getDocumentElement().getOuterXml();
		StringReader stringReader = new StringReader(inXml);
		StreamSource streamSource = new StreamSource(stringReader);
		StreamResult streamResult = new StreamResult(stringWriter);
		this.transformer.transform(streamSource, streamResult);
	}
}