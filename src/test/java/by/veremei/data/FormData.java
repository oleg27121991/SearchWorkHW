package by.veremei.data;

import com.github.javafaker.Faker;

import java.util.Locale;

import static by.veremei.utils.RandomUtils.getRandomUtils;

public class FormData {
    private final Faker euFaker = new Faker(new Locale("en"));
    public final String validEmail = "test@test.com",
                        randomEmail = euFaker.internet().emailAddress(),
                        invalidEmail = randomEmail.replace("@", "!").replace(".", "_"),
                        shortDescr = getRandomUtils(100);
}
