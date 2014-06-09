package com.example.AndroidRSSReader;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;

/**
 * Created by Tatu on 9.6.2014.
 */
public class XmlReader {
    enum Operators {
        ITEM, CATEGORY, PUBLISH_DATE, TITLE, LINK
    }

    private static String XML_LOCATION = "http://www.hs.fi/uutiset/rss/";
    private Document rootDocument;
    private final HttpGet URL;

    private NodeList rootNodes;

    public NodeList getRootNodes() {
        return rootNodes;
    }

    public ArrayList<String> getTitleValues() {
        return titleValues;
    }

    private ArrayList<String> titleValues;

    public XmlReader() {
        URL = new HttpGet(XML_LOCATION);
        getXMLDocument();
        rootNodes = XmlParser.parseXML(this.rootDocument);
        titleValues = XmlParser.getTitleValues(this.rootNodes);
    }

    public String getNodeValueById(int identifier, Operators operator) {
        return XmlParser.getNodeValueByOperator(identifier, operator, rootDocument);
    }

    private void getXMLDocument() {
        HttpResponse httpResponse = null;
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            httpResponse = client.execute(URL);
            StatusLine statusLine = httpResponse.getStatusLine();

            if (statusLine.getStatusCode() != 200) {
                Log.d("XmlReader", "HTTP error, invalid server statusLine code: " + httpResponse.getStatusLine());
            }
        } catch (Exception ex) {
            Log.d("XmlParser", "Document fetching failed");
            ex.printStackTrace();
        }
        rootDocument = getDocumentFromConnection(httpResponse);
    }

    private Document getDocumentFromConnection(HttpResponse httpResponse) {
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(httpResponse.getEntity().getContent());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }

    public interface TagNames {
        public String ITEM = "item";
        public String TITLE = "title";
        public String PUBLISH_DATE = "pubDate";
        public String CATEGORY = "category";
        public String LINK = "link";
    }

    private static class XmlParser implements TagNames {
        private static NodeList parseXML(Document document) {
            NodeList nodeList = document.getElementsByTagName(ITEM);
            return nodeList;
        }

        private static ArrayList<String> getTitleValues(final NodeList nodeList) {
            ArrayList list = new ArrayList() {{
                for (int i = 0, isize = nodeList.getLength(); i < isize; ++i) {
                    add(nodeList.item(i).getTextContent());
                }
            }};
            return list;
        }

        private static String getNodeValueByOperator(int identifier, Operators operator, Document document) {
            String result = "";
            NodeList nodeList;
            switch (operator) {
                case ITEM:
                    nodeList = document.getElementsByTagName(ITEM);
                    result = nodeList.item(identifier).getTextContent();
                    break;
                case TITLE:
                    nodeList = document.getElementsByTagName(TITLE);
                    result = nodeList.item(identifier).getTextContent();
                    break;
                case PUBLISH_DATE:
                    nodeList = document.getElementsByTagName(PUBLISH_DATE);
                    result = nodeList.item(identifier).getTextContent();
                    break;
                case CATEGORY:
                    nodeList = document.getElementsByTagName(CATEGORY);
                    result = nodeList.item(identifier).getTextContent();
                    break;
                case LINK:
                    nodeList = document.getElementsByTagName(LINK);
                    result = nodeList.item(identifier).getTextContent();
                    break;
            }
            return result;
        }
    }
}
