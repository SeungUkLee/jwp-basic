package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by SeungUk on 2018. 1. 9..
 */
abstract public class UpdateJdbcTemplate {
    public void update(User user) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        PreparedStatement pstmt = con.prepareStatement(createQueryForUpdate());
        setValuesForUpdate(user, pstmt);
        pstmt.executeUpdate();
    }

    abstract public void setValuesForUpdate(User user, PreparedStatement pstmt) throws SQLException;
    abstract public String createQueryForUpdate();
}
