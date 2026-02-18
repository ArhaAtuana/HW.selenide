package ru.netology.cardDelivery;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardDeliveryTest {

    public String generateDate(int days, String pattern) {

        return  LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test

    public void ShouldAcceptValidForm() {
        Selenide.open("http://localhost:9999/");

        String planningDate = generateDate(4,"dd.MM.yyyy");

        SelenideElement form = $("form");
        form.$("[data-test-id=city] .input__control").setValue("Москва");
        form.$("[data-test-id=date] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        //String date = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        form.$("[data-test-id=date] input").setValue(planningDate);
        form.$("[data-test-id=name] .input__control").setValue("Анна Асафьева");
        form.$("[data-test-id=phone] .input__control").setValue("+79998887766");
        form.$("[data-test-id=agreement]").click();
        $$("button").find(Condition.text ("Забронировать")).click();

        $(Selectors.withText("Встреча успешно забронирована на")).should(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.text(planningDate));

    }
}
