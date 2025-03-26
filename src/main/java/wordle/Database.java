package wordle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database {
    public static Connection conn;

    public static void connect() throws Exception {
        conn = DriverManager.getConnection("jdbc:sqlite:database.db");

        conn.createStatement().execute("""
                    CREATE TABLE IF NOT EXISTS history (
                        username TEXT,
                        date TEXT,
                        winorloss TEXT,
                        word TEXT
                    )
                """);
    }

    public static void insertHistory(String username, String date, String winorloss, String word) throws Exception {
        String sql = "INSERT INTO history (username, date, winorloss, word) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, date);
            pstmt.setString(3, winorloss);
            pstmt.setString(4, word);
            pstmt.executeUpdate();
        }
    }

    public static List<List<String>> getUserHistory(String username) throws Exception {
        String sql = "SELECT date, winorloss, word FROM history WHERE username = ?";
        List<List<String>> historyList = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    historyList.add(Arrays.asList(
                            rs.getString("date"),
                            rs.getString("winorloss"),
                            rs.getString("word")));
                }
            }
        }

        return historyList;
    }
}
