package com.reports;

import com.google.common.io.Resources;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperDesignViewer;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class JasperDesignViewerRunner {

    public static void main(String[] args) throws JRException, IOException {
        URL xmlReportTemplatePath = Resources.getResource("FirstReport.jrxml");

        String jasperFileName = viewTemplate(xmlReportTemplatePath);
        generatePdf(jasperFileName);
    }

    private static String viewTemplate(URL xmlReportTemplatePath) throws JRException {
        String jasperFileName = JasperCompileManager.compileReportToFile(xmlReportTemplatePath.getFile());
        JasperDesignViewer.main(new String[] {"-F", jasperFileName});
        return jasperFileName;
    }

    private static void generatePdf(String jasperFileName) throws JRException, IOException {

        byte[] data = JasperRunManager.runReportToPdf(jasperFileName, parameters(), new JREmptyDataSource());
        Files.write(Paths.get("FirstReport.pdf"), data);
    }

    private static HashMap<String, Object> parameters() {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("paramName", "paramValue");
        return parameters;
    }
}
