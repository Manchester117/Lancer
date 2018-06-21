package component.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ResourceCDN;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class ReportManager {
    private static Logger logger = LogManager.getLogger(ReportManager.class);
    private static ExtentReports extent;

    /*
    * @description: 获取报告实例
    * @author:      Alex
    * @date:        2018/5/4
    * @time:        10:50
    * @param:       reportName  报告名称
    * @return:
    */
    public static ExtentReports getInstance(String reportName) {
        if (Objects.isNull(extent))
            createInstance(reportName);
        return extent;
    }

    /*
    * @description: 创建报告实例
    * @author:      Alex
    * @date:        2018/5/4
    * @time:        10:49
    * @param:       reportName  报告名称
    * @return:
    */
    private static void createInstance(String reportName) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportName);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle(reportName);
        htmlReporter.config().setEncoding("UTF-8");
        htmlReporter.config().setReportName(reportName);
        htmlReporter.config().setResourceCDN(ResourceCDN.EXTENTREPORTS);        // 在不使用VPN的情况下,需要使用ExtentReports的CDN

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        logger.info("报告配置初始化完毕!");
    }
}
