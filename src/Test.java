import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        System.out.println(now.isBefore(tomorrow));
    }
}