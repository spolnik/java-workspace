package com.reports;

import com.google.common.io.Resources;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;

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
        viewPrintFile(jasperFileName);
    }

    private static String viewTemplate(URL xmlReportTemplatePath) throws JRException {
        String jasperFileName = JasperCompileManager.compileReportToFile(xmlReportTemplatePath.getFile());
        JasperDesignViewer.main(new String[] {"-F", jasperFileName});
        return jasperFileName;
    }

    private static void generatePdf(String jasperFileName) throws JRException, IOException {
        byte[] data = JasperRunManager.runReportToPdf(jasperFileName, new HashMap<>(), new JREmptyDataSource());
        Files.write(Paths.get("FirstReport.pdf"), data);
    }

    private static void viewPrintFile(String jasperFileName) throws JRException {
        String printFileName = JasperFillManager.fillReportToFile(jasperFileName, new HashMap<>(), new JREmptyDataSource());
        JasperViewer.main(new String[]{"-F", printFileName});
    }
}
