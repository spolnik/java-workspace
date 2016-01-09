package com.reports;

import com.google.common.io.Resources;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.view.JasperDesignViewer;

import java.net.URL;

public class JasperDesignViewerRunner {
    public static void main(String[] args) throws JRException {
        URL xmlReportTemplatePath = Resources.getResource("FirstReport.jrxml");

        String jasperFileName = JasperCompileManager.compileReportToFile(xmlReportTemplatePath.getFile());
        JasperDesignViewer.main(new String[] {"-F", jasperFileName});
    }
}
