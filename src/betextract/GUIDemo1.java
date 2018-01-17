package betextract;

import betextract.Handicap;
import betextract.Oddshelp;
import betextract.totalcorner;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.Document;

public class GUIDemo1
extends JFrame {
    JButton button = new JButton("Over/Under");
    JButton button2 = new JButton("Corner");
    JButton button3 = new JButton("Handicap");
    JButton button4 = new JButton("All");
    JButton button5 = new JButton("Clear");
    JTextField textarea = new JTextField("100");
    JTextField odd1area = new JTextField("2.00");
    JTextField odd2area = new JTextField("2.00");
    JTextField s1area = new JTextField("1");
    JTextField s2area = new JTextField("1");
    JTextField odd = new JTextField("0.00 (0.0%)");
    JCheckBox loop = new JCheckBox();
    JSpinner m_numberSpinner;
    SpinnerNumberModel m_numberSpinnerModel;
    JPopupMenu popupMenu = new JPopupMenu();
    JMenuItem browseItem = new JMenuItem("Details");
    JMenuItem deleteItem = new JMenuItem("Delete");
    JMenuItem browseURL = new JMenuItem("Open URL");
    static int row;
    JTextField timefield = new JTextField("0");
    double time = 0.0;
    static double percentage;
    static double stake;
    static String[] columnNames;
    static DefaultTableModel model;
    static JTable table;
    JScrollPane scrollPane = new JScrollPane(table);
    static DateFormat dateFormat;

    public static String cal(double a, double b, double percentage, double value) {
        double c = a + b;
        double bval = a / c * value * b;
        double aval = b / c * value * a;
        if ((aval - value) / value > percentage) {
            long aStake = 0;
            long bStake = 0;
            double earn = -99999.0;
            double earnP = -10.0;
            double diff = 9.9999999E7;
            for (long astake = Math.round((double)Math.floor((double)(aval / a))); astake <= Math.round(Math.ceil(aval / a)); ++astake) {
                for (long bstake = Math.round((double)Math.floor((double)GUIDemo1.DobVal((double)a, (long)astake, (double)b))); bstake <= Math.round(Math.ceil(GUIDemo1.DobVal(a, astake, b))); ++bstake) {
                    double temp;
                    if ((double)astake * a - (double)(astake + bstake) > (double)bstake * b - (double)(astake + bstake)) {
                        temp = (double)astake * a - (double)(astake + bstake) < 0.0 ? Math.abs(Math.abs((double)astake * a - (double)(astake + bstake)) - ((double)bstake * b - (double)(astake + bstake))) / (double)(astake + bstake) : ((double)astake * a - (double)(astake + bstake) - ((double)bstake * b - (double)(astake + bstake))) / (double)(astake + bstake);
                        if (temp >= diff) continue;
                        diff = temp;
                        earn = ((double)bstake * b + (double)astake * a) / 2.0 - (double)(astake + bstake);
                        aStake = astake;
                        bStake = bstake;
                        earnP = earn / (double)(astake + bstake) * 100.0;
                        continue;
                    }
                    temp = (double)astake * a - (double)(astake + bstake) < 0.0 ? Math.abs(Math.abs((double)bstake * b - (double)(astake + bstake)) - ((double)astake * a - (double)(astake + bstake))) / (double)(astake + bstake) : ((double)bstake * b - (double)(astake + bstake) - ((double)astake * a - (double)(astake + bstake))) / (double)(astake + bstake);
                    if (temp >= diff) continue;
                    diff = temp;
                    earn = ((double)bstake * b + (double)astake * a) / 2.0 - (double)(astake + bstake);
                    aStake = astake;
                    bStake = bstake;
                    earnP = earn / (double)(astake + bstake) * 100.0;
                }
            }
            if (aStake == 0 || bStake == 0) {
                if (percentage > -1.0) {
                    return "" + (aStake + bStake) + "/" + a + "/" + b + "/" + aStake + "/" + bStake + "/-0.00 (-100.0%)";
                }
                return "";
            }
            return "" + (aStake + bStake) + "/" + a + "/" + b + "/" + aStake + "/" + bStake + "/" + String.format("%.2f", earn) + " (" + String.format("%.1f", earnP) + "%)";
        }
        return "";
    }

    public static String cal(double a, double b) {
        double c = a + b;
        double bval = a / c * 10.0 * b;
        double aval = b / c * 10.0 * a;
        return String.format("%.2f", ((bval + aval) / 2.0 - 10.0) / 10.0 * 100.0) + "%";
    }

    public static String putVal(double a, double aval, double b) {
        double floor = (aval * a + Math.floor(a * aval / b) * b) / 2.0 - (aval + Math.floor(a * aval / b));
        double ceil = (aval * a + Math.ceil(a * aval / b) * b) / 2.0 - (aval + Math.ceil(a * aval / b));
        double diff1 = aval * a > Math.floor(a * aval / b) * b ? aval * a - Math.floor(a * aval / b) * b : Math.floor(a * aval / b) * b - aval * a;
        double diff2 = aval * a > Math.ceil(a * aval / b) * b ? aval * a - Math.ceil(a * aval / b) * b : Math.ceil(a * aval / b) * b - aval * a;
        if (diff1 < diff2) {
            if (aval == 0.0 || Math.floor(a * aval / b) == 0.0) {
                return "" + (int)Math.ceil(aval + Math.floor(a * aval / b)) + "/" + (int)Math.floor(a * aval / b) + "/-0.00 (-100%)";
            }
            return "" + (int)Math.ceil(aval + Math.floor(a * aval / b)) + "/" + (int)Math.floor(a * aval / b) + "/" + String.format("%.2f", floor) + " (" + String.format("%.2g", floor / (aval + Math.floor(a * aval / b)) * 100.0) + "%)";
        }
        if (aval == 0.0 || Math.ceil(a * aval / b) == 0.0) {
            return "" + (int)Math.ceil(aval + Math.ceil(a * aval / b)) + "/" + (int)Math.ceil(a * aval / b) + "/-0.00 (-100%)";
        }
        return "" + (int)Math.ceil(aval + Math.ceil(a * aval / b)) + "/" + (int)Math.ceil(a * aval / b) + "/" + String.format("%.2f", ceil) + " (" + String.format("%.2g", ceil / (aval + Math.ceil(a * aval / b)) * 100.0) + "%)";
    }

    public static long intVal(double a, long aval, double b) {
        return Math.round(a * (double)aval / b);
    }

    public static double DobVal(double a, long aval, double b) {
        return a * (double)aval / b;
    }

    public static String putVal(double a, double aval, double b, double bval) {
        if (aval * a - (aval + bval) > bval * b - (aval + bval)) {
            double best = aval * a - (aval + bval);
            double worst = bval * b - (aval + bval);
            return "Best: " + String.format("%.2f", aval) + " * " + String.format("%.2f", a) + " = " + String.format("%.2f", aval * a) + ". " + "Earned." + String.format("%.2f", best) + "\nWorst: " + String.format("%.2f", bval) + " * " + String.format("%.2f", b) + " = " + String.format("%.2f", bval * b) + ". " + "Earned." + String.format("%.2f", worst);
        }
        double worst = aval * a - (aval + bval);
        double best = bval * b - (aval + bval);
        return "Best: " + String.format("%.2f", bval) + " * " + String.format("%.2f", b) + " = " + String.format("%.2f", bval * b) + ". " + "Earned." + String.format("%.2f", best) + "\nWorst: " + String.format("%.2f", aval) + " * " + String.format("%.2f", a) + " = " + String.format("%.2f", aval * a) + ". " + "Earned." + String.format("%.2f", worst);
    }

    public static void openWebpage(URI uri) {
        Desktop desktop;
        Desktop desktop2 = desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void openWebpage(URL url) {
        try {
            GUIDemo1.openWebpage(url.toURI());
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public GUIDemo1() {
        this.setTitle("Surebets checker");
        GridLayout bigLayout = new GridLayout(0, 5);
        JPanel compsToExperiment = new JPanel();
        compsToExperiment.setLayout(bigLayout);
        compsToExperiment.add(this.button);
        compsToExperiment.add(this.button2);
        compsToExperiment.add(this.button3);
        compsToExperiment.add(this.button4);
        compsToExperiment.add(this.button5);
        Double current = 0.0;
        Double min = -1.0;
        Double max = 1.0;
        Double step = 0.01;
        this.m_numberSpinnerModel = new SpinnerNumberModel(current, min, max, step);
        this.m_numberSpinner = new JSpinner(this.m_numberSpinnerModel);
        this.textarea.setHorizontalAlignment(4);
        this.timefield.setHorizontalAlignment(0);
        this.odd.setEditable(false);
        JPanel controls = new JPanel();
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new GridLayout(0, 3));
        timePanel.add(new JLabel("every "));
        timePanel.add(this.timefield);
        timePanel.add(new JLabel(" min"));
        controls.setLayout(new GridLayout(2, 6));
        controls.add(new JLabel("Start Search:"));
        JPanel search = new JPanel();
        search.setLayout(new GridLayout(0, 2));
        search.add(new JLabel("return >"));
        search.add(this.m_numberSpinner);
        controls.add(search);
        JLabel stake2 = new JLabel("with total stake");
        stake2.setHorizontalAlignment(0);
        controls.add(stake2);
        controls.add(this.textarea);
        this.loop.setHorizontalAlignment(4);
        controls.add(this.loop);
        controls.add(timePanel);
        controls.add(new JLabel("Calculate Odds:"));
        JPanel oddcal = new JPanel();
        oddcal.setLayout(new GridLayout(0, 2));
        oddcal.add(this.odd1area);
        oddcal.add(this.odd2area);
        controls.add(oddcal);
        JLabel stake = new JLabel("with stake resp.");
        stake.setHorizontalAlignment(0);
        controls.add(stake);
        JPanel scal = new JPanel();
        scal.setLayout(new GridLayout(0, 2));
        scal.add(this.s1area);
        scal.add(this.s2area);
        controls.add(scal);
        JLabel earned = new JLabel("will earn you");
        earned.setHorizontalAlignment(0);
        controls.add(earned);
        controls.add(this.odd);
        table.putClientProperty("terminateEditOnFocusLost", true);
        TableColumn column = null;
        for (int i = 0; i < 11; ++i) {
            column = table.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(200);
                continue;
            }
            if (i == 1 || i == 7) {
                column.setPreferredWidth(60);
                continue;
            }
            if (i == 10) {
                column.setPreferredWidth(270);
                continue;
            }
            if (i == 8 || i == 9) {
                column.setPreferredWidth(65);
                continue;
            }
            column.setPreferredWidth(13);
        }
        this.deleteItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selection = GUIDemo1.table.getSelectedRows();
                int[] lines = GUIDemo1.table.getSelectedRows();
                if (lines.length != 0) {
                    int i;
                    for (i = 0; i < lines.length; ++i) {
                        lines[i] = GUIDemo1.table.convertRowIndexToModel(lines[i]);
                    }
                    Arrays.sort(lines);
                    for (i = lines.length - 1; i >= 0; --i) {
                        GUIDemo1.model.removeRow(lines[i]);
                    }
                }
            }
        });
        this.browseItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int num = GUIDemo1.table.convertRowIndexToModel(GUIDemo1.table.getSelectedRow());
                    JOptionPane.showMessageDialog(null, GUIDemo1.putVal(Double.parseDouble(GUIDemo1.model.getValueAt(num, 3).toString()), Double.parseDouble(GUIDemo1.model.getValueAt(num, 5).toString()), Double.parseDouble(GUIDemo1.model.getValueAt(num, 4).toString()), Double.parseDouble(GUIDemo1.model.getValueAt(num, 6).toString())), GUIDemo1.model.getValueAt(num, 0).toString(), 1);
                }
                catch (NumberFormatException num) {
                    // empty catch block
                }
            }
        });
        this.browseURL.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int num = GUIDemo1.table.convertRowIndexToModel(GUIDemo1.table.getSelectedRow());
                    GUIDemo1.openWebpage(new URI(GUIDemo1.model.getValueAt(num, 10) + ""));
                }
                catch (URISyntaxException ex) {
                    Logger.getLogger(GUIDemo1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.popupMenu.add(this.browseItem);
        this.popupMenu.add(this.deleteItem);
        this.popupMenu.add(this.browseURL);
        table.setComponentPopupMenu(this.popupMenu);
        table.addMouseListener(new MouseAdapter(){

            @Override
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable)me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 3) {
                    try {
                        GUIDemo1.openWebpage(new URI(table.getValueAt(row, 10) + ""));
                    }
                    catch (URISyntaxException ex) {
                        Logger.getLogger(GUIDemo1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        this.setLayout(new BorderLayout());
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
        Comparator<String> comparator = new Comparator<String>(){

            @Override
            public int compare(String s1, String s2) {
                String[] strings1 = s1.split("[(]");
                String[] strings2 = s2.split("[(]");
                try {
                    double a = Double.parseDouble(strings1[0]);
                    double b = Double.parseDouble(strings2[0]);
                    return Double.valueOf(a).compareTo(b);
                }
                catch (NumberFormatException e2) {
                    return s1.compareTo(s2);
                }
            }
        };
        sorter.setComparator(2, comparator);
        sorter.setComparator(3, comparator);
        sorter.setComparator(4, comparator);
        sorter.setComparator(5, comparator);
        sorter.setComparator(6, comparator);
        sorter.setComparator(7, comparator);
        table.setRowSorter(sorter);
        table.setFillsViewportHeight(true);
        this.scrollPane.setVisible(true);
        this.add(this.scrollPane);
        JPanel control = new JPanel();
        control.add((Component)controls, "South");
        control.add((Component)compsToExperiment, "North");
        this.add((Component)control, "South");
        this.getContentPane().setPreferredSize(new Dimension(1300, 300));
        this.setMinimumSize(new Dimension(955, 200));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
        ClickListener cl = new ClickListener();
        this.button.addActionListener(cl);
        this.button2.addActionListener(cl);
        this.button3.addActionListener(cl);
        this.button4.addActionListener(cl);
        this.button5.addActionListener(cl);
        this.loop.setText("Repeat");
        DocumentListener documentListener = new DocumentListener(){
            String ans;
            String[] anss;

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                if (!(GUIDemo1.this.odd1area.getText().equals("") || GUIDemo1.this.odd2area.getText().equals("") || GUIDemo1.this.s1area.getText().equals(""))) {
                    try {
                        if (Double.parseDouble(GUIDemo1.this.odd1area.getText()) > 0.0 && Double.parseDouble(GUIDemo1.this.odd2area.getText()) > 0.0 && Double.parseDouble(GUIDemo1.this.s1area.getText()) > 0.0) {
                            GUIDemo1.this.s2area.setText("" + GUIDemo1.intVal(Double.parseDouble(GUIDemo1.this.odd1area.getText()), Integer.parseInt(GUIDemo1.this.s1area.getText()), Double.parseDouble(GUIDemo1.this.odd2area.getText())) + "");
                            this.ans = GUIDemo1.cal(Double.parseDouble(GUIDemo1.this.odd1area.getText()), Double.parseDouble(GUIDemo1.this.odd2area.getText()), -1.0, Integer.parseInt(GUIDemo1.this.s1area.getText()) + Integer.parseInt(GUIDemo1.this.s2area.getText()));
                            this.anss = this.ans.split("/");
                            GUIDemo1.this.odd.setText(this.anss[5]);
                        }
                    }
                    catch (NumberFormatException numberFormatException) {
                        // empty catch block
                    }
                }
            }

            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                if (!(GUIDemo1.this.odd1area.getText().equals("") || GUIDemo1.this.odd2area.getText().equals("") || GUIDemo1.this.s1area.getText().equals(""))) {
                    try {
                        if (Double.parseDouble(GUIDemo1.this.odd1area.getText()) > 0.0 && Double.parseDouble(GUIDemo1.this.odd2area.getText()) > 0.0 && Double.parseDouble(GUIDemo1.this.s1area.getText()) > 0.0) {
                            GUIDemo1.this.s2area.setText("" + GUIDemo1.intVal(Double.parseDouble(GUIDemo1.this.odd1area.getText()), Integer.parseInt(GUIDemo1.this.s1area.getText()), Double.parseDouble(GUIDemo1.this.odd2area.getText())) + "");
                            this.ans = GUIDemo1.cal(Double.parseDouble(GUIDemo1.this.odd1area.getText()), Double.parseDouble(GUIDemo1.this.odd2area.getText()), -1.0, Integer.parseInt(GUIDemo1.this.s1area.getText()) + Integer.parseInt(GUIDemo1.this.s2area.getText()));
                            this.anss = this.ans.split("/");
                            GUIDemo1.this.odd.setText(this.anss[5]);
                        }
                    }
                    catch (NumberFormatException numberFormatException) {
                        // empty catch block
                    }
                }
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                if (!(GUIDemo1.this.odd1area.getText().equals("") || GUIDemo1.this.odd2area.getText().equals("") || GUIDemo1.this.s1area.getText().equals(""))) {
                    try {
                        if (Double.parseDouble(GUIDemo1.this.odd1area.getText()) > 0.0 && Double.parseDouble(GUIDemo1.this.odd2area.getText()) > 0.0 && Double.parseDouble(GUIDemo1.this.s1area.getText()) > 0.0) {
                            GUIDemo1.this.s2area.setText("" + GUIDemo1.intVal(Double.parseDouble(GUIDemo1.this.odd1area.getText()), Integer.parseInt(GUIDemo1.this.s1area.getText()), Double.parseDouble(GUIDemo1.this.odd2area.getText())) + "");
                            this.ans = GUIDemo1.cal(Double.parseDouble(GUIDemo1.this.odd1area.getText()), Double.parseDouble(GUIDemo1.this.odd2area.getText()), -1.0, Integer.parseInt(GUIDemo1.this.s1area.getText()) + Integer.parseInt(GUIDemo1.this.s2area.getText()));
                            this.anss = this.ans.split("/");
                            GUIDemo1.this.odd.setText(this.anss[5]);
                        }
                    }
                    catch (NumberFormatException numberFormatException) {
                        // empty catch block
                    }
                }
            }
        };
        this.odd1area.getDocument().addDocumentListener(documentListener);
        this.odd2area.getDocument().addDocumentListener(documentListener);
        this.s1area.getDocument().addDocumentListener(documentListener);
        this.s2area.setEditable(false);
        this.setVisible(true);
    }

    public static synchronized void insert(String match, String type, String stake, String odd1, String odd2, String o1, String o2, String earned, String time, String url) {
        for (int i = 0; i < table.getRowCount(); ++i) {
            if (!table.getModel().getValueAt(i, 0).toString().replaceAll("\\(.*?\\)", "").equals(match.replaceAll("\\(.*?\\)", "")) || !table.getModel().getValueAt(i, 1).equals(type) || !table.getModel().getValueAt(i, 10).equals(url)) continue;
            model.setValueAt(match, i, 0);
            model.setValueAt(stake, i, 2);
            model.setValueAt(odd1, i, 3);
            model.setValueAt(odd2, i, 4);
            Date date = new Date();
            model.setValueAt(dateFormat.format(date), i, 9);
            return;
        }
        model.insertRow(0, new String[]{match, type, stake, odd1, odd2, o1, o2, earned, time, time, url});
    }

    public static void main(String[] args) throws IOException {
        new GUIDemo1();
    }

    static {
        columnNames = new String[]{"Match", "Type", "Stake", "Odds1", "Odds2", "O1$", "O2$", "Earned (Avg)", "Time Created", "Last Updated", "Url"};
        model = new DefaultTableModel(null, columnNames){

            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 2 || column == 5 || column == 6) {
                    return true;
                }
                return false;
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {
                Vector rowVector = (Vector)this.dataVector.elementAt(row);
                rowVector.setElementAt(aValue, column);
                if (column == 2) {
                    try {
                        Double data = Double.parseDouble(GUIDemo1.model.getValueAt(row, column) + "");
                        String[] newdata = GUIDemo1.cal(Double.parseDouble(GUIDemo1.model.getValueAt(row, 3) + ""), Double.parseDouble(GUIDemo1.model.getValueAt(row, 4) + ""), -1.0, data).split("/");
                        rowVector.setElementAt(newdata[3], 5);
                        rowVector.setElementAt(newdata[4], 6);
                        GUIDemo1.model.setValueAt(newdata[5], row, 7);
                        rowVector.setElementAt(newdata[0], column);
                        this.fireTableCellUpdated(row, 5);
                        this.fireTableCellUpdated(row, 6);
                    }
                    catch (NumberFormatException data) {}
                } else if (column == 5 || column == 6) {
                    try {
                        String[] newdata;
                        Double data = Double.parseDouble(GUIDemo1.model.getValueAt(row, column) + "");
                        if (column == 5) {
                            newdata = GUIDemo1.putVal(Double.parseDouble(GUIDemo1.model.getValueAt(row, 3) + ""), data, Double.parseDouble(GUIDemo1.model.getValueAt(row, 4) + "")).split("/");
                            rowVector.setElementAt(newdata[1], 6);
                        } else {
                            newdata = GUIDemo1.putVal(Double.parseDouble(GUIDemo1.model.getValueAt(row, 4) + ""), data, Double.parseDouble(GUIDemo1.model.getValueAt(row, 3) + "")).split("/");
                            rowVector.setElementAt(newdata[1], 5);
                        }
                        rowVector.setElementAt(newdata[0], 2);
                        GUIDemo1.model.setValueAt(newdata[2], row, 7);
                    }
                    catch (NumberFormatException data) {
                        // empty catch block
                    }
                }
                this.fireTableCellUpdated(row, column);
            }
        };
        table = new JTable(model);
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    }

    private class ClickListener
    implements ActionListener {
        private int clickCount;

        private ClickListener() {
            this.clickCount = 0;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == GUIDemo1.this.button5) {
                GUIDemo1.model.setRowCount(0);
                return;
            }
            boolean valid = false;
            try {
                GUIDemo1.percentage = (Double)GUIDemo1.this.m_numberSpinner.getValue();
                GUIDemo1.stake = Double.parseDouble(GUIDemo1.this.textarea.getText());
                if (GUIDemo1.stake < 1.0) {
                    throw new NumberFormatException("Exception thrown");
                }
                valid = true;
                GUIDemo1.this.time = Double.parseDouble(GUIDemo1.this.timefield.getText());
                if (GUIDemo1.this.time < 0.0) {
                    GUIDemo1.this.time = 0.0;
                    GUIDemo1.this.timefield.setText("0");
                }
            }
            catch (NumberFormatException e2) {
                JOptionPane.showMessageDialog(GUIDemo1.this, "Please input valid amount of total stake below. Total stake should be at least 1.", "Input Type Error", 2);
            }
            if (valid) {
                if (e.getSource() == GUIDemo1.this.button) {
                    new SwingWorker<String, Void>(){

                        @Override
                        protected String doInBackground() throws Exception {
                            JOptionPane.showMessageDialog(GUIDemo1.this, "Generating report for o/u");
                            do {
                                Oddshelp o = new Oddshelp();
                                o.run();
                                if (GUIDemo1.this.loop.isSelected()) {
                                    Oddshelp.sleep((long)(60000.0 * GUIDemo1.this.time));
                                    try {
                                        GUIDemo1.percentage = (Double)GUIDemo1.this.m_numberSpinner.getValue();
                                        GUIDemo1.stake = Double.parseDouble(GUIDemo1.this.textarea.getText());
                                    }
                                    catch (NumberFormatException numberFormatException) {
                                        // empty catch block
                                    }
                                }
                                if (GUIDemo1.this.loop.isSelected()) continue;
                                o.run();
                            } while (GUIDemo1.this.loop.isSelected());
                            return "";
                        }

                        @Override
                        protected void done() {
                            JOptionPane.showMessageDialog(GUIDemo1.this, "Report Generated!");
                        }
                    }.execute();
                } else if (e.getSource() == GUIDemo1.this.button2) {
                    new SwingWorker<String, Void>(){

                        @Override
                        protected String doInBackground() throws Exception {
                            JOptionPane.showMessageDialog(GUIDemo1.this, "Generating report for corner");
                            do {
                                totalcorner t = new totalcorner();
                                t.run();
                                if (GUIDemo1.this.loop.isSelected()) {
                                    totalcorner.sleep((long)(60000.0 * GUIDemo1.this.time));
                                    try {
                                        GUIDemo1.percentage = (Double)GUIDemo1.this.m_numberSpinner.getValue();
                                        GUIDemo1.stake = Double.parseDouble(GUIDemo1.this.textarea.getText());
                                    }
                                    catch (NumberFormatException numberFormatException) {
                                        // empty catch block
                                    }
                                }
                                if (GUIDemo1.this.loop.isSelected()) continue;
                                t.run();
                            } while (GUIDemo1.this.loop.isSelected());
                            return "";
                        }

                        @Override
                        protected void done() {
                            JOptionPane.showMessageDialog(GUIDemo1.this, "Report Generated!");
                        }
                    }.execute();
                } else if (e.getSource() == GUIDemo1.this.button3) {
                    new SwingWorker<String, Void>(){

                        @Override
                        protected String doInBackground() throws Exception {
                            JOptionPane.showMessageDialog(GUIDemo1.this, "Generating report for handicaps");
                            do {
                                Handicap b = new Handicap();
                                b.run();
                                if (GUIDemo1.this.loop.isSelected()) {
                                    Handicap.sleep((long)(60000.0 * GUIDemo1.this.time));
                                    try {
                                        GUIDemo1.percentage = (Double)GUIDemo1.this.m_numberSpinner.getValue();
                                        GUIDemo1.stake = Double.parseDouble(GUIDemo1.this.textarea.getText());
                                    }
                                    catch (NumberFormatException numberFormatException) {
                                        // empty catch block
                                    }
                                }
                                if (GUIDemo1.this.loop.isSelected()) continue;
                                b.run();
                            } while (GUIDemo1.this.loop.isSelected());
                            return "";
                        }

                        @Override
                        protected void done() {
                            JOptionPane.showMessageDialog(GUIDemo1.this, "Report Generated!");
                        }
                    }.execute();
                } else if (e.getSource() == GUIDemo1.this.button4) {
                    JOptionPane.showMessageDialog(GUIDemo1.this, "Generating reports");
                    new SwingWorker<String, Void>(){

                        @Override
                        protected String doInBackground() throws Exception {
                            do {
                                ExecutorService service = Executors.newFixedThreadPool(3);
                                service.execute(new Oddshelp());
                                service.execute(new totalcorner());
                                service.execute(new Handicap());
                                service.shutdown();
                                service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                                if (GUIDemo1.this.loop.isSelected()) {
                                    Thread.sleep((long)(60000.0 * GUIDemo1.this.time));
                                    try {
                                        GUIDemo1.percentage = (Double)GUIDemo1.this.m_numberSpinner.getValue();
                                        GUIDemo1.stake = Double.parseDouble(GUIDemo1.this.textarea.getText());
                                    }
                                    catch (NumberFormatException numberFormatException) {
                                        // empty catch block
                                    }
                                }
                                if (GUIDemo1.this.loop.isSelected()) continue;
                                service = Executors.newFixedThreadPool(3);
                                service.execute(new Oddshelp());
                                service.execute(new totalcorner());
                                service.execute(new Handicap());
                                service.shutdown();
                                service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                            } while (GUIDemo1.this.loop.isSelected());
                            return "";
                        }

                        @Override
                        protected void done() {
                            JOptionPane.showMessageDialog(GUIDemo1.this, "Reports Generated!");
                        }
                    }.execute();
                }
            }
        }

    }

}