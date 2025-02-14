package utility;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

public class Driver {
    private Driver() {}

    private static final ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();

    private static final Logger logger = LogManager.getLogger(Driver.class);

    public static synchronized WebDriver getDriver() {
        if (driverPool.get() == null) {
            try {
                String browser = ConfigReader.get("browser");
                boolean isHeadless = ConfigReader.getBoolean("headless");

                WebDriver driver;
                switch (browser.toLowerCase()) {
                    case "chrome" -> driver = createChromeDriver(isHeadless);
                    case "firefox" -> driver = createFirefoxDriver(isHeadless);
                    case "edge" -> driver = createEdgeDriver();
                    case "safari" -> driver = createSafariDriver();
                    default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
                }

                setupDriver(driver);
                driverPool.set(driver);

            } catch (Exception e) {
                logger.error("Failed to create WebDriver: {}", e.getMessage());
                throw new RuntimeException("Failed to create WebDriver", e);
            }
        }
        return driverPool.get();
    }

    private static ChromeDriver createChromeDriver(boolean isHeadless) {
        ChromeOptions options = new ChromeOptions();

        options.addArguments(
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--disable-notifications",
                "--remote-allow-origins=*",
                "--ignore-certificate-errors",
                "--start-maximized",
                "--headless",
                "--disable-infobars"
        );

        if (System.getenv("CI") != null || isHeadless) {
            options.addArguments(
                    "--headless=new",
                    "--window-size=1920,1080",
                    "--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36", // Güncel User-Agent
                    "--disable-blink-features=AutomationControlled",
                    "--disable-extensions",
                    "--disable-web-security",
                    "--disable-site-isolation-trials"
            );
        }

        options.setExperimentalOption("excludeSwitches",
                new String[]{"enable-automation", "enable-logging"});

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("webrtc.ip_handling_policy", "disable_non_proxied_udp");

        options.setExperimentalOption("prefs", prefs);

        options.setPageLoadTimeout(Duration.ofSeconds(45)); // 30 -> 45 saniye
        options.setImplicitWaitTimeout(Duration.ofSeconds(15)); // 10 -> 15 saniye
        options.setScriptTimeout(Duration.ofSeconds(30));

        options.addArguments(
                "--disable-cookie-encryption",
                "--allow-running-insecure-content"
        );

        return new ChromeDriver(options);
    }

    private static FirefoxDriver createFirefoxDriver(boolean isHeadless) {
        FirefoxOptions options = new FirefoxOptions();

        if (isHeadless) {
            options.addArguments("--headless");
        }

        return new FirefoxDriver(options);
    }

    private static EdgeDriver createEdgeDriver() {
        checkOS("windows", "Edge");
        return new EdgeDriver();
    }

    private static SafariDriver createSafariDriver() {
        checkOS("mac", "Safari");
        return new SafariDriver();
    }

    private static void setupDriver(WebDriver driver) {
        if (ConfigReader.getBoolean("fullscreen")) {
            driver.manage().window().maximize();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getInt("implicit.wait")));
    }

    private static void checkOS(String requiredOS, String browser) {
        boolean isCorrectOS = System.getProperty("os.name").toLowerCase().contains(requiredOS);
        if (!isCorrectOS) {
            throw new UnsupportedOperationException("Your OS doesn't support " + browser);
        }
    }

    public static synchronized void closeDriver() {
        try {
            if (driverPool.get() != null) {
                driverPool.get().quit();
                driverPool.remove();
            }
        } catch (Exception e) {
            logger.error("Error closing driver: {}", e.getMessage());
        }
    }
}