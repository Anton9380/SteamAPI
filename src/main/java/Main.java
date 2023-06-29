import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Youngus", "Harrisuk", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        long notAnAdult = persons.parallelStream().filter(person -> person.getAge()<18).count();
        System.out.println("В городе Лондон проживает несовершеннолетних : " + notAnAdult);
        List<String> conscripts = persons.stream().filter(person ->
                        person.getAge()>18 && person.getAge()<27 && person.getSex().equals(Sex.MAN))
                .map(person -> person.getFamily())
                .collect(Collectors.toList());
        System.out.println("Список призывников для мобилизации: " + conscripts);
        List<Person> workersHigher = persons.parallelStream()
                .filter(person -> person.getEducation().equals(Education.HIGHER) && person.getAge()>18)
                .filter(person -> (person.getSex().equals(Sex.MAN) && person.getAge()<65) ||
                        (person.getSex().equals(Sex.WOMAN) && person.getAge()<60))
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        System.out.printf("Список высококвалифицированных рабочих: %s%n", workersHigher);

    }
}