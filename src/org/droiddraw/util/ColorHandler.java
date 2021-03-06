package org.droiddraw.util;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Hashtable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.droiddraw.property.ColorProperty;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ColorHandler extends DefaultHandler {
	Hashtable<String,Color> colors;
	StringBuffer buff;
	String name;

	public ColorHandler() {
		colors = new Hashtable<String,Color>();
		buff = new StringBuffer();
	}

	@Override
	public void characters(char[] arg0, int arg1, int arg2) {
		buff.append(arg0,arg1,arg2);
	}

	@Override
	public void startDocument() {
		colors.clear();
	}

	@Override
	public void startElement(String ns, String lName, String qName, Attributes atts) {
		buff.setLength(0);
		if (qName.equals("color")) {
			name = atts.getValue("name");
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
	{
		if (qName.equals("color")) {
			colors.put(name, ColorProperty.parseColor(buff.toString()));
		}
	}

	public Hashtable<String,Color> getColors() {
		return colors;
	}

	public static Hashtable<String, Color> load(InputStream is) 
	throws SAXException, ParserConfigurationException, IOException	
	{
		return load(new InputSource(is));
	}

	public static Hashtable<String, Color> load(InputSource in) 
	throws SAXException, ParserConfigurationException, IOException
	{	
		ColorHandler sh = new ColorHandler();

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(in, sh);
		return sh.getColors();
	}

	public static void dump(Writer w, Hashtable<String, Color> colors) 
	throws IOException
	{
		PrintWriter pw = new PrintWriter(w);
		pw.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		pw.println("<resources>");
		for (String key : colors.keySet()) {
			Color value = colors.get(key);
			String clr = "#"+Integer.toString(value.getRed(), 16)+
				Integer.toString(value.getGreen(), 16)+
				Integer.toString(value.getBlue(), 16)+
				Integer.toString(value.getAlpha(), 16);
			pw.println("<drawable name=\""+key+"\">"+clr+"</drawable>");
		}
		pw.println("</resources>");
		pw.flush();
		w.flush();
	}
}
