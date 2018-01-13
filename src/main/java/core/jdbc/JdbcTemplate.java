package core.jdbc;

import core.jdbc.ConnectionManager;
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
public class JdbcTemplate {
    public void update(String query, PreparedStatementSetter pss) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(query);
            pss.setValues(pstmt);
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
    public List query(String query, PreparedStatementSetter pss, RowMapper rowMapper) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(query);

            pss.setValues(pstmt);
            rs = pstmt.executeQuery();

            List<Object> result = new ArrayList<>();
            while (rs.next()) {
                result.add(rowMapper.mapRow(rs));
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
    }

    public Object queryForObject(String query, PreparedStatementSetter pss, RowMapper rowMapper) throws SQLException {
        List result = query(query, pss, rowMapper);
        if (result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }

//    abstract public Object mapRow(ResultSet rs) throws SQLException;
//
//
//    abstract public void setValues(PreparedStatement pstmt) throws SQLException;
}
