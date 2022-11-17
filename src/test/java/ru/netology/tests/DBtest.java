package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.date.DBUtils;
import ru.netology.date.DataHelper;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.date.DataHelper.*;

public class DBtest {
    @BeforeAll
    static void setUpAll(){
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll(){
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void openSource() {
        open("http://localhost:8080");
        DBUtils.cleanTable();
    }

    @Test
    void shouldBeApprovedWithApprovedCard() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.successfullPayment();
        assertEquals("APPROVED", DBUtils.getPaymentStatus());
    }

    @Test
    void shouldBeDeclinedWithDeclinedCard() {
        val cardInfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.successfullPayment();
        assertEquals("DECLINED", DBUtils.getPaymentStatus());
    }

}
