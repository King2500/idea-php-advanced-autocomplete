package net.king2500.plugins.PhpAdvancedAutoComplete.utils;

import com.intellij.openapi.project.Project;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class DatabaseUtil {
    public static String[] getDbHostnames(Project project, String jdbcPrefix) {
        List<String> hostnames = new ArrayList<String>();

        if (project == null) {
            return null;
        }

        String dataSourcesPath = getDataSourcesXmlPath(project);
        File dataSourcesFile = new File(dataSourcesPath);

        if (!jdbcPrefix.startsWith("jdbc:")) {
            jdbcPrefix = "jdbc:" + jdbcPrefix;
        }

        String jdbcPrefixQuoted = '"' + jdbcPrefix + '"';

        NodeList nodes = XmlUtil.getNodesByXPath(dataSourcesFile, "/project/component/data-source/jdbc-url/text()[starts-with(., " + jdbcPrefixQuoted + ")]");

        if (nodes == null) {
            return null;
        }

        for (int i = 0; i < nodes.getLength(); i++) {
            String jdbcUrl = nodes.item(i).getNodeValue().trim();
            String jdbcPath = jdbcUrl.replace(jdbcPrefix, "");

            if (jdbcPath.contains("/")) {
                hostnames.add(jdbcPath.substring(0, jdbcPath.indexOf('/')));
            }
            else {
                hostnames.add(jdbcPath);
            }
        }

        return hostnames.toArray(new String[hostnames.size()]);
    }

    public static String[] getDbUsers(Project project, String jdbcPrefix) {
        List<String> users = new ArrayList<String>();

        if (project == null) {
            return null;
        }

        String dataSourcesPath = getDataSourcesXmlPath(project);
        File dataSourcesFile = new File(dataSourcesPath);

        if (!jdbcPrefix.startsWith("jdbc:")) {
            jdbcPrefix = "jdbc:" + jdbcPrefix;
        }

        String jdbcPrefixQuoted = '"' + jdbcPrefix + '"';

        NodeList nodes = XmlUtil.getNodesByXPath(dataSourcesFile, "/project/component/data-source[jdbc-url/text()[starts-with(., " + jdbcPrefixQuoted + ")]]/user-name/text()");

        if (nodes == null) {
            return null;
        }

        for (int i = 0; i < nodes.getLength(); i++) {
            String username = nodes.item(i).getNodeValue().trim();
            users.add(username);
        }

        return users.toArray(new String[users.size()]);
    }

    public static String[] getDbNames(Project project, String jdbcPrefix) {
        List<String> dbNames = new ArrayList<String>();

        if (project == null) {
            return null;
        }

        String dataSourcesPath = getDataSourcesXmlPath(project);
        File dataSourcesFile = new File(dataSourcesPath);

        if (!jdbcPrefix.startsWith("jdbc:")) {
            jdbcPrefix = "jdbc:" + jdbcPrefix;
        }

        String jdbcPrefixQuoted = '"' + jdbcPrefix + '"';

        // Search in JDBC URL
        NodeList nodes1 = XmlUtil.getNodesByXPath(dataSourcesFile, "/project/component/data-source/jdbc-url/text()[starts-with(., " + jdbcPrefixQuoted + ")]");

        if (nodes1 == null) {
            return null;
        }

        for (int i = 0; i < nodes1.getLength(); i++) {
            String jdbcUrl = nodes1.item(i).getNodeValue().trim();
            String jdbcPath = jdbcUrl.replace(jdbcPrefix, "");

            if (!jdbcPath.contains("/")) {
                continue;
            }

            if (jdbcPath.contains("?")) {
                dbNames.add(jdbcPath.substring(jdbcPath.indexOf('/') + 1, jdbcPath.indexOf('?')));
            }
            else {
                dbNames.add(jdbcPath.substring(jdbcPath.indexOf('/') + 1));
            }
        }

        // Search in selected schemas
        NodeList nodes2 = XmlUtil.getNodesByXPath(dataSourcesFile, "/project/component/data-source[jdbc-url/text()[starts-with(., " + jdbcPrefixQuoted + ")]]/*[self::schema-pattern or self::default-schemas]/text()");

        if (nodes2 == null) {
            return null;
        }

        for (int i = 0; i < nodes2.getLength(); i++) {
            String schemaPattern = nodes2.item(i).getNodeValue().trim();

            // we can only handle schemas listed in xml file. '*' is not yet supported
            String[] schemas = schemaPattern.split(" ");

            for (String schema : schemas) {
                if (!schema.contains(".")) {
                    continue;
                }

                String schemaName = schema.substring(0, schema.indexOf('.'));
                dbNames.add(schemaName);
            }
        }

        return dbNames.toArray(new String[dbNames.size()]);
    }

    public static String[] getPdoDSNs(Project project, String jdbcPrefix) {
        List<String> dsns = new ArrayList<String>();

        if (project == null) {
            return null;
        }

        String dataSourcesPath = getDataSourcesXmlPath(project);
        File dataSourcesFile = new File(dataSourcesPath);

        if (!jdbcPrefix.startsWith("jdbc:")) {
            jdbcPrefix = "jdbc:" + jdbcPrefix;
        }

        String jdbcPrefixQuoted = '"' + jdbcPrefix + '"';

        NodeList nodes = XmlUtil.getNodesByXPath(dataSourcesFile, "/project/component/data-source[jdbc-url/text()[starts-with(., " + jdbcPrefixQuoted + ")]]");

        if (nodes == null) {
            return null;
        }

        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element)nodes.item(i);

            String jdbcUrl = element.getElementsByTagName("jdbc-url").item(0).getTextContent().trim();
            String jdbcPath = jdbcUrl.replace(jdbcPrefix, "");
            String dbHost;

            if (jdbcPath.contains("/")) {
                dbHost = jdbcPath.substring(0, jdbcPath.indexOf('/'));
                String dbName = jdbcPath.substring(jdbcPath.indexOf('/') + 1);

                if (dbName.contains("?")) {
                    dbName = dbName.substring(0, dbName.indexOf('?'));
                }

                dsns.add("mysql:dbname=" + dbName + ";host=" + dbHost);
            }
            else {
                dbHost = jdbcPath;
            }

            NodeList schemaNodes = element.getElementsByTagName("schema-pattern");

            if (schemaNodes.getLength() > 0) {
                String schemaPattern = schemaNodes.item(0).getTextContent().trim();

                // we can only handle schemas listed in xml file. '*' is not yet supported
                String[] schemas = schemaPattern.split(" ");

                for (String schema : schemas) {
                    if (!schema.contains(".")) {
                        continue;
                    }

                    String schemaName = schema.substring(0, schema.indexOf('.'));
                    dsns.add("mysql:dbname=" + schemaName + ";host=" + dbHost);
                }
            }

            NodeList schemaDefaultNodes = element.getElementsByTagName("default-schemas");

            if (schemaNodes.getLength() > 0) {
                String schemaPattern = schemaDefaultNodes.item(0).getTextContent().trim();

                // we can only handle schemas listed in xml file. '*' is not yet supported
                String[] schemas = schemaPattern.split(" ");

                for (String schema : schemas) {
                    if (!schema.contains(".")) {
                        continue;
                    }

                    String schemaName = schema.substring(0, schema.indexOf('.'));
                    dsns.add("mysql:dbname=" + schemaName + ";host=" + dbHost);
                }
            }
        }

        return dsns.toArray(new String[dsns.size()]);
    }

    private static String getDataSourcesXmlPath(Project project) {
        return project.getBasePath() + File.separator + ".idea" + File.separator + "dataSources.xml";
    }
}
