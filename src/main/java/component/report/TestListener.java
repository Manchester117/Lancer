package component.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import macaca.client.MacacaClient;
import org.apache.commons.lang3.StringUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import util.Tools;

import java.nio.file.Paths;

public class TestListener implements ITestListener {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static MacacaClient driver;
    private String reportFolderPath;

    /**
    * @description: 给监听器设置Driver实例,用于后面的截图方法
    * @author:      Alex
    * @date:        2018/5/4
    * @time:        10:51
    * @param:       macacaDriver    用户操作的Handler
    * @return:
    */
    public static void setDriver(MacacaClient macacaDriver) {
        driver = macacaDriver;
    }

    /**
    * @description: 测试开始-定义报告存放文件夹以及获取报告实例
    * @author:      Alex
    * @date:        2018/5/4
    * @time:        10:52
    * @param:       iTestContext    测试上下文
    * @return:
    */
    @Override
    public void onStart(ITestContext iTestContext) {
        String reportRootPath = iTestContext.getCurrentXmlTest().getParameter("reportRootPath");
        String reportName = StringUtils.join("Report", "_", Tools.getTime());
        String reportFullName = StringUtils.join(reportName, ".html");
        reportFolderPath = Paths.get(reportRootPath, reportName).toString();
        Tools.createFolder(reportFolderPath);                                                      // 创建存放报告的子文件夹

        String reportFullPath = Paths.get(reportFolderPath, reportFullName).toString();

        extent = ReportManager.getInstance(reportFullPath);                                        // 获取报告实例
        ExtentTest parent = extent.createTest(iTestContext.getName());
        parentTest.set(parent);
    }

    /**
    * @description: 测试完成-进行报告写入
    * @author:      Alex
    * @date:        2018/5/4
    * @time:        10:53
    * @param:       iTestContext    测试上下文
    * @return:
    */
    @Override
    public void onFinish(ITestContext iTestContext) {
        extent.flush();
    }

    /**
    * @description: 测试方法开始
    * @author:      Alex
    * @date:        2018/5/4
    * @time:        10:54
    * @param:       iTestResult     测试方法的Handler
    * @return:
    */
    @Override
    public void onTestStart(ITestResult iTestResult) {
        ExtentTest childTest = parentTest.get().createNode(iTestResult.getMethod().getMethodName());
        test.set(childTest);
    }

    /**
     * @description: 测试方法验证成功-需要截图
     * @author:      Alex
     * @date:        2018/5/4
     * @time:        10:54
     * @param:       iTestResult     测试方法的Handler
     * @return:
     */
    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        String successMethodName = iTestResult.getMethod().getMethodName();
        for (Object param: iTestResult.getParameters()) {
            String paramInfo = StringUtils.join("参数: ", param.toString());
            test.get().info(paramInfo);
        }
        test.get().pass(StringUtils.join(successMethodName, "||||验证通过"));
        Tools.takeScreenShot(reportFolderPath, successMethodName, driver, test);                  // 成功后截图
    }

    /**
     * @description: 测试方法验证失败-需要截图
     * @author:      Alex
     * @date:        2018/5/4
     * @time:        10:54
     * @param:       iTestResult     测试方法的Handler
     * @return:
     */
    @Override
    public void onTestFailure(ITestResult iTestResult) {
        String failMethodName = iTestResult.getMethod().getMethodName();
        for (Object param: iTestResult.getParameters()) {
            String paramInfo = StringUtils.join("参数: ", param.toString());
            test.get().info(paramInfo);
        }
        test.get().fail(failMethodName + "||||" + iTestResult.getThrowable());
        Tools.takeScreenShot(reportFolderPath, failMethodName, driver, test);                     // 失败后截图
    }

    /**
    * @description: 测试方法跳过
    * @author:      Alex
    * @date:        2018/5/4
    * @time:        10:55
    * @param:       iTestResult     测试方法Handler
    * @return:
    */
    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        test.get().skip(StringUtils.join(iTestResult.getMethod().getMethodName(), "||||验证跳过"));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {}
}
