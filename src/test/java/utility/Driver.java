package utility;

import java.time.Duration;
import java.util.Arrays;
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
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--window-size=1920,1080");
        
        // Cookie ve popup ayarları
        options.addArguments(
            "--disable-popup-blocking",
            "--disable-infobars",
            "--disable-gpu",
            "--disable-extensions",
            "--no-sandbox",
            "--disable-dev-shm-usage"
        );
        
        // Cookie tercihlerini ayarla
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.cookies", 1);
        prefs.put("profile.cookie_controls_mode", 1);
        prefs.put("profile.block_third_party_cookies", false);
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("profile.default_content_setting_values.notifications", 1);
        options.setExperimentalOption("prefs", prefs);
        
        // Ek Chrome flags
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        
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