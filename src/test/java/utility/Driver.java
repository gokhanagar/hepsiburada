package utility;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

public class Driver {
    private Driver() {
        // Singleton pattern
    }

    private static final ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();
    
    public static WebDriver getDriver() {
        if (driverPool.get() == null) {
            synchronized (Driver.class) {
                String browser = System.getProperty("browser", "chrome");
                boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));
                
                switch (browser.toLowerCase()) {
                    case "chrome" -> driverPool.set(createChromeDriver(isHeadless));
                    case "firefox" -> driverPool.set(createFirefoxDriver(isHeadless));
                    case "edge" -> driverPool.set(createEdgeDriver());
                    case "safari" -> driverPool.set(createSafariDriver());
                    default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
                }
                
                setupDriver(driverPool.get());
            }
        }
        return driverPool.get();
    }

    private static ChromeDriver createChromeDriver(boolean isHeadless) {
        ChromeOptions options = new ChromeOptions();
        
        // Temel ayarlar
        options.addArguments(
            "--remote-allow-origins=*",
            "--disable-notifications",
            "--window-size=1920,1080",
            "--disable-popup-blocking",
            "--disable-blink-features=AutomationControlled",  // Automation bayrağını gizle
            "--disable-infobars",
            "--disable-extensions"
        );
        
        // Bot tespitini engelleme
        options.addArguments("--disable-blink-features=AutomationControlled");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        
        // Automation flags'i gizle
        options.setExperimentalOption("excludeSwitches", 
            new String[]{"enable-automation", "enable-logging"});
        options.setExperimentalOption("useAutomationExtension", false);
        
        // User agent değiştir
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36");

        if (isHeadless) {
            options.addArguments(
                "--headless=new",
                "--disable-gpu",
                "--no-sandbox",
                "--disable-dev-shm-usage"
            );
        }
        
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
        boolean isFullScreen = Boolean.parseBoolean(System.getProperty("fullscreen", "true"));
        
        if (isFullScreen) {
            driver.manage().window().maximize();
        }
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    private static void checkOS(String requiredOS, String browser) {
        boolean isCorrectOS = System.getProperty("os.name").toLowerCase().contains(requiredOS);
        if (!isCorrectOS) {
            throw new UnsupportedOperationException("Your OS doesn't support " + browser);
        }
    }

    public static void closeDriver() {
        if (driverPool.get() != null) {
            driverPool.get().quit();
            driverPool.remove();
        }
    }
}