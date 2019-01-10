/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputoutput;

import java.io.IOException;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Chris Taliaferro
 */
public class XmlParser 
{

   private ConnectionData connectionData;
   private Document document;
   private static final Logger logger = Logger.getLogger(XmlParser.class.getName());
   
    public ConnectionData getConnectionData() {
        return connectionData;
    }

    public void setConnectionData(ConnectionData connectionData) {
        this.connectionData = connectionData;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
   

   public XmlParser(String file)
   {
       parseXmlFile(file);
   }

   private void parseXmlFile(String fileName)
    {        
        //Get the DOM Builder Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try 
        {
            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //Load and Parse the XML document
            //document contains the complete XML as a Tree.
            document = db.parse(ClassLoader.getSystemResourceAsStream("inputoutput/properties.xml"));
            
            //Iterating through the nodes and extracting the data.
            NodeList nodeList = document.getDocumentElement().getChildNodes();

            for(int i = 0; i < nodeList.getLength(); i++) 
            {
                //get the <Employee> element
                Node node = nodeList.item(i);

                if(node instanceof Element)
                {
                    //for each <employee> element get text or int values of
                    //name ,id, age and name
                    String type = node.getAttributes().getNamedItem("type").getNodeValue();
                    //Create a new connection
                    connectionData = new ConnectionData();
                    connectionData.setType(type);
                    
                    NodeList childNodes = node.getChildNodes();

                    for (int j = 0; j < childNodes.getLength(); j++) 
                    {
                        Node cNode = childNodes.item(j);  
 
                        //Identifying the child tag of employee encountered.
                        if (cNode instanceof Element) 
                        {
                            String content = cNode.getLastChild().getTextContent().trim();
                            switch (cNode.getNodeName()) 
                            {
                                case "url":
                                  connectionData.setUrl(content);
                                  break;
                                case "ipaddress":
                                  connectionData.setIpaddress(content);
                                  break;
                                case "port":
                                  connectionData.setPort(content);
                                  break;
                                case "database":
                                  connectionData.setDatabase(content);
                                  break;
                                case "login":
                                  connectionData.setLogin(content);
                                  break;
                                case "password":
                                  connectionData.setPassword(content);
                                  break;                                  
                            }
                        }
                    }
                }
            }
        }
        catch(ParserConfigurationException pce) 
        {
            pce.printStackTrace();
        }
        catch(SAXException se) 
        {
            se.printStackTrace();
        }
        catch(IOException ioe) 
        {
            ioe.printStackTrace();
        }
    }

}

