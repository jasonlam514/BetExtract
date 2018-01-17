package betextract;

import betextract.GUIDemo1;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Oddshelp
extends Thread {
    static PrintWriter out;
    static boolean debug;
    static boolean print;
    static boolean filter;
    static boolean filter2;
    static boolean test;
    static final String url = "http://www.oddshelp.com";
    private static final int MYTHREADS = 30;
    static double percentage;
    static double stake;

    private static void getLeague(String filename, double percentage, double stake) throws IOException {
        HashSet<String> links = new HashSet<String>();
        try {
            Document doc = Jsoup.connect((String)"http://www.oddshelp.com").maxBodySize(2457600).timeout(0).get();
            for (Element select : doc.select("select[name= league]")) {
                for (Element option : select.select("option")) {
                    links.add(option.attr("value"));
                }
            }
            links.remove("all");
            ExecutorService executor = Executors.newFixedThreadPool(30);
            for (String a : links) {
                BetRunnable worker = new BetRunnable("http://www.oddshelp.com/index.php?page=home&cmd=change_league&view=" + a, filename, percentage, stake);
                executor.execute(worker);
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
            }
        }
        catch (IOException e) {
            System.out.println("!!!!!!!");
            e.printStackTrace();
        }
    }

    public Oddshelp() throws IOException {
        percentage = GUIDemo1.percentage;
        stake = GUIDemo1.stake;
    }

    @Override
    public void run() {
        try {
            String filename = "C:\\xampp\\htdocs\\root\\Oddshelp.txt";
            if (print) {
                try {
                    out = new PrintWriter(new FileWriter(filename));
                    out.close();
                }
                catch (IOException ex) {
                    Logger.getLogger(Oddshelp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Oddshelp.getLeague(filename, percentage, stake);
        }
        catch (IOException ex) {
            Logger.getLogger(Oddshelp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws IOException {
    }

    static {
        debug = false;
        print = false;
        filter = false;
        filter2 = false;
        test = false;
    }

    private static class BetRunnable
    implements Runnable {
        private final String url;
        private final String filename;
        private final double percentage;
        private final double stake;
        private double odd1;
        private double odd2;
        private String home;
        private String away;

        BetRunnable(String url, String filename, double percentage, double stake) {
            this.url = url;
            this.filename = filename;
            this.percentage = percentage;
            this.stake = stake;
        }

        @Override
        public void run() {
            System.out.println("Processing: " + this.url);
            try {
                Document doc = Jsoup.connect((String)this.url).maxBodySize(2457600).timeout(0).get();
                for (Element table : doc.select("table[class= table table-striped table-hover]")) {
                    for (Element row : table.select("tr")) {
                        if (!row.select("td[class = col_6]").select("a").select("span").text().equals("")) {
                            this.odd1 = Double.parseDouble(row.select("td[class = col_6]").text());
                        }
                        if (row.select("td[class = col_7]").select("a").select("span").text().equals("")) continue;
                        this.odd2 = Double.parseDouble(row.select("td[class = col_7]").text());
                        String result = GUIDemo1.cal(this.odd1, this.odd2, this.percentage, this.stake);
                        if (result.equals("")) continue;
                        this.home = row.select("td[class = col_3]").text();
                        this.away = row.select("td[class = col_5]").text();
                        String[] data = result.split("/");
                        Date date = new Date();
                        GUIDemo1.insert(this.home + " vs " + this.away, "O/U 2.5", data[0], data[1] + " ", data[2] + " ", data[3], data[4], data[5], GUIDemo1.dateFormat.format(date), this.url);
                        if (!Oddshelp.print) continue;
                        Oddshelp.out = new PrintWriter(new FileWriter(this.filename, true));
                        Oddshelp.out.println(this.url + "\n" + this.home + " vs " + this.away + "\n" + result + "\n" + GUIDemo1.dateFormat.format(date) + "\n\n");
                        Oddshelp.out.close();
                    }
                }
            }
            catch (IOException e) {
                System.out.println("!!!!!!!");
                e.printStackTrace();
            }
        }
    }

}