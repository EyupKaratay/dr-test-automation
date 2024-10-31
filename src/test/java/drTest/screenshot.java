package drTest;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.time.Duration;
import java.io.IOException;


public class screenshot {
    public static  void main(String[] args) throws IOException {


        //TODO:Bu kod search yapıp ekran fotoğrafı çekiyor mail yollama yok

        System.setProperty("webdriver.chrome.driver", "drivers/chromewebdriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.navigate().to("https://www.dr.com.tr/");

        //Search alanına gidip apple yazıp ürünlerin fotosunu çekecek
        WebElement searchBox = driver.findElement(By.cssSelector("div.search-wrapper.col-12.col-lg-10.p-0 > input"));
        searchBox.sendKeys("Apple", Keys.ENTER);

        //1. TakeScreenShot objesi oluşturuyorum
        TakesScreenshot tss =(TakesScreenshot)  driver;
        //2: Resmi Kaydedeceğimiz dosyayı oluşturacağız
        File tumSayfaSS = new File("target/screenshot/tumSayfaSS.png");
        //3.Bir dosya daha oluşturacağız ve screenshot objesi ile ekran goruntusu alma işlem yapacağız
        File geciciResim = tss.getScreenshotAs(OutputType.FILE);
        //4. GeciciResmi kaydetmek istedigimiz asil dosyaya copy islemi yapilir.
        FileUtils.copyFile(geciciResim,tumSayfaSS);//FileUtils hata veriyodu pom.xml'e eklenti yerleştirdim

        //driver'i kapat
        driver.quit();

    }
}
