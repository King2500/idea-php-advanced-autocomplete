package net.king2500.plugins.PhpAdvancedAutoComplete.utils;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas
 * Date: 07.07.13
 * Time: 18:04
 * To change this template use File | Settings | File Templates.
 */
public class XmlUtil {
    public static NodeList getNodesByXPath(File file, String xpath) {
        if(!file.isFile() || !file.canRead())
            return null;

        Document document;
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
            document = documentBuilder.parse(file);
        } catch (ParserConfigurationException e) {
            return null;
        } catch (SAXException e) {
            return null;
        } catch (IOException e) {
            return null;
        }

        if(document == null) {
            return null;
        }

        Object result;
        try {
            XPath xpathCompiler = XPathFactory.newInstance().newXPath();
            XPathExpression xPathExpr = xpathCompiler.compile(xpath);
            result = xPathExpr.evaluate(document, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            return null;
        }

       return (NodeList)result;
    }
}
