package drTest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.MultiPartEmail;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class errorScreenShot {

//TODO:Bu kod test hata verdiğinde ekran fotoğrafı çekip ekran fotoğrafını maille yollamaya yarıyor
//TODO:Hata verdirdiğim yer ise Yeni çıkanlar kategorisinde bir ürünün ürün detayına gidip
// TODO:Ürünü Sepete eklemeye çalışıyor ancak sepete ekle butonunun üstüne Çerez bildirimi geldiği için buton kullanılamıyor
    //TODO:ve hata veriyor ss çekip mail yolluyor.

        public static void main(String[] args) throws InterruptedException {
            System.setProperty("webdriver.chrome.driver", "drivers/chromewebdriver/chromedriver.exe");
            WebDriver driver = new ChromeDriver();

            driver.manage().window().maximize();
            driver.navigate().to("https://www.dr.com.tr/");



            // Recipient's email ID needs to be mentioned.//Alıcı
            String to = "errordr123123@gmail.com";

            // Sender's email ID needs to be mentioned//Gönderen
            String from = "errordr123123@gmail.com";

            // Assuming you are sending email from through gmails smtp
            String host = "smtp.gmail.com";

            // Get system properties
            Properties properties = System.getProperties();

            // Setup mail server
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            // Get the Session object.// and pass username and password //Anlamı Session'u alın kullanıcı adı ve parolayı geçirin
            // Session sınıfı, JavaMail API'sinde e-posta oturumlarını yönetmek için kullanılır.
            // properties nesnesi, SMTP sunucusuyla ilgili yapılandırma bilgilerini içerir.
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {


                // getPasswordAuthentication metodu, SMTP sunucusuna kimlik doğrulaması sağlamak için kullanılır.
                // Bu metod, kimlik doğrulaması gerektiğinde çağrılır ve bir PasswordAuthentication nesnesi döner.
                protected PasswordAuthentication getPasswordAuthentication() {


                    // E-posta adresi ve uygulama şifresini kullanarak PasswordAuthentication nesnesi oluşturulur.
                    // "errordr123123@gmail.com" e-posta adresi, "brhxrifqhykwhhte" ise uygulama şifresidir.
                    return new PasswordAuthentication("errordr123123@gmail.com", "brhxrifqhykwhhte");

                }

            });

            // Used to debug SMTP issues
            // SMTP sorunlarını tanılamak için oturum üzerinde hata ayıklama (debug) modu etkinleştirilir.
            // Bu, SMTP işlemine ait ayrıntılı logların konsolda görüntülenmesini sağlar.
            session.setDebug(true);


            try {
                // Test işlemleri burada yapılır

                //Buradaki try catch yapısında cookie ve üstten gelen bildirim kapatılıyor
                Thread.sleep(7000);
                try {
                    // WebElement cookie = driver.findElement(By.xpath("//a[@id='CybotCookiebotDialogBodyButtonDecline']"));

                    //cookie.click();//TODO:geri açmayı unutma

                    try {
                        WebElement bildirim = driver.findElement(By.xpath("//div[@class=\"dn-slide-buttons horizontal\"]/button[@class=\"dn-slide-deny-btn\"]"));
                        bildirim.click();
                    }
                    catch (NoSuchElementException e){
                        System.out.println("Bildirimler Gelmedi");
                    }
                }
                catch (NoSuchElementException e)
                {

                    System.out.println("Çerezler Gelmedi");
                }

                //burada Kitapın alt kategorilerini for döngüsüne aldım , her bir alt kategoriye gidiyorum
                for(int i=1 ; i<12 ; i++){
                    Thread.sleep(1000);
                    WebElement category = driver.findElement(By.xpath(
                            "(//a[@class=\"pointer-events-auto\"])[1]"));
                    Actions touch =new Actions(driver);
                    touch.moveToElement(category).perform();

                    //yeniCikanlar adlı alt kategoriye gidip scroll yapıp, oradan bir ürün'ün ürün detayına gidip scroll yapıp sepete ekliyor
                    if(i==1)
                    {



                        Thread.sleep(2000);
                        WebElement yeniCikanlar = driver.findElement(By.xpath("(//li/a[@target=\"_self\"])[9]"));
                        yeniCikanlar.click();

                        WebElement videoFrame = driver.findElement(By.cssSelector("div:nth-child(3) > div > div.prd-content > div.prd-content-wrapper > div.prd-infos > h3:nth-child(1) > a"));

                        Actions action = new Actions(driver);
                        action.moveToElement(videoFrame).perform();
                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();

                        Thread.sleep(2000);
                        WebElement yeniCikanKitap = driver.findElement(By.cssSelector("div:nth-child(3) > div > div.prd-content > div.prd-content-wrapper > div.prd-infos > h3:nth-child(1) > a"));
                        yeniCikanKitap.click();


                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();
                        Thread.sleep(2000);

                        WebElement sepeteEkle1 =  driver.findElement(By.xpath("//button[@class=\"col-12 d-block py-10 btn text-c255 text-center fs-5 js-btn-detail-basket js-add-basket\"]"));

                        //Buradaki olay şu üründetayda sepeteeklenin üstüne cookie geliyor bu yüzden hata veriyor ben de bu hata anının
                        //screen shot'ını aldırdım
                        try {
                            sepeteEkle1.click();
                            Thread.sleep(3000);
                            WebElement alisveriseDevamet1 = driver.findElement(By.xpath("//button[@class=\"btn basket-modal__btn-more border font-weight-600 p-10 text-center js-basket-modal-close\"]"));
                            alisveriseDevamet1.click();

                            driver.navigate().to("https://www.dr.com.tr/");



                        }
                        catch (Exception a)
                        {
                            Thread.sleep(3000);


                            //1. TakeScreenShot objesi oluşturuyorum
                            TakesScreenshot tss =(TakesScreenshot)  driver;
                            //2: Resmi Kaydedeceğimiz dosyayı oluşturacağız
                            File tumSayfaSS = new File("target/screenshot/error.png");
                            //3.Bir dosya daha oluşturacağız ve screenshot objesi ile ekran goruntusu alma işlem yapacağız
                            File geciciResim = tss.getScreenshotAs(OutputType.FILE);
                            //4. GeciciResmi kaydetmek istedigimiz asil dosyaya copy islemi yapilir.
                            FileUtils.copyFile(geciciResim,tumSayfaSS);//FileUtils hata veriyodu pom.xml'e eklenti yerleştirdim

                            try {
                                // Create a default MimeMessage object.
                                // Varsayılan bir MimeMessage nesnesi oluşturulur.
                                MimeMessage message = new MimeMessage(session);

                                // Set From: header field of the header.
                                // From başlık alanı ayarlanır.
                                message.setFrom(new InternetAddress(from));

                                // Set To: header field of the header.
                                // To başlık alanı ayarlanır.
                                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                                // Set Subject: header field
                                //message.setSubject("This is the Subject Line!");

                                // Now set the actual message
                                //message.setText("This is actual message");

                                //System.out.println("sending...");
                                // Send message
                                //Transport.send(message);

                                //System.out.println("Sent message successfully....");

                                // Multipart nesnesi oluşturulur (E-posta gövdesi ve ekleri içerecek).
                                Multipart multipart = new MimeMultipart();

                                // E-posta eki için MimeBodyPart nesnesi oluşturulur.
                                MimeBodyPart attachmentPart = new MimeBodyPart();

                                // E-posta metni için MimeBodyPart nesnesi oluşturulur.
                                MimeBodyPart textPart = new MimeBodyPart();

                                try {

                                    // Eklenecek dosya tanımlanır.
                                    File f =new File("target/screenshot/error.png");

                                    // Dosya ek olarak MimeBodyPart nesnesine eklenir.
                                    attachmentPart.attachFile(f);

                                    // E-posta metni ayarlanır.
                                    textPart.setText("This is text");

                                    // E-posta metni Multipart nesnesine eklenir.
                                    multipart.addBodyPart(textPart);

                                    // Dosya eki Multipart nesnesine eklenir.
                                    multipart.addBodyPart(attachmentPart);

                                }catch (IOException e) {

                                    // Dosya eklenirken bir hata oluşursa, hata yığını çıktılanır.
                                    e.printStackTrace();

                                }

                                // MimeMessage nesnesine Multipart içeriği eklenir.
                                message.setContent(multipart);

                                // Mesajın gönderileceğini belirten çıktı.
                                System.out.println("sending...");


                                // Send message
                                // Mesaj gönderilir.
                                Transport.send(message);

                                // Mesajın başarıyla gönderildiğini belirten çıktı.
                                System.out.println("Sent message successfully....");
                            } catch (MessagingException mex) {
                                // Mesaj gönderilirken bir hata oluşursa, hata yığını çıktılanır.
                                mex.printStackTrace();
                            }
                        }
                        /*

                         */
                    }
                    //cokSatanlar adlı alt kategoriye gidip scroll yapıp, oradan bir ürün'ün ürün detayına gidip scroll yapıp sepete ekliyor
                    else if(i==2)
                    {
                        Thread.sleep(2000);
                        WebElement cokSatanlar = driver.findElement(By.xpath("(//li/a[@target=\"_self\"])[10]"));
                        cokSatanlar.click();

                        WebElement videoFrame = driver.findElement(By.xpath("//a[@title=\"Rezonans Kanunu\"]"));

                        Actions action = new Actions(driver);
                        action.moveToElement(videoFrame).perform();
                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();

                        Thread.sleep(2000);
                        WebElement cokSatanKitap = driver.findElement(By.xpath("//a[@title=\"Rezonans Kanunu\"]"));
                        cokSatanKitap.click();


                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();
                        Thread.sleep(2000);

                        WebElement sepeteEkle2 =  driver.findElement(By.xpath("//button[@class=\"col-12 d-block py-10 btn text-c255 text-center fs-5 js-btn-detail-basket js-add-basket\"]"));
                        sepeteEkle2.click();

                        Thread.sleep(3000);
                        WebElement alisveriseDevamet2 = driver.findElement(By.xpath("//button[@class=\"btn basket-modal__btn-more border font-weight-600 p-10 text-center js-basket-modal-close\"]"));
                        alisveriseDevamet2.click();

                        driver.navigate().to("https://www.dr.com.tr/");

                    }
                    //edebiyat adlı alt kategoriye gidip scroll yapıp, oradan bir ürün'ün ürün detayına gidip scroll yapıp sepete ekliyor
                    else if(i==3)
                    {
                        Thread.sleep(2000);
                        WebElement edebiyat = driver.findElement(By.xpath("(//li/a[@target=\"_self\"])[11]"));
                        edebiyat.click();

                        WebElement videoFrame = driver.findElement(By.xpath("//a[@title=\"Salkım Sokak No: 3\"]"));

                        Actions action = new Actions(driver);
                        action.moveToElement(videoFrame).perform();
                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();

                        Thread.sleep(2000);
                        WebElement edebiyatKitap = driver.findElement(By.xpath("//a[@title=\"Salkım Sokak No: 3\"]"));
                        edebiyatKitap.click();


                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();
                        Thread.sleep(2000);

                        WebElement sepeteEkle3 =  driver.findElement(By.xpath("//button[@class=\"col-12 d-block py-10 btn text-c255 text-center fs-5 js-btn-detail-basket js-add-basket\"]"));
                        sepeteEkle3.click();

                        Thread.sleep(3000);
                        WebElement alisveriseDevamet3 = driver.findElement(By.xpath("//button[@class=\"btn basket-modal__btn-more border font-weight-600 p-10 text-center js-basket-modal-close\"]"));
                        alisveriseDevamet3.click();

                        driver.navigate().to("https://www.dr.com.tr/");

                    }
                    //roman adlı alt kategoriye gidip scroll yapıp, oradan bir ürün'ün ürün detayına gidip scroll yapıp sepete ekliyor
                    else if(i==4)
                    {
                        Thread.sleep(2000);
                        WebElement roman = driver.findElement(By.xpath("(//li/a[@target=\"_self\"])[12]"));
                        roman.click();

                        WebElement videoFrame = driver.findElement(By.xpath("//a[@title=\"Yaşamak\"]"));

                        Actions action = new Actions(driver);
                        action.moveToElement(videoFrame).perform();
                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();

                        Thread.sleep(2000);
                        WebElement romanKitap = driver.findElement(By.xpath("//a[@title=\"Yaşamak\"]"));
                        romanKitap.click();


                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();
                        Thread.sleep(2000);

                        WebElement sepeteEkle4 =  driver.findElement(By.xpath("//button[@class=\"col-12 d-block py-10 btn text-c255 text-center fs-5 js-btn-detail-basket js-add-basket\"]"));
                        sepeteEkle4.click();

                        Thread.sleep(3000);
                        WebElement alisveriseDevamet4 = driver.findElement(By.xpath("//button[@class=\"btn basket-modal__btn-more border font-weight-600 p-10 text-center js-basket-modal-close\"]"));
                        alisveriseDevamet4.click();

                        driver.navigate().to("https://www.dr.com.tr/");

                    }
                    //kisiselGelişim adlı alt kategoriye gidip scroll yapıp, oradan bir ürün'ün ürün detayına gidip scroll yapıp sepete ekliyor
                    else if(i==5)
                    {
                        Thread.sleep(2000);
                        WebElement kisiselGelisim = driver.findElement(By.xpath("(//li/a[@target=\"_self\"])[13]"));
                        kisiselGelisim.click();

                        WebElement videoFrame = driver.findElement(By.xpath("//a[@title=\"Bilinçaltının Gücü - Zihinsel ve Bedensel İyileşme\"]"));

                        Actions action = new Actions(driver);
                        action.moveToElement(videoFrame).perform();
                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();

                        Thread.sleep(2000);
                        WebElement kisiselGelisimKitap = driver.findElement(By.xpath("//a[@title=\"Bilinçaltının Gücü - Zihinsel ve Bedensel İyileşme\"]"));
                        kisiselGelisimKitap.click();


                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();
                        Thread.sleep(2000);

                        WebElement sepeteEkle5 =  driver.findElement(By.xpath("//button[@class=\"col-12 d-block py-10 btn text-c255 text-center fs-5 js-btn-detail-basket js-add-basket\"]"));
                        sepeteEkle5.click();

                        Thread.sleep(3000);
                        WebElement alisveriseDevamet5 = driver.findElement(By.xpath("//button[@class=\"btn basket-modal__btn-more border font-weight-600 p-10 text-center js-basket-modal-close\"]"));
                        alisveriseDevamet5.click();

                        driver.navigate().to("https://www.dr.com.tr/");

                    }
                    //cocukVeGençlik adlı alt kategoriye gidip scroll yapıp, oradan bir ürün'ün ürün detayına gidip scroll yapıp sepete ekliyor
                    else if(i==6)
                    {
                        Thread.sleep(2000);
                        WebElement cocukVeGenclik = driver.findElement(By.xpath("(//li/a[@target=\"_self\"])[14]"));
                        cocukVeGenclik.click();


                        WebElement videoFrame = driver.findElement(By.xpath("//a[@title=\"Çantamdan Fil Çıktı\"]"));

                        Actions action = new Actions(driver);
                        action.moveToElement(videoFrame).perform();
                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();

                        Thread.sleep(2000);
                        WebElement cocukVeGenclikKitap = driver.findElement(By.xpath("//a[@title=\"Çantamdan Fil Çıktı\"]"));
                        cocukVeGenclikKitap.click();


                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();
                        Thread.sleep(2000);

                        WebElement sepeteEkle6 =  driver.findElement(By.xpath("//button[@class=\"col-12 d-block py-10 btn text-c255 text-center fs-5 js-btn-detail-basket js-add-basket\"]"));
                        sepeteEkle6.click();

                        Thread.sleep(3000);
                        WebElement alisveriseDevamet6 = driver.findElement(By.xpath("//button[@class=\"btn basket-modal__btn-more border font-weight-600 p-10 text-center js-basket-modal-close\"]"));
                        alisveriseDevamet6.click();

                        driver.navigate().to("https://www.dr.com.tr/");

                    }
                    //arastirmaVeTarih adlı alt kategoriye gidip scrollyapıp, oradan bir ürün'ün ürün detayına gidip scroll yapıp sepete ekliyor
                    else if(i==7)
                    {
                        Thread.sleep(2000);
                        WebElement arastirmaVeTarih = driver.findElement(By.xpath("(//li/a[@target=\"_self\"])[15]"));
                        arastirmaVeTarih.click();

                        WebElement videoFrame = driver.findElement(By.xpath("//a[@title=\"Nutuk - Gençler İçin Fotoğraflarla\"]"));

                        Actions action = new Actions(driver);
                        action.moveToElement(videoFrame).perform();
                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();

                        Thread.sleep(2000);
                        WebElement arastirmaVeTarihKitap = driver.findElement(By.xpath("//a[@title=\"Nutuk - Gençler İçin Fotoğraflarla\"]"));
                        arastirmaVeTarihKitap.click();


                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();
                        Thread.sleep(2000);

                        WebElement sepeteEkle7 =  driver.findElement(By.xpath("//button[@class=\"col-12 d-block py-10 btn text-c255 text-center fs-5 js-btn-detail-basket js-add-basket\"]"));
                        sepeteEkle7.click();

                        Thread.sleep(3000);
                        WebElement alisveriseDevamet7 = driver.findElement(By.xpath("//button[@class=\"btn basket-modal__btn-more border font-weight-600 p-10 text-center js-basket-modal-close\"]"));
                        alisveriseDevamet7.click();

                        driver.navigate().to("https://www.dr.com.tr/");

                    }
                    //yabancidil adlı alt kategoriye gidip scrollyapıp, oradan bir ürün'ün ürün detayına gidip scroll yapıp sepete ekliyor
                    else if(i==8)
                    {
                        Thread.sleep(2000);
                        WebElement yabancidil = driver.findElement(By.xpath("(//li/a[@target=\"_self\"])[16]"));
                        try {
                            yabancidil.click();
                        }
                        catch (Exception e)
                        {
                            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                            // Ekran görüntüsünü dosyaya kaydet
                            FileUtils.copyFile(screenshotFile, new File("//target/screenshot/error_screenshot.png"));

                        }

                        WebElement videoFrame = driver.findElement(By.xpath("//a[@title=\"English Grammar Today Türkçe Açıklamalı İngilizce Gramer\"]"));

                        Actions action = new Actions(driver);
                        action.moveToElement(videoFrame).perform();
                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();

                        Thread.sleep(2000);
                        WebElement yabancidilKitap = driver.findElement(By.xpath("//a[@title=\"English Grammar Today Türkçe Açıklamalı İngilizce Gramer\"]"));
                        yabancidilKitap.click();


                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();
                        Thread.sleep(2000);

                        WebElement sepeteEkle8 =  driver.findElement(By.xpath("//button[@class=\"col-12 d-block py-10 btn text-c255 text-center fs-5 js-btn-detail-basket js-add-basket\"]"));
                        sepeteEkle8.click();

                        Thread.sleep(3000);
                        WebElement alisveriseDevamet8 = driver.findElement(By.xpath("//button[@class=\"btn basket-modal__btn-more border font-weight-600 p-10 text-center js-basket-modal-close\"]"));
                        alisveriseDevamet8.click();

                        driver.navigate().to("https://www.dr.com.tr/");

                    }
                    //cizgiRoman adlı alt kategoriye gidip scrollyapıp, oradan bir ürün'ün ürün detayına gidip scroll yapıp sepete ekliyor
                    else if(i==9)
                    {
                        Thread.sleep(2000);
                        WebElement cizgiRoman = driver.findElement(By.xpath("(//li/a[@target=\"_self\"])[17]"));
                        cizgiRoman.click();

                        WebElement videoFrame = driver.findElement(By.xpath("//a[@title=\"The Boys Cilt 1 - Oyunun Adı\"]"));

                        Actions action = new Actions(driver);
                        action.moveToElement(videoFrame).perform();
                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();

                        Thread.sleep(2000);
                        WebElement cizgiRomanKitap = driver.findElement(By.xpath("//a[@title=\"The Boys Cilt 1 - Oyunun Adı\"]"));
                        cizgiRomanKitap.click();


                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();
                        Thread.sleep(2000);

                        WebElement sepeteEkle9 =  driver.findElement(By.xpath("//button[@class=\"col-12 d-block py-10 btn text-c255 text-center fs-5 js-btn-detail-basket js-add-basket\"]"));
                        sepeteEkle9.click();

                        Thread.sleep(3000);
                        WebElement alisveriseDevamet9 = driver.findElement(By.xpath("//button[@class=\"btn basket-modal__btn-more border font-weight-600 p-10 text-center js-basket-modal-close\"]"));
                        alisveriseDevamet9.click();

                        driver.navigate().to("https://www.dr.com.tr/");

                    }
                    //felsefe adlı alt kategoriye gidip scrollyapıp, oradan bir ürün'ün ürün detayına gidip scroll yapıp sepete ekliyor
                    else if(i==10)
                    {

                        Thread.sleep(2000);
                        WebElement felsefe = driver.findElement(By.xpath("(//li/a[@target=\"_self\"])[18]"));
                        felsefe.click();

                        WebElement videoFrame = driver.findElement(By.xpath("//a[@title=\"İyinin ve Kötünün Ötesinde - Gelecekteki Bir Felseye Giriş\"]"));


                        Actions action = new Actions(driver);
                        action.moveToElement(videoFrame).perform();
                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();

                        Thread.sleep(2000);
                        WebElement felsefeKitap = driver.findElement(By.xpath("//a[@title=\"İyinin ve Kötünün Ötesinde - Gelecekteki Bir Felseye Giriş\"]"));
                        felsefeKitap.click();


                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();
                        Thread.sleep(2000);

                        WebElement sepeteEkle10 =  driver.findElement(By.xpath("//button[@class=\"col-12 d-block py-10 btn text-c255 text-center fs-5 js-btn-detail-basket js-add-basket\"]"));
                        sepeteEkle10.click();

                        Thread.sleep(3000);
                        WebElement sepetegit = driver.findElement(By.xpath("//button[@class=\"btn basket-modal__btn-more border font-weight-600 p-10 text-center js-basket-modal-close\"]"));
                        sepetegit.click();

                        driver.navigate().to("https://www.dr.com.tr/");


                    }

                    //ekitap adlı alt kategoriye gidip scrollyapıp, oradan bir ürün'ün ürün detayına gidip scroll yapıp sepete ekliyor
                    //ardından sepete gidip bazı ürünlerin adetini arttırıp ardından sepeti temizliyor.
                    else if (i==11){

                        Thread.sleep(2000);
                        WebElement ekitap = driver.findElement(By.xpath("(//li/a[@target=\"_self\"])[19]"));
                        ekitap.click();

                        WebElement videoFrame = driver.findElement(By.cssSelector("div:nth-child(6) > div > div > div > div.carousel-slider.js-datalayer-carousel-slider.slick-initialized.slick-slider > div > div > div:nth-child(8) > div > div > div.prd-content > div.prd-content-wrapper > div.prd-infos > h3:nth-child(1) > a"));


                        Actions action = new Actions(driver);
                        action.moveToElement(videoFrame).perform();
                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();

                        Thread.sleep(2000);
                        WebElement felsefeKitap = driver.findElement(By.cssSelector("div:nth-child(6) > div > div > div > div.carousel-slider.js-datalayer-carousel-slider.slick-initialized.slick-slider > div > div > div:nth-child(8) > div > div > div.prd-content > div.prd-content-wrapper > div.prd-infos > h3:nth-child(1) > a"));
                        felsefeKitap.click();



                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();
                        Thread.sleep(2000);

                        WebElement sepeteEkle10 =  driver.findElement(By.xpath("//button[@class=\"col-12 d-block py-10 btn text-c255 text-center fs-5 js-btn-detail-basket js-add-basket\"]"));
                        sepeteEkle10.click();

                        Thread.sleep(4000);
                        WebElement sepetegit = driver.findElement(By.xpath("//button[@class=\"btn basket-modal__btn-more border font-weight-600 p-10 text-center js-basket-modal-close\"]"));
                        sepetegit.click();

                        driver.navigate().to("https://www.dr.com.tr/Sepetim");



                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();

                        Thread.sleep(4000);
                        WebElement suutGoolArttir1 = driver.findElement(By.cssSelector("section#cart-container div:nth-child(2) > div.col-12.col-lg-5.flexCol.no-relative > div > div.col-4.col-lg-5.flexOrderLast > div > div > div > span.number-plus"));
                        suutGoolArttir1.click();

                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();

                        Thread.sleep(4000);
                        WebElement suutGoolArttir2 = driver.findElement(By.cssSelector("section#cart-container div:nth-child(2) > div.col-12.col-lg-5.flexCol.no-relative > div > div.col-4.col-lg-5.flexOrderLast > div > div > div > span.number-plus"));
                        suutGoolArttir2.click();


                        Thread.sleep(4000);
                        WebElement suutGoolArttir3 = driver.findElement(By.cssSelector("section#cart-container div:nth-child(2) > div.col-12.col-lg-5.flexCol.no-relative > div > div.col-4.col-lg-5.flexOrderLast > div > div > div > span.number-plus"));
                        suutGoolArttir3.click();

                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();

                        Thread.sleep(4000);
                        WebElement salkimSokakNo1 = driver.findElement(By.cssSelector("section#cart-container div:nth-child(4) > div.col-12.col-lg-5.flexCol.no-relative > div > div.col-4.col-lg-5.flexOrderLast > div > div > div > span.number-plus"));
                        salkimSokakNo1.click();

                        Thread.sleep(2000);
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();

                        Thread.sleep(4000);
                        WebElement salkimSokakNo2 = driver.findElement(By.cssSelector("section#cart-container div:nth-child(4) > div.col-12.col-lg-5.flexCol.no-relative > div > div.col-4.col-lg-5.flexOrderLast > div > div > div > span.number-plus"));
                        salkimSokakNo2.click();


                        Thread.sleep(4000);
                        WebElement bilincAltininGucu1 = driver.findElement(By.cssSelector("section#cart-container div:nth-child(6) > div.col-12.col-lg-5.flexCol.no-relative > div > div.col-4.col-lg-5.flexOrderLast > div > div > div > span.number-plus"));
                        bilincAltininGucu1.click();


                        Thread.sleep(4000);
                        WebElement EnglishGrammer1 = driver.findElement(By.cssSelector("section#cart-container div:nth-child(9) > div.col-12.col-lg-5.flexCol.no-relative > div > div.col-4.col-lg-5.flexOrderLast > div > div > div > span.number-plus"));
                        EnglishGrammer1.click();
                        Thread.sleep(2000);


                        //Sepette ki Ürünlerin tümünü seçiyo
                        WebElement silSec = driver.findElement(By.cssSelector("section#cart-container div.col-1.col-sm-1 > label > span"));
                        action.moveToElement(silSec).perform();
                        Thread.sleep(2000);
                        silSec.click();
                        Thread.sleep(5000);


                        //Sepette ki ürünleri siliyo
                        WebElement silButon = driver.findElement(By.cssSelector("section#cart-container a.totalRemove"));
                        action.moveToElement(silButon).perform();
                        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();
                        Thread.sleep(2000);
                        silButon.click();
                        Thread.sleep(2000);


                        //Sepetteki ürünleri sildikten sonra sayfa da yukarı çıkıyo
                        action.sendKeys(Keys.PAGE_UP).perform();
                        action.sendKeys(Keys.PAGE_UP).perform();
                        action.sendKeys(Keys.PAGE_UP).perform();

                    }

                }

            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
    }




