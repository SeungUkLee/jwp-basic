package core.jdbc;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by SeungUk on 2018. 1. 9..
 */
public abstract class JdbcTamplate {
    public void update(String query) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(query);
            setValues(pstmt);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }

    }

    abstract public void setValues(PreparedStatement pstmt) throws SQLException;
}
