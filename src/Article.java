import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Article {

    private String title, url;
    private int rating, nbrOfComments;
    private LocalDate dateOfCreation;
    HashMap<String, String> hm = new HashMap<>();
    private static String txt;

    public Article(String s) {
        txt = s;
        setTitle();
        setDateTimeOfCreation();
        setUrl();
        setRating();
        setNbrOfComments();
    }

    public String date() {
        return dateOfCreation.toString();
    }

    public boolean isLegal(Template t) {
        if (this.rating >= t.getRatingMin() && this.rating <= t.getRatingMax()
                && this.nbrOfComments >= t.getCommMin() && this.nbrOfComments <= t.getCommMax()) {
            return true;
        }
        return false;
    }

    public boolean dateIsLegal(Template t) {
        if ((dateOfCreation.isAfter(t.getDate()) || dateOfCreation.isEqual(t.getDate()) ))
            return true;
        return false;
    }

    private String findMatch(String REGEX, String txt) {
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(txt);
        if (m.find()) {
            return m.group(1);
        }
        else
            System.out.println("no match available");
        return "-1";
    }

    private void setTitle() {
        String REGEX = "class=\"post__title_link\">(.+?)</a>";//set title
        title = findMatch(REGEX, txt);
    }

    public void setDateTimeOfCreation() {
        String REGEX = "<span class=\"post__time\">(.+?)</span>";
        String dateTimeOfCreation = findMatch(REGEX, txt);

        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-yyy");
        hm.put(" января ", "-01-");
        hm.put(" февраля ", "-02-");
        hm.put(" марта ", "-03-");
        hm.put(" апреля ", "-04-");
        hm.put(" мая ", "-05-");
        hm.put(" июня ", "-06-");
        hm.put(" июля ", "-07-");
        hm.put(" августа ", "-08-");
        hm.put(" сентября ", "-09-");
        hm.put(" октября ", "-10-");
        hm.put(" ноября ", "-11-");
        hm.put(" декабря ", "-12-");
        hm.put("сегодня", LocalDate.now().format(f));
        hm.put("вчера", LocalDate.now().minusDays(1).format(f));

        dateTimeOfCreation = dateTimeOfCreation.replace(" в ", " ");
        for (Map.Entry m : hm.entrySet()) {
            if (dateTimeOfCreation.contains((CharSequence) m.getKey())) {
                dateTimeOfCreation = dateTimeOfCreation.replace((CharSequence) m.getKey(),
                        (CharSequence) m.getValue());
                break;
            }
        }
        DateTimeFormatter formatter;
        if (dateTimeOfCreation.length() == 16)
            formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        else
            formatter = DateTimeFormatter.ofPattern("d-MM-yyyy HH:mm");
        this.dateOfCreation = LocalDate.parse(dateTimeOfCreation, formatter);
    }

    public void setUrl() {
        String REGEX = "(https://.+?)\"";
        this.url =  findMatch(REGEX, txt);
    }

    public void setRating() {
        String REGEX = "Всего голосов.+?>((\\+|-)\\d*)</span>";
        String s =  findMatch(REGEX, txt);
        this.rating = Integer.parseInt(s);
    }

    public void setNbrOfComments() {
        String REGEX = "\"Читать комментарии\">(\\d*)</span>";
        String s = findMatch(REGEX, txt);
        this.nbrOfComments =  Integer.parseInt(s);
    }

    @Override
    public String toString() {
        return "Article {" +
                "title= '" + title + '\'' +
                ", url= '" + url + '\'' +
                ", rating= " + rating +
                ", nbrOfComments= " + nbrOfComments +
                ", DateTimeOfCreation= " + dateOfCreation +
                "}";
    }
}