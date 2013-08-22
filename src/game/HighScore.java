package game;

import java.util.*;
import java.sql.*;


// don't forget to create database first
// maybe better to use a datasource
public class HighScore implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String url = "jdbc:derby://localhost:1527/Dice";
    transient Connection con = null;
    private Vector<Entry> v = new Vector<Entry>();

    public HighScore() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");  //loads the driver
            con = DriverManager.getConnection(url, "app", "app");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.load();
    }

    public void load() {
        v.clear();
        try {
            Statement select = con.createStatement();
            ResultSet result = select.executeQuery("SELECT Name,Score FROM HighScore");
            while (result.next()) {
                this.addEntry(result.getString(1), result.getInt(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            for (Enumeration e = v.elements(); e.hasMoreElements();) {
                Entry entry = (Entry) e.nextElement();
                Statement s = con.createStatement();
                s.executeUpdate("INSERT INTO HighScore (Name,Score)"
                        + "VALUES('" + entry.getName() + "',"
                        + entry.getScore() + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addEntry(String name, int score) {
        v.addElement(new Entry(name, score));
        this.save();
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        for (Entry entry : v) {
            result.append("name:" + entry.getName() + ",score:" + entry.getScore());
            result.append("<br/>");
        }
        return result.toString();
    }

    public static void main(String args[]) {
        HighScore hs = new HighScore();
        System.out.println(hs);
        hs.addEntry("momo", 100);
        hs.save();
        hs.load();
        System.out.println(hs);
    }

}

