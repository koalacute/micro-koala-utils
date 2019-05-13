package com.koalacute.microkoala.utils.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import org.apache.commons.lang3.StringUtils;

import java.io.Writer;
import java.util.Map;

/**
 * XML工具类
 */
public class XmlUtils {

    /**
     * 创建XStream
     */
    private static XStream createXstream() {
        return new XStream(new XppDriver() {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    boolean cdata = true;

                    @Override
                    public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {
                        super.startNode(name, clazz);
                    }

                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        if (cdata) {
                            writer.write(cDATA(text));
                        } else {
                            writer.write(text);
                        }
                    }

                    private String cDATA(String text) {
                        return "<![CDATA[" + text + "]]>";
                    }

                };
            }
        });
    }

    /**
     * 支持注解转化XML
     */
    public static String toXML(Object obj, Class<?> cls) {
        if (obj == null) {
            return null;
        }
        XStream xstream = createXstream();
        xstream.processAnnotations(cls);
        return xstream.toXML(obj);
    }

    /**
     * Object 转化 XML
     */
    public static String toXML(Object obj) {
        if (obj == null) {
            return null;
        }
        XStream xstream = new XStream();
        return xstream.toXML(obj);
    }

    /**
     * XML转化为JAVA对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T xml2Obj(String xml, Class<?> cls) {
        if (StringUtils.isBlank(xml)) {
            return null;
        }
        XStream xstream = new XStream();
        if (cls != null) {
            xstream.processAnnotations(cls);
        }
        return (T) xstream.fromXML(xml);
    }
    
    /**
     * XML转化为JAVA对象(xml里面有的字段并且Object里面无的字段、则xml的字段忽略)
     */
    @SuppressWarnings("unchecked")
    public static <T> T xmlToObj(String xml, Class<?> cls) {
        if (StringUtils.isBlank(xml)) {
            return null;
        }
        XStream xstream = new XStream() {
            @Override
            protected MapperWrapper wrapMapper(MapperWrapper next) {
                return new MapperWrapper(next) {
                    @Override
                    public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                        if (definedIn == Object.class) {
                            return false;
                        }
                        return super.shouldSerializeMember(definedIn, fieldName);
                    }
                };
            }
        };
        if (cls != null) {
            xstream.processAnnotations(cls);
        }
        return (T) xstream.fromXML(xml);
    }

    /**
     * XML转化为JAVA对象
     */
    public static <T> T xml2Obj(String xml) {
        return xml2Obj(xml, null);
    }

	public static String maptoXML(Map<String, String> map,String root) {
		StringBuilder sb = new StringBuilder("<");
        sb.append(root);
        sb.append(">");

        for (Map.Entry<String, String> e : map.entrySet()) {
            sb.append("<");
            sb.append(e.getKey());
            sb.append(">");

            sb.append(e.getValue());
            sb.append("</");
            sb.append(e.getKey());
            sb.append(">");
        }

        sb.append("</");
        sb.append(root);
        sb.append(">");

        return sb.toString();
	}

}
