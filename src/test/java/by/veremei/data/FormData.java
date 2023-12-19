package by.veremei.data;

import com.github.javafaker.Faker;

import java.util.Locale;

public class FormData {
    private final Faker euFaker = new Faker(new Locale("en"));
    public final String validEmail = "test@test.com",
                        randomEmail = euFaker.internet().emailAddress(),
                        invalidEmail = randomEmail.replace("@", "!").replace(".", "_"),
                        shortDescr = euFaker.regexify("[a-z1-9]{100}");
}
