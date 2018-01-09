package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by SeungUk on 2018. 1. 9..
 */
abstract public class InsertJdbcTemplate {
    public void insert(User user) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        PreparedStatement pstmt = con.prepareStatement(createQueryForInsert());
        setValuesForInsert(user, pstmt);
        pstmt.executeUpdate();
    }

    abstract public void setValuesForInsert(User user, PreparedStatement pstmt) throws SQLException;
    abstract public String createQueryForInsert();
}
