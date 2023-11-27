package Dictionary;
import Connect.ConnectDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Word {
    private String word;
    private String html;

    public static final Word NOT_FOUND = new Word("Not found", "");

    private int sequentialId;
    private int id;
    private String pronounce;
    private String description;
    public Word() {
    }

    public Word(int id, String word, String pronounce, String description, int sequentialId) {
        this.id = id;
        this.word = word;
        this.pronounce = pronounce;
        this.description = description;
        this.sequentialId = sequentialId;
    }

    public Word(String word, String html) {
        this.word = word;
        this.html = html;
    }

    public String getWord(){return word;}

    public int getSequentialId() {
        return sequentialId;
    }

    public String getHtml() {
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            String fakeword = word.trim().replaceAll("\'", "''").toLowerCase();
            String verify = "SELECT html FROM av1 WHERE LOWER(word) = '" + fakeword + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);

            if (query.next()) {
                String html = query.getString("html");
                return html;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        } finally {
            ConnectDB.closeConnection();
        }
        return null;
    }

    @Override
    public String toString() {
        return word;
    }

    public int getId() {
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            String fakeword = word.trim().replaceAll("\'", "''").toLowerCase();
            String verify = "SELECT ID FROM av1 WHERE LOWER(word) = '" + fakeword + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);

            if (query.next()) {
                int ID = query.getInt("ID");
                return ID;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        } finally {
            ConnectDB.closeConnection();
        }
        return 0;
    }

    public String getDescription() {
        return description;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }
}
