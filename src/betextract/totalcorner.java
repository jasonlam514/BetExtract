package betextract;

import betextract.GUIDemo1;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class totalcorner
extends Thread {
    static Set<String> matches = new HashSet<String>();
    static String cap = "";
    static String attr;
    static Double prev;
    static PrintWriter out;
    static boolean print;
    private static final int MYTHREADS = 30;
    static boolean remove;
    static double percentage;
    static double stake;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void getLeague(String filename, double percentage, double stake) throws IOException {
        URL url = new URL("http://www.oddschecker.com/football");
        BufferedReader reader = null;
        Pattern pattern = Pattern.compile("href=\"/football/[a-zA-Z0-9-]+/[a-zA-Z0-9-]+\"");
        HashSet<String> links = new HashSet<String>();
        try {
            HttpURLConnection httpcon = (HttpURLConnection)url.openConnection();
            httpcon.addRequestProperty("User-Agent", "Mozilla/4.0");
            reader = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    links.add(matcher.group(0).substring(6, matcher.group(0).length() - 1));
                }
            }
            links.add("/football/world-cup");
            if (remove) {
                totalcorner.remove(links);
            }
            ExecutorService executor = Executors.newFixedThreadPool(30);
            for (String a : links) {
                MatchRunnable worker = new MatchRunnable("http://www.oddschecker.com" + a);
                executor.execute(worker);
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
            }
            executor = Executors.newFixedThreadPool(30);
            for (String b : matches) {
                BetRunnable worker = new BetRunnable(b, filename, percentage, stake);
                executor.execute(worker);
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
            }
        }
        finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private static void remove(Set<String> links) {
        links.remove("/football/scottish/non-league");
        links.remove("/football/youth/u18");
        links.remove("/football/youth/u19");
        links.remove("/football/football-coupons/win-both-halves");
        links.remove("/football/womens-international/u20");
        links.remove("/football/womens-international/u23");
        links.remove("/football/scottish/highland-league");
        links.remove("/football/germany/lower-leagues");
        links.remove("/football/italy/campionato-primavera");
        links.remove("/football/france/youth");
        links.remove("/football/scottish/league-1");
        links.remove("/football-coupons/bumper-coupon");
        links.remove("/football/germany/regionalliga");
        links.remove("/football/scottish/league-2");
        links.remove("/football/germany/youth");
        links.remove("/football/germany/3rd-liga");
        links.remove("/football/scottish/league-2");
        links.remove("/football/football-coupons/match-result-btts");
        links.remove("/football/youth/u17");
        links.remove("/football/spain/season-match-bets");
        links.remove("/football/italy/lega-pro");
        links.remove("/football/spain/la-liga-tercera");
        links.remove("/football/italy/season-match-bets");
        links.remove("/football/football-coupons/draw-no-bet");
        links.remove("/football/spain/youth");
        links.remove("/football/youth/u23");
        links.remove("/football/youth/u22");
        links.remove("/football/youth/u20");
        links.remove("/football/football-coupons/bumper-coupon");
        links.remove("/football/womens-international/u17");
        links.remove("/football/germany/season-match-bets");
        links.remove("/football/italy/serie-d");
        links.remove("/football/football-coupons/handicaps");
        links.remove("/football/football-coupons/btts-both-halves");
        links.remove("/football/football-coupons/double-chance");
        links.remove("/football/football-coupons/win-to-nil");
        links.remove("/football/football-coupons/uk");
        links.remove("/football/football-coupons/major-leagues-cups");
    }

    public totalcorner() throws IOException {
        percentage = GUIDemo1.percentage;
        stake = GUIDemo1.stake;
    }

    @Override
    public void run() {
        try {
            String filename = "C:\\xampp\\htdocs\\root\\corner.txt";
            if (print) {
                try {
                    out = new PrintWriter(new FileWriter(filename));
                    out.close();
                }
                catch (IOException ex) {
                    Logger.getLogger(totalcorner.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            remove = false;
            totalcorner.getLeague(filename, percentage, stake);
        }
        catch (IOException ex) {
            Logger.getLogger(totalcorner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws IOException {
    }

    static {
        print = false;
        remove = true;
    }

    private static class MatchRunnable
    implements Runnable {
        private final String league_url;

        MatchRunnable(String league_url) {
            this.league_url = league_url;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            try {
                URL league_url = new URL(this.league_url);
                BufferedReader reader = null;
                Pattern pattern = Pattern.compile("href=\"/football/[a-zA-Z0-9/-]+/winner\"");
                HashSet<String> links = new HashSet<String>();
                try {
                    HttpURLConnection httpcon = (HttpURLConnection)league_url.openConnection();
                    httpcon.addRequestProperty("User-Agent", "Mozilla/4.0");
                    httpcon.setInstanceFollowRedirects(false);
                    reader = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        Matcher matcher = pattern.matcher(line);
                        while (matcher.find()) {
                            String link = "http://www.oddschecker.com" + matcher.group(0).substring(6, matcher.group(0).length() - 7) + "total-corners/";
                            links.add(link);
                        }
                    }
                    for (String a : links) {
                        totalcorner.out = new PrintWriter(new FileWriter("cornersite.txt", true));
                        totalcorner.out.println(a);
                        totalcorner.out.close();
                    }
                    totalcorner.matches.addAll(links);
                }
                catch (IOException ex) {
                    Logger.getLogger(totalcorner.class.getName()).log(Level.SEVERE, null, ex);
                }
                finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        }
                        catch (IOException ex) {
                            Logger.getLogger(totalcorner.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            catch (MalformedURLException ex) {
                Logger.getLogger(totalcorner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static class BetRunnable
    implements Runnable {
        private final String url;
        private final String filename;
        private final double percentage;
        private final double stake;

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
                Document doc = Jsoup.connect((String)this.url).maxBodySize(2457600).followRedirects(false).timeout(0).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();
                if (!doc.select("span[class = button no-arrow blink in-play]").text().equals("")) {
                    return;
                }
                block2 : for (Element table : doc.select("table[class= eventTable]")) {
                    for (Element row : table.select("tr[class= diff-row eventTableRow bc]")) {
                        String result;
                        totalcorner.attr = row.attr("data-best-dig");
                        if (totalcorner.cap.equals(row.attr("data-hcap")) && !(result = GUIDemo1.cal(totalcorner.prev, Double.parseDouble(totalcorner.attr), this.percentage, this.stake)).equals("")) {
                            System.out.println("---------------------------------------------\n" + row.attr("data-hcap") + "BELOW \n" + this.url + "\n" + result + "\n\n");
                            String[] data = result.split("/");
                            Date date = new Date();
                            String[] match = doc.select("title").first().text().split(" Total");
                            GUIDemo1.insert(match[0], "Corner " + row.attr("data-hcap"), data[0], data[1] + " ", data[2] + " ", data[3], data[4], data[5], GUIDemo1.dateFormat.format(date), this.url);
                            if (totalcorner.print) {
                                totalcorner.out = new PrintWriter(new FileWriter(this.filename, true));
                                totalcorner.out.println(this.url + "\n" + "Corner U/O: " + row.attr("data-hcap") + "\n" + result + "\n" + GUIDemo1.dateFormat.format(date) + "\n\n");
                                totalcorner.out.close();
                            }
                        }
                        totalcorner.prev = Double.parseDouble(totalcorner.attr);
                        totalcorner.cap = row.attr("data-hcap");
                        if (row.tagName().equals("tr")) continue;
                        continue block2;
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