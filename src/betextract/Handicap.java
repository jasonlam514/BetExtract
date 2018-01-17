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
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.ScriptException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Handicap
extends Thread {
    static PrintWriter out;
    static boolean debug;
    static boolean print;
    static boolean filter;
    static boolean filter2;
    static boolean test;
    static boolean getBook;
    private static final int MYTHREADS = 30;
    static double percentage;
    static double stake;

    public static void remove(Set set) {
        String[] urls;
        for (String s : urls = new String[]{"http://www.sportspunter.com/sports-betting/soccer/montenegro/prva-liga-betting/", "http://www.sportspunter.com/sports-betting/soccer/iran/azadegan-league-betting/", "http://www.sportspunter.com/sports-betting/soccer/tunisia/league-betting/", "http://www.sportspunter.com/sports-betting/soccer/azerbaijan/cup-betting/", "http://www.sportspunter.com/sports-betting/soccer/gibraltar/premier-division-betting/", "http://www.sportspunter.com/sports-betting/soccer/cyprus/division-1-betting/", "http://www.sportspunter.com/sports-betting/soccer/northern-ireland/ifa-championship-1-betting/", "http://www.sportspunter.com/sports-betting/soccer/switzerland/challenge-league-betting/", "http://www.sportspunter.com/sports-betting/soccer/brazil/serie-a-betting/", "http://www.sportspunter.com/sports-betting/soccer/world-club/club-friendlies-betting/", "http://www.sportspunter.com/sports-betting/soccer/france/cfa-group-d-betting/", "http://www.sportspunter.com/sports-betting/soccer/brazil/baiano-1-betting/", "http://www.sportspunter.com/sports-betting/soccer/argentina/primera-c-metropolitana-betting/", "http://www.sportspunter.com/sports-betting/soccer/germany/oberliga-nordost-sud-betting/", "http://www.sportspunter.com/sports-betting/soccer/israel/ligat-al-betting/", "http://www.sportspunter.com/sports-betting/soccer/brazil/goiano-betting/", "http://www.sportspunter.com/sports-betting/soccer/russia/2nd-division-south-betting/", "http://www.sportspunter.com/sports-betting/soccer/wales/division-1-betting/", "http://www.sportspunter.com/sports-betting/soccer/portugal/league-cup-betting/", "http://www.sportspunter.com/sports-betting/soccer/latvia/latvian-cup-betting/", "http://www.sportspunter.com/sports-betting/soccer/kazakhstan/kazakhstan-cup-betting/", "http://www.sportspunter.com/sports-betting/soccer/panama/lfp-betting/", "http://www.sportspunter.com/sports-betting/soccer/turkey/2-lig-beyaz-betting/", "http://www.sportspunter.com/sports-betting/soccer/hungary/nbi-betting/", "http://www.sportspunter.com/sports-betting/soccer/greece/greek-cup-betting/", "http://www.sportspunter.com/sports-betting/soccer/sweden/division-3-sodra-gotaland-betting/", "http://www.sportspunter.com/sports-betting/soccer/sweden/division-3-nordostra-gotaland-betting/", "http://www.sportspunter.com/sports-betting/soccer/ofc/ofc-champions-league-betting/", "http://www.sportspunter.com/sports-betting/soccer/albania/super-league-betting/", "http://www.sportspunter.com/sports-betting/soccer/norway/norwegian-cup-betting/", "http://www.sportspunter.com/sports-betting/soccer/turkey/2-lig-kirmizi-betting/", "http://www.sportspunter.com/sports-betting/soccer/faroe-islands/meistaradeilden-betting/", "http://www.sportspunter.com/sports-betting/soccer/bulgaria/b-pfg-betting/", "http://www.sportspunter.com/sports-betting/soccer/russia/2nd-division-west-betting/", "http://www.sportspunter.com/sports-betting/soccer/ecuador/serie-a-betting/", "http://www.sportspunter.com/sports-betting/soccer/south-africa/premier-league-betting/", "http://www.sportspunter.com/sports-betting/soccer/australia/minor-leagues-betting/", "http://www.sportspunter.com/sports-betting/soccer/costa-rica/primera-division-betting/", "http://www.sportspunter.com/sports-betting/soccer/south-africa/1st-division-betting/", "http://www.sportspunter.com/sports-betting/soccer/czech-republic/pohar-cmfs-betting/", "http://www.sportspunter.com/sports-betting/soccer/ukraine/u21-league-betting/", "http://www.sportspunter.com/sports-betting/soccer/sudan/premier-league-betting/", "http://www.sportspunter.com/sports-betting/soccer/brazil/copa-do-brazil-betting/", "http://www.sportspunter.com/sports-betting/soccer/turkey/lig-a-betting/", "http://www.sportspunter.com/sports-betting/soccer/germany/oberliga-bremen-betting/", "http://www.sportspunter.com/sports-betting/soccer/brazil/brasiliense-betting/", "http://www.sportspunter.com/sports-betting/soccer/georgia/umaglesi-liga-betting/", "http://www.sportspunter.com/sports-betting/soccer/sweden/division-3-sodra-svealand-betting/", "http://www.sportspunter.com/sports-betting/soccer/sweden/division-3-vastra-svealand-betting/", "http://www.sportspunter.com/sports-betting/soccer/brazil/copa-verde-betting/", "http://www.sportspunter.com/sports-betting/soccer/moldova/national-division-betting/", "http://www.sportspunter.com/sports-betting/soccer/netherlands/eredivisie-betting/", "http://www.sportspunter.com/sports-betting/soccer/macedonian/1st-league-betting/", "http://www.sportspunter.com/sports-betting/soccer/egypt/egyptian-cup-betting/", "http://www.sportspunter.com/sports-betting/soccer/kenya/premier-league-betting/", "http://www.sportspunter.com/sports-betting/soccer/brazil/mineiro-1-betting/", "http://www.sportspunter.com/sports-betting/soccer/el-salvador/la-primera-betting/", "http://www.sportspunter.com/sports-betting/soccer/india/i-league-betting/", "http://www.sportspunter.com/sports-betting/soccer/argentina/primera-d-betting/", "http://www.sportspunter.com/sports-betting/soccer/vietnam/v-league-betting/", "http://www.sportspunter.com/sports-betting/soccer/israel/liga-leumit-betting/", "http://www.sportspunter.com/sports-betting/soccer/russia/2nd-division-east-betting/", "http://www.sportspunter.com/sports-betting/soccer/belarus/premier-league-w-betting/", "http://www.sportspunter.com/sports-betting/soccer/uganda/premier-league-betting/", "http://www.sportspunter.com/sports-betting/soccer/russia/2nd-division-ural-povolzhye-betting/", "http://www.sportspunter.com/sports-betting/soccer/england/u21-premier-league-betting/", "http://www.sportspunter.com/sports-betting/soccer/iceland/womens-cup-betting/", "http://www.sportspunter.com/sports-betting/soccer/denmark/danmarksseriaen-p3-betting/", "http://www.sportspunter.com/sports-betting/soccer/spain/segunda-b-group-4-betting/", "http://www.sportspunter.com/sports-betting/soccer/usa/usl-pro-betting/", "http://www.sportspunter.com/sports-betting/soccer/concacaf/concacaf-champions-league-betting/", "http://www.sportspunter.com/sports-betting/soccer/ukraine/1st-division-betting/", "http://www.sportspunter.com/sports-betting/soccer/brazil/sergipano-betting/", "http://www.sportspunter.com/sports-betting/soccer/brazil/alagoano-betting/", "http://www.sportspunter.com/sports-betting/soccer/brazil/gaucho-1-betting/", "http://www.sportspunter.com/sports-betting/soccer/brazil/matogrossense-betting/", "http://www.sportspunter.com/sports-betting/soccer/france/coupe-de-france-betting/", "http://www.sportspunter.com/sports-betting/soccer/mexico/ascenso-mx-betting/", "http://www.sportspunter.com/sports-betting/soccer/iraq/iraqi-league-betting/", "http://www.sportspunter.com/sports-betting/soccer/malta/premier-league-betting/", "http://www.sportspunter.com/sports-betting/soccer/slovakia/slovakian-cup-betting/", "http://www.sportspunter.com/sports-betting/soccer/brazil/campeonata-paulista-a1-betting/", "http://www.sportspunter.com/sports-betting/soccer/brazil/cearense-1-betting/", "http://www.sportspunter.com/sports-betting/soccer/ireland/fai-cup-betting/", "http://www.sportspunter.com/sports-betting/soccer/hungary/nb-ii-betting/", "http://www.sportspunter.com/sports-betting/soccer/international/international-friendlies-betting/", "http://www.sportspunter.com/sports-betting/soccer/argentina/torneo-federal-a-betting/", "http://www.sportspunter.com/sports-betting/soccer/brazil/copa-nordeste-betting/", "http://www.sportspunter.com/sports-betting/soccer/armenia/armenian-premier-league-betting/", "http://www.sportspunter.com/sports-betting/soccer/guatemala/liga-nacional-betting/", "http://www.sportspunter.com/sports-betting/soccer/russia/2nd-division-center-betting/"}) {
            set.remove(s);
        }
    }

    public Handicap() {
        percentage = GUIDemo1.percentage;
        stake = GUIDemo1.stake;
    }

    @Override
    public void run() {
        if (!test) {
            debug = true;
            String filename = "C:\\xampp\\htdocs\\root\\sportspunter.txt";
            if (print) {
                try {
                    out = new PrintWriter(new FileWriter(filename));
                }
                catch (IOException ex) {
                    Logger.getLogger(Handicap.class.getName()).log(Level.SEVERE, null, ex);
                }
                out.close();
            }
            MatchRunnable worker = new MatchRunnable("http://www.sportspunter.com/sports-betting/soccer/", filename, percentage, stake);
            worker.run();
        }
    }

    public static String getBookie(Document doc, String title, String overS, String underS) throws IOException {
        String b1 = "";
        String b2 = "";
        boolean flag = false;
        for (Element table : doc.select("tr")) {
            if (table.select("td[class=heading]").size() > 1) {
                if (title.length() == 4) {
                    if (((Element)table.select("td[class=heading]").get(1)).text().contains(title.substring(0, 4))) {
                        flag = true;
                    }
                } else if (((Element)table.select("td[class=heading]").get(1)).text().contains(title.substring(0, 3))) {
                    flag = true;
                }
            }
            if (!flag) continue;
            for (Element row : table.select("td[class]")) {
                if (row.select("b").text().equals(overS)) {
                    if (row.previousElementSibling().text().trim().equals(b1.trim())) continue;
                    b1 = b1 + " " + row.previousElementSibling().text();
                    continue;
                }
                if (!row.select("b").text().equals(underS) || row.previousElementSibling().previousElementSibling() == null || row.previousElementSibling().previousElementSibling().text().trim().equals(b2.trim())) continue;
                b2 = b2 + " " + row.previousElementSibling().previousElementSibling().text();
            }
        }
        return b1 + "/" + b2;
    }

    public static void main(String[] args) throws IOException, ScriptException {
        debug = true;
        getBook = true;
    }

    static {
        debug = false;
        print = false;
        filter = false;
        filter2 = false;
        test = false;
        getBook = false;
    }

    public static class BetRunnable
    implements Runnable {
        private final String address;
        private final String filename;
        private final double percentage;
        private final double stake;

        BetRunnable(String address, String filename, double percentage, double stake) {
            this.address = address;
            this.filename = filename;
            this.percentage = percentage;
            this.stake = stake;
        }

        @Override
        public void run() {
            Object reader = null;
            String htmlurl = "";
            Double over = 1.0;
            Double under = 1.0;
            String overS = "";
            String underS = "";
            String result = "";
            String title = "";
            String match = "";
            String b1 = "";
            String b2 = "";
            boolean tit = false;
            boolean flag = false;
            try {
                Document doc = Jsoup.connect((String)this.address).timeout(0).maxBodySize(2457600).get();
                for (Element table : doc.select("tr")) {
                    String[] split;
                    Document doc2;
                    if (table.select("td[align = center]").size() < 2) continue;
                    Element odd = (Element)table.select("td[align = center]").get(1);
                    Element odd2 = null;
                    if (table.select("td[align = center]").size() > 3) {
                        odd2 = (Element)table.select("td").get(3);
                    }
                    if (!odd.text().contains(".5")) {
                        if (table.select("td[align = center]").size() <= 3 || !odd2.text().equals("0.50") && !odd2.text().equals("-2.50 : 2.50")) continue;
                        title = odd2.text();
                    } else {
                        title = odd.text();
                    }
                    if (!table.select("a").first().attr("title").contains("Compare odds sorted by ") && !((Element)table.select("a").get(2)).attr("title").contains("Compare odds sorted by ")) continue;
                    htmlurl = table.select("a").attr("href");
                    System.out.println(htmlurl);
                    overS = table.select("a").first().text();
                    underS = ((Element)table.select("a").get(1)).text();
                    if (((Element)table.select("a").get(2)).attr("title").contains("Compare odds sorted by ")) {
                        overS = ((Element)table.select("a").get(1)).text();
                        underS = ((Element)table.select("a").get(2)).text();
                    }
                    if ((split = overS.split("/")).length > 1) {
                        over = (Double.parseDouble(split[0]) + Double.parseDouble(split[1])) / Double.parseDouble(split[1]);
                    } else if (overS.equals("Evens")) {
                        over = 2.0;
                    } else {
                        try {
                            over = Double.parseDouble(overS);
                        }
                        catch (NumberFormatException e) {
                            return;
                        }
                    }
                    split = underS.split("/");
                    if (split.length > 1) {
                        under = (Double.parseDouble(split[0]) + Double.parseDouble(split[1])) / Double.parseDouble(split[1]);
                    } else if (underS.equals("Evens")) {
                        under = 2.0;
                    } else {
                        try {
                            under = Double.parseDouble(underS);
                        }
                        catch (NumberFormatException e) {
                            return;
                        }
                    }
                    if (under < 0.0 || over < 0.0) {
                        return;
                    }
                    if (GUIDemo1.cal(over, under, this.percentage, this.stake).equals("") || (doc2 = Jsoup.connect((String)("http://www.sportspunter.com" + htmlurl)).timeout(0).get()).select("big") != null && doc2.select("big").select("span").first().attr("title").equals("This match has already started")) continue;
                    if (htmlurl.contains("matchnumbers=")) {
                        int start = htmlurl.indexOf("matchnumbers=") + "matchnumbers=".length();
                        int end = start + 7;
                        match = doc2.select("option[value =" + htmlurl.substring(start, end) + "]").text();
                        System.out.println("match" + match);
                    } else if (htmlurl.contains("matchnumber=")) {
                        int start = htmlurl.indexOf("matchnumber=") + "matchnumber=".length();
                        int end = start + 7;
                        match = doc2.select("option[value =" + htmlurl.substring(start, end) + "]").text();
                        System.out.println("match" + match);
                    }
                    if (Handicap.getBook && (split = Handicap.getBookie(doc2, title, overS, underS).split("/")).length > 1) {
                        b1 = split[0];
                        b2 = split[1];
                    }
                    Date date = new Date();
                    result = "http://www.sportspunter.com" + htmlurl + "\n" + "Type: " + title + ", " + b1 + " : " + b2 + "\n" + GUIDemo1.cal(over, under, this.percentage, this.stake) + "\n" + GUIDemo1.dateFormat.format(date) + "\n\n";
                    String[] data = GUIDemo1.cal(over, under, this.percentage, this.stake).split("/");
                    GUIDemo1.insert(match, (title.split(":").length > 1 ? "AH " : "O/U ") + title, data[0], data[1] + " " + b1, data[2] + " " + b2, data[3], data[4], data[5], GUIDemo1.dateFormat.format(date), "http://www.sportspunter.com" + htmlurl);
                    if (Handicap.debug) {
                        System.out.println(result);
                    }
                    if (!Handicap.print) continue;
                    try {
                        Handicap.out = new PrintWriter(new FileWriter(this.filename, true));
                    }
                    catch (IOException ex) {
                        Logger.getLogger(Handicap.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Handicap.out.println(result);
                    Handicap.out.close();
                }
            }
            catch (IOException ex) {
                Logger.getLogger(Handicap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static class MatchRunnable
    implements Runnable {
        private final String address;
        private final String filename;
        private final double percentage;
        private final double stake;

        MatchRunnable(String address, String filename, double percentage, double stake) {
            this.address = address;
            this.filename = filename;
            this.percentage = percentage;
            this.stake = stake;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            try {
                URL url = new URL(this.address);
                BufferedReader reader = null;
                Pattern pattern = Pattern.compile("href=\"/sports-betting/soccer/[a-zA-Z0-9-]+/[a-zA-Z0-9-]+/\"");
                String result = "";
                HashSet<String> links = new HashSet<String>();
                try {
                    reader = new BufferedReader(new InputStreamReader(url.openStream()));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        Matcher matcher = pattern.matcher(line);
                        if (!matcher.find()) continue;
                        String link = "http://www.sportspunter.com" + matcher.group(0).substring(6, matcher.group(0).length() - 1);
                        links.add(link);
                    }
                    if (Handicap.debug) {
                        System.out.println("" + links.size() + ", start search!");
                    }
                    ExecutorService executor = Executors.newFixedThreadPool(30);
                    for (String a : links) {
                        BetRunnable worker = new BetRunnable(a, this.filename, this.percentage, this.stake);
                        executor.execute(worker);
                    }
                    executor.shutdown();
                    while (!executor.isTerminated()) {
                    }
                }
                catch (IOException ex) {
                    Logger.getLogger(Handicap.class.getName()).log(Level.SEVERE, null, ex);
                }
                finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        }
                        catch (IOException ex) {
                            Logger.getLogger(Handicap.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            catch (MalformedURLException ex) {
                Logger.getLogger(Handicap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}