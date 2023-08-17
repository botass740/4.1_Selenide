import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldFillingCardForm () {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Романов Максим");
        $("[data-test-id='phone'] input").setValue("+79787574471");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        WebElement title = $("[data-test-id='notification'] .notification__title") .shouldBe(Condition.visible, Duration.ofSeconds(15));
        Assertions.assertEquals("Успешно!", title.getText());
        WebElement content = $("[data-test-id='notification'] .notification__content");
        Assertions.assertEquals("Встреча успешно забронирована на " + currentDate, content.getText());
    }
}

