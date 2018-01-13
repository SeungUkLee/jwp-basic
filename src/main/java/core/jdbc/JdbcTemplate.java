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
    public void update(String query, PreparedStatementSetter pss) throws DataAccessException {
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);){

            pss.setValues(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public List query(String query, PreparedStatementSetter pss, RowMapper rowMapper) throws DataAccessException{
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();){

            pss.setValues(pstmt);

            List<Object> result = new ArrayList<>();
            while (rs.next()) {
                result.add(rowMapper.mapRow(rs));
            }
            return result;

        } catch (SQLException e){
            throw new DataAccessException(e);
        }
    }

    public Object queryForObject(String query, PreparedStatementSetter pss, RowMapper rowMapper) throws SQLException {
        List result = query(query, pss, rowMapper);
        if (result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }
}
