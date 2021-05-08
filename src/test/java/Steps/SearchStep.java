package Steps;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.lu.a;
import cucumber.api.junit.Cucumber;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(Cucumber.class)
public class SearchStep {
    List<List<String>> data;
    public WebDriver driver;

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
    public void wypełnienieFormularzaAplikacyjnegoZModeluAktora() {
        driver.findElement(By.xpath("//input[@id='filter-position']")).sendKeys("Tester");
        driver.findElement(By.cssSelector("button[class*='btn--primary jobsSelectTrigger']")).click();
        driver.findElement(By.xpath("//a[@class='link--component -smallmobile'][1]")).click();

    }

    @When("^Załączenie dowolnego pliku jako CV$")
    public void załączenieDowolnegoPlikuJakoCV() {
    }

    @Then("^Full page screenshot z wypełnionego formularza \\(bez wysyłania\\)$")
    public void fullPageScreenshotZWypełnionegoFormularzaBezWysyłania() {
    }
}
