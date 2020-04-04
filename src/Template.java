import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Template {
    private static int ratingMin = 0, ratingMax = Integer.MAX_VALUE,
            commMin = 0, commMax = Integer.MAX_VALUE;
    private static LocalDate date = LocalDate.now().minusDays(1);
    private static boolean news = false;

    public Template(String args[]) {
        if (args.length == 0) return;

        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            date = LocalDate.parse(args[0], f);
            if (args.length >= 3) {
                setComments(args[2]);
                setRating(args[1]);
                if (args.length == 4) news = true;
            }
            else if (args.length == 2) setRating(args[2]);
        }
        catch (Exception e) {
            System.out.println("arguments parse error");
            System.exit(-1);
        }
    }

    private void setRating(String s) {
        if (s.contains("-")) {
            int index = s.indexOf("-");
            ratingMin = Integer.parseInt(s.substring(0, index));
            ratingMax = Integer.parseInt(s.substring(index + 1));
            if (ratingMin > ratingMax) {
                int temp = ratingMin;
                ratingMin = ratingMax;
                ratingMax = temp;
            }
        }else
            ratingMin = Integer.parseInt(s);
    }

    private void setComments(String s) {
        if (s.contains("-")) {
            int index = s.indexOf("-");
            commMin = Integer.parseInt(s.substring(0, index));
            commMax = Integer.parseInt(s.substring(index + 1));
            if (commMin > commMax) {
                int temp = commMin;
                commMin = commMax;
                commMax = temp;
            }
        }else
            commMin = Integer.parseInt(s);
    }

    @Override
    public String toString() {
        return "SearchExampleArticle{ Date: " + date +
                ", rating " + ratingMin + " - " + ratingMax +
                ", comm " + commMin + " - " + commMax +
                 " }";
    }

    public static int getRatingMin() {
        return ratingMin;
    }

    public static int getRatingMax() {
        return ratingMax;
    }

    public static int getCommMin() {
        return commMin;
    }

    public static int getCommMax() {
        return commMax;
    }

    public static LocalDate getDate() {
        return date;
    }

    public static boolean isNews() {
        return news;
    }
}
