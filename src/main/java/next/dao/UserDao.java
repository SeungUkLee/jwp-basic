package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import core.jdbc.JdbcTamplate;
import core.jdbc.SelectJdbcTemplate;
import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
        String insertQuery = "INSERT INTO USERS VALUES (?, ?, ?, ?)";

        JdbcTamplate jdbcTamplate = new JdbcTamplate() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }
        };

        jdbcTamplate.update(insertQuery);
    }

    public void update(User user) throws SQLException {
        JdbcTamplate jdbcTamplate = new JdbcTamplate() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
            }
        };
        String updateQuery = "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?";
        jdbcTamplate.update(updateQuery);
    }


    public List<User> findAll() throws SQLException {
        SelectJdbcTemplate selectJdbcTemplate = new SelectJdbcTemplate() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException{

            }

            @Override
            public Object mapRow(ResultSet rs) throws SQLException {
                return new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
        };
        String findAllSelcectQuery = "SELECT userId, password, name, email FROM USERS";

        return (List<User>) selectJdbcTemplate.query(findAllSelcectQuery);
    }

//    public User findByUserId(String userId) throws SQLException {
//
//
//        Connection con = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        try {
//            con = ConnectionManager.getConnection();
//            String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
//            pstmt = con.prepareStatement(sql);
//            pstmt.setString(1, userId);
//
//            rs = pstmt.executeQuery();
//
//            User user = null;
//            if (rs.next()) {
//                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
//                        rs.getString("email"));
//            }
//
//            return user;
//        } finally {
//            if (rs != null) {
//                rs.close();
//            }
//            if (pstmt != null) {
//                pstmt.close();
//            }
//            if (con != null) {
//                con.close();
//            }
//        }
//    }

    public User findByUserId(String userId) throws SQLException {
        SelectJdbcTemplate selectJdbcTemplate = new SelectJdbcTemplate() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }

            @Override
            public Object mapRow(ResultSet rs) throws SQLException {
                return new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
        };

        String findByUserIdSelectQuery = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";
        return (User)selectJdbcTemplate.queryForObject(findByUserIdSelectQuery);
    }
}
