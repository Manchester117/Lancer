package testcase;

import action.BaseAction;
import action.LoginAction;
import action.LogoutAction;
import component.report.TestListener;
import macaca.client.MacacaClient;
import org.testng.Assert;
import org.testng.annotations.*;

@Listeners({TestListener.class})
public class TestLoginAction {
    private Boolean swipeFlag = Boolean.TRUE;
    private BaseAction baseAction;
    private LoginAction loginAction;
    private LogoutAction logoutAction;

    @Parameters({"platformName", "app", "udid", "reuse", "repositoryPath", "waitTimeInterval", "waitTimeout"})
    @BeforeTest
    public void beforeTest(String platformName, String app, String udid, String reuse, String repositoryPath, String waitTimeInterval, String waitTimeout) {
        MacacaClient driver = CaseConfigAction.getDriver(platformName, app, udid, reuse, waitTimeInterval, waitTimeout);
        baseAction = new BaseAction(driver, repositoryPath);
        loginAction = new LoginAction(driver, repositoryPath);
        logoutAction = new LogoutAction(driver, repositoryPath);
    }

    @DataProvider(name = "signInData")
    public Object[][] signInData() {
        return new Object[][] {
            {"XXXXXXX@qq.com", "XXXXXXXX"},
            {"XXXXXXX2@qq.com", "XXXXXXXX"}
        };
    }

    @Test(priority = 0, description = "通过'XXXX'登录", dataProvider = "signInData")
    public void loginBySubscribeNowPage(String userName, String password) {
        if (swipeFlag) {
            baseAction.swipePageToLeft(2);
            baseAction.clickAnyPage("PageElementModule", "欢迎页", "欢迎页");
            swipeFlag = Boolean.FALSE;
        }
        loginAction.loginBySubscribeNow(userName, password);
        String verifyText = loginAction.getText("PageElementModule", "机会页", "输入关键字");
        Assert.assertEquals("输入关键字", verifyText);
        logoutAction.Logout();
    }

    @AfterTest
    public void afterTest() {
        baseAction.closeAppForAndroid("com.XXXX.XXXX");
    }
}
