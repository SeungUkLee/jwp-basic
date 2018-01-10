package core.jdbc;

import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SeungUk on 2018. 1. 9..
 */
abstract public class SelectJdbcTemplate {
    public List query(String query) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
//        List<User> userList = new ArrayList<>();

        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(query);

            setValues(pstmt);
            rs = pstmt.executeQuery();

            List<Object> result = new ArrayList<>();
            while (rs.next()) {
//                User user = (User) mapRow(rs);
//                userList.add(user);
                result.add(mapRow(rs));
            }
            return result;

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
//        return userList;
    }

    public Object queryForObject(String query) throws SQLException {
        List result = query(query);
        if (result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }

    abstract public void setValues(PreparedStatement pstmt) throws SQLException;
    abstract public Object mapRow(ResultSet rs) throws SQLException;
}
