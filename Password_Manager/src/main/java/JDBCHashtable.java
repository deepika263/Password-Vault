import java.sql.*;

public class JDBCHashtable implements hashTableMap {

    @Override
    public Object get_Acc(Object account) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT password FROM passwords WHERE account_name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, account.toString().toLowerCase());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int add_Acc(Object account, Object password) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO passwords (account_name, password) VALUES (?, ?) " +
                    "ON DUPLICATE KEY UPDATE password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, account.toString().toLowerCase());
            stmt.setString(2, password.toString());
            stmt.setString(3, password.toString());
            stmt.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Object remove_Acc(Object account) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "DELETE FROM passwords WHERE account_name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, account.toString().toLowerCase());
            int result = stmt.executeUpdate();
            return result > 0 ? account : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
