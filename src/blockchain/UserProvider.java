package blockchain;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserProvider {
    private static List<Miner> miners;
    private static List<Person> persons;
    private static List<Company> companies;

    public static void generateUsers(BlockChain blockChain) {
        if (miners == null || persons == null || companies == null) {
            Faker faker = new Faker();
            miners = Stream.generate(() -> new Miner(blockChain))
                    .limit(10)
                    .collect(Collectors.toList());

            persons = Stream.generate(() -> new Person(faker.name().firstName(), blockChain))
                    .distinct()
                    .limit(5)
                    .collect(Collectors.toList());

            companies = Stream.generate(() -> new Company(faker.company().name().replaceAll(" ", ""), blockChain))
                    .distinct()
                    .limit(5)
                    .collect(Collectors.toList());
        }
    }

    public static List<Miner> getMiners() {
        return miners;
    }

    public static List<Person> getPersons() {
        return persons;
    }

    public static List<Company> getCompanies() {
        return companies;
    }

    public synchronized static User getRandomUser() {
        Random random = new Random();
        List<User> users = new ArrayList<>();
        users.addAll(miners);
        users.addAll(persons);
        users.addAll(companies);

        return users.get(random.nextInt(users.size()));
    }
}
