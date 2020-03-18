package Magnit;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class XMLService {
    static void createXML(DBConnect dbConnect) {
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            Document document = factory.newDocumentBuilder().newDocument();
            Element root = document.createElement("entries");
            document.appendChild(root);
            List<Integer> result = dbConnect.select();
            for (Integer a : result) {
                Element entry = document.createElement("entry");
                Element field = document.createElement("field");
                field.setTextContent(String.valueOf(a));
                entry.appendChild(field);
                root.appendChild(entry);
            }


            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream("1.xml")));

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    static void transformXML() {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource("src/resource/transform.xsl");
            Transformer transformer = factory.newTransformer(xslt);
            Source text = new StreamSource("1.xml");
            transformer.transform(text, new StreamResult("2.xml"));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
    static long parseXML() {
        long sum = 0;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document doc = factory.newDocumentBuilder().parse("2.xml");
            NodeList nodeList = doc.getElementsByTagName("entry");
            for(int i = 0; i < nodeList.getLength(); i++) {
                sum += Long.parseLong(((Element)nodeList.item(i)).getAttribute("field").trim());
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return sum;
    }
}

