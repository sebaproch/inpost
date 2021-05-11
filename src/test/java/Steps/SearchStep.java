package Steps;


import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(Cucumber.class)
public class SearchStep {
    List<List<String>> data;
    public WebDriver driver;
    private String addURI;
    private Response response;



    @Given("^Otworzenie ekranu początkowego strony$")
    public void otworzenieEkranuPoczątkowegoStrony() {

        System.out.println("Otwarcie początku strony");
        System.setProperty( "webdriver.chrome.driver", "C:\\libs\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait( 10, TimeUnit.SECONDS );
        driver.get("https://inpost.pl/");
        driver.findElement(By.xpath("//*[@id='onetrust-accept-btn-handler']")).click();
    }

    @And("^Wyszukanie paczki o numerze as$")
    public void wyszukaniePaczkiONumerzeAs(DataTable table) {
        List<List<String>> data = table.raw();

        System.out.println("Wyszukanie paczki o numerze " + data.get(1).get(0));
        System.out.println("Wyszukanie paczki o numerze " + data.get(1).get(1));
        System.out.println("Wyszukanie paczki o numerze " + data.get(1).get(2));

        driver.findElement(By.cssSelector("a.btn--tracking--action.-withwave")).click();
        driver.findElement(By.cssSelector("input[class*='form--control -md']")).sendKeys(data.get(1).get(0));
        driver.findElement(By.cssSelector("button[class*='btn--primary h-100 w-100 d-none d-md-block']")).click();


    }

    @When("^Sprawdzenie statusu paczek$")
    public void sprawdzenieStatusuPaczek() {

        System.out.println("Sprawdzenie statusy paczki");
        driver.findElement(By.cssSelector("p[class*='paragraph--component -big -secondary']")).getText();
        String status = driver.findElement(By.cssSelector("p[class*='paragraph--component -big -secondary']")).getText();
        System.out.println(status);
        Assert.assertTrue(status.equals("Dostarczona."));


    }

    @Then("^Powinienem znać statusy paczek$")
    public void powinienemZnaćStatusyPaczek() {
        System.out.println("Powinienem znać statusy paczek");
    }






    @Given("^Otworzenie ekranu dla stanowiska pracy$")
    public void otworzenieEkranuDlaStanowiskaPracy() {
        System.out.println("Otworzenie ekranu dla stanowiska pracy");
        System.setProperty( "webdriver.chrome.driver", "C:\\libs\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait( 10, TimeUnit.SECONDS );
        driver.get("https://inpost.pl/kariera");
        driver.findElement(By.xpath("//*[@id='onetrust-accept-btn-handler']")).click();

    }

    @And("^Wypełnienie formularza aplikacyjnego z modelu aktora$")
    public void wypełnienieFormularzaAplikacyjnegoZModeluAktora() throws Exception {
        driver.findElement(By.xpath("//input[@id='filter-position']")).sendKeys("Tester");
        driver.findElement(By.cssSelector("button[class*='btn--primary jobsSelectTrigger']")).click();
        String parentHandle = driver.getWindowHandle();
        driver.findElement(By.xpath("//a[@class='link--component -smallmobile'][1]")).click();//przeniesienie na inna strone
        Set<String> handles = driver.getWindowHandles();
        for (String handle: handles){
            System.out.println(handle);
            if (!handle.equals(parentHandle)){
                driver.switchTo().window(handle);
                Thread.sleep(2000);
            }
        }
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,1900)");
        Thread.sleep(2000);
        String secondParentHandle = driver.getWindowHandle();
        WebElement apply = driver.findElement(By.xpath("//*[@id=\"offCont\"]/div[4]/a/img"));
        try {
            apply.isDisplayed();
            apply.click();
        } catch (Exception ex) {
            throw new Exception( "Element is not visible" );
        }
        Set<String> secondhandles = driver.getWindowHandles();
        for (String handle: secondhandles) {
            System.out.println(handle);
            if (!handle.equals(parentHandle)) {
                if (!handle.equals(secondParentHandle))
                    driver.switchTo().window(handle);
                    Thread.sleep(2000);
            }
        }


    }

    @When("^Załączenie dowolnego pliku jako CV$")
    public void załączenieDowolnegoPlikuJakoCV() {
        WebElement uploadFileInput = driver.findElement(By.xpath("//*[@id='ctl00_DefaultContent_ctl45_fuCv']"));
        String expectedFileName = "CvTest.odt";
        String path = "G:\\" + expectedFileName;
        uploadFileInput.sendKeys(path);
    }

    @Then("^Full page screenshot z wypełnionego formularza \\(bez wysyłania\\)$")
    public void fullPageScreenshotZWypełnionegoFormularzaBezWysyłania() throws IOException {

        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(ShootingStrategies.scaling(2f),1000)).takeScreenshot(driver);
        ImageIO.write(screenshot.getImage(), "PNG",new File("Screenshot.png"));


    }

    @Given("^Ustanowienie GET API endpoint$")
    public void ustanowienieGETAPIEndpoint() {
        addURI = "https://api-shipx-pl.easypack24.net/v1/organizations/35838/";
        System.out.println("Add URL :"+addURI);
    }

    @And("^Wysłałem zapytanie GET HTTTP$")
    public void wysłałemZapytanieGETHTTTP() {
        RestAssured.baseURI = addURI;
        RequestSpecification request = RestAssured.given();
        String token = "YourPrivateToken";
        request.header("Authorization", "Bearer " + token);
        request.header("Content-Type", "application/json");
        response = request.get();
        String jsonString = response.asString();
        System.out.println(jsonString);
        LinkedHashMap<String,String> adres = JsonPath.from(jsonString).get("address");
        System.out.println(adres);
        String city = adres.get("city");
        System.out.println(city);
        Assert.assertEquals("Kraków",city);
    }

    @When("^Otrzymałem odpowiedź odnośnie paczkomatów$")
    public void otrzymałemOdpowiedźOdnośniePaczkomatów() {
    }

    @Then("^Upewniłem się że paczkomaty są z Krakowa$")
    public void upewniłemSięŻePaczkomatySąZKrakowa() {
    }
}
