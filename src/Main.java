import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Main {

    public static void main(String[] args) throws IOException {

        Template t = new Template(args);
        String url;
        if (t.isNews()) url = "https://habr.com/ru/news/";
        else if (t.getCommMin() >= 100) url = "https://habr.com/ru/all/top100/";
        else if (t.getCommMin() >= 50) url = "https://habr.com/ru/all/top50/";
        else if (t.getCommMin() >= 25) url = "https://habr.com/ru/all/top25/";
        else if (t.getCommMin() >= 10) url = "https://habr.com/ru/all/top510/";
        else url = "https://habr.com/ru/all/";

        System.out.println(t.toString());
        System.out.println(url);

        int pageNbr = 1;
        while (true) {
            String txt = getPageText(url);
            Pattern p = Pattern.compile("<span class=\"post__time");
            Matcher m = p.matcher(txt);
            System.out.println("page " + pageNbr);
            FileWriter fw = new FileWriter("/home/lena/Desktop/habr.txt", true);
            while (m.find()) {
                Article art = new Article(txt.substring(m.start()));
                System.out.println(art.date());
                if (art.dateIsLegal(t)) {
                    if (art.isLegal(t)) {
                        fw.append(art.toString());
                        fw.append(System.lineSeparator());
                        System.out.println(art.toString());
                    }
                }
                else if (!art.dateIsLegal(t)) {
                    fw.close();
                    System.exit(0);
                }
            }
            url = getNextPageAddr(url, ++pageNbr);
            fw.close();
        }

    }
    static String getPageText(String str) throws MalformedURLException {
        URL url = new URL(str);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            System.out.println("getPageText ok");
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static String getNextPageAddr(String url, int pageNbr) {
        String nextPageUrl;
        int i;
        if ((i = url.indexOf("page")) == -1) {
            return url + "page2/";
        } else
            return url.substring(0, i + 4) + pageNbr + "/";
    }

    static void getHtmlFromUrl(String strUrl) throws MalformedURLException {
        URL url = new URL(strUrl);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            StringBuilder sb = new StringBuilder();
            FileWriter fw = new FileWriter("/home/lena/Desktop/habr.html");
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                fw.append(line);
                fw.append(System.lineSeparator());
            }
            System.out.println("file is complete");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}