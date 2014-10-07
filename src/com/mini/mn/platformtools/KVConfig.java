package com.mini.mn.platformtools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.mini.mn.util.Log;
import com.mini.mn.util.Util;

/**
 * utility class for parse xml, json, ini to String-String map
 * 
 * @author kirozhao
 * 
 */
public class KVConfig {

	private static final String TAG = "MicroMsg.SDK.KVConfig";

	private static boolean verbose = false;

	/**
	 * INI parser
	 * 
	 * @param ini
	 * @return
	 */
	public static Map<String, String> parseIni(final String ini) {
		if (ini == null || ini.length() <= 0) {
			return null;
		}

		final Map<String, String> values = new HashMap<String, String>();

		final String[] lines = ini.split("\n");
		for (final String line : lines) {
			if (line == null || line.length() <= 0) {
				continue;
			}

			final String[] kv = line.trim().split("=", 2);
			if (kv == null || kv.length < 2) {
				continue;
			}

			// key
			final String key = kv[0];
			final String value = kv[1];
			if (key == null || key.length() <= 0 || !key.matches("^[a-zA-Z0-9_]*")) {
				continue;
			}

			values.put(key, value);
		}

		if (verbose) {
			dump(values);
		}
		return values;
	}

	/**
	 * XML parser
	 * 
	 * @param xml
	 * @param tag
	 * @param encode
	 * @return
	 */
	public static Map<String, String> parseXml(String xml, final String tag, final String encode) {

		final int check = (xml == null ? -1 : xml.indexOf('<'));
		if (check < 0) {
			Log.e(TAG, "text not in xml format");
			return null; // not xml
		}

		if (check > 0) {
			Log.w(TAG, "fix xml header from + %d", check);
			xml = xml.substring(check);
		}

		if (xml == null || xml.length() <= 0) {
			return null;
		}

		final Map<String, String> values = new HashMap<String, String>();
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (final ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
		if (builder == null) {
			Log.e(TAG, "new Document Builder failed");
			return null;
		}

		Document dom = null;
		try {
			final InputSource is = new InputSource(new ByteArrayInputStream(xml.getBytes()));
			if (encode != null) {
				is.setEncoding(encode);
			}
			dom = builder.parse(is);
			dom.normalize();

		} catch (final DOMException e) {
			e.printStackTrace();

		} catch (final SAXException e) {
			e.printStackTrace();
			return null;

		} catch (final IOException e) {
			e.printStackTrace();
			return null;

		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}

		if (dom == null) {
			Log.e(TAG, "new Document failed");
			return null;
		}

		final Element root = dom.getDocumentElement();
		if (root == null) {
			Log.e(TAG, "getDocumentElement failed");
			return null;
		}

		if (tag != null && tag.equals(root.getNodeName())) { // fix 4.0 xml parse error
			putAllNode(values, "", root, 0);

		} else {
			final NodeList items = root.getElementsByTagName(tag);
			if (items.getLength() <= 0) {
				Log.e(TAG, "parse item null");
				return null;
			}
			if (items.getLength() > 1) {
				Log.w(TAG, "parse items more than one");
			}
			putAllNode(values, "", items.item(0), 0);
		}

		if (verbose) {
			dump(values);
		}

		return values;
	}

	private static void putAllNode(final Map<String, String> values, String prefix, final Node node, final int indexOf) {
		if (node.getNodeName().equals("#text")) {
			values.put(prefix, node.getNodeValue());

		} else if (node.getNodeName().equals("#cdata-section")) {
			values.put(prefix, node.getNodeValue());

		} else {
			prefix += "." + node.getNodeName() + ((indexOf > 0) ? indexOf : "");
			values.put(prefix, node.getNodeValue());

			// process attributes
			final NamedNodeMap properties = node.getAttributes();
			if (properties != null) {
				for (int i = 0; i < properties.getLength(); i++) {
					final Node p = properties.item(i);
					values.put(prefix + ".$" + p.getNodeName(), p.getNodeValue());
				}
			}

			// process sub node
			final HashMap<String, Integer> conflicts = new HashMap<String, Integer>();
			final NodeList items = node.getChildNodes();
			for (int i = 0; i < items.getLength(); i++) {
				final Node s = items.item(i);
				final int no = Util.nullAsNil(conflicts.get(s.getNodeName()));
				putAllNode(values, prefix, s, no);
				conflicts.put(s.getNodeName(), no + 1);
			}
		}
	}

	private static void dump(final Map<String, String> values) {
		if (values == null || values.size() <= 0) {
			Log.v(TAG, "empty values");
			return;
		}

		final Iterator<Map.Entry<String, String>> iter = values.entrySet().iterator();
		while (iter.hasNext()) {
			final Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
			Log.v(TAG, "key=" + entry.getKey() + " value=" + entry.getValue());
		}
	}
}
