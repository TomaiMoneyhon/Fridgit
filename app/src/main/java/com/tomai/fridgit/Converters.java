package com.tomai.fridgit;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by admin on 12/10/2015.
 */
public class Converters {

    private Context context;

    private String convertURL;
    private Document doc;
    private URL fullurl;
    private String result;
    private int response;
    private Handler handler = new Handler();

    HttpURLConnection conn;

    public enum ConverterKind {
        CookingUnits,
        WeightUnits,
    }

    public Converters(Context context) {
        this.context = context;
    }

    public String cookingUnitConverter ( double amount, String original,String convertTo, ConverterKind converterKind) {

                switch (converterKind)

                {
                    case CookingUnits:
                        convertURL = "http://www.webservicex.net/ConvertCooking.asmx/ChangeCookingUnit?CookingValue=" + amount + "&fromCookingUnit=" + original + "&toCookingUnit=" + convertTo;
                        break;
                    case WeightUnits:
                        convertURL = "http://www.webservicex.net/ConvertWeight.asmx/ConvertWeight?Weight=" + amount + "&FromUnit=" + original + "&ToUnit=" + convertTo;
                }

                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                fullurl = new URL(convertURL);
                                conn = (HttpURLConnection) fullurl.openConnection();
                                conn.connect();
                                response = conn.getResponseCode();


                                 if (response >= 400) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(context, "Something bad happened", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                 } else {

                                     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                                     DocumentBuilder db = dbf.newDocumentBuilder();
                                     doc = db.parse(conn.getInputStream());

                                     NodeList list = doc.getElementsByTagName("double");
                                     NodeList textNodes = (list.item(0)).getChildNodes();

                                    result = (textNodes.item(0)).getNodeValue();
                                 }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ParserConfigurationException e) {
                                e.printStackTrace();
                            } catch (SAXException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
        while (result == null) {} //TODO Seems to be not best practice. should see about other options.
        return result;
    }
}
