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

    public void update(String query, Object... parameters) throws DataAccessException {
        update(query, createPreparedStatementSetter(parameters));
    }

    public <T> List<T> query(String query, RowMapper<T> rowMapper, PreparedStatementSetter pss) throws DataAccessException{
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();){

            pss.setValues(pstmt);

            List<T> result = new ArrayList<>();
            while (rs.next()) {
                result.add(rowMapper.mapRow(rs));
            }
            return result;

        } catch (SQLException e){
            throw new DataAccessException(e);
        }
    }

    public <T> List<T> query(String query, RowMapper<T> rowMapper, Object... parameters) throws DataAccessException {
        return query(query, rowMapper, createPreparedStatementSetter(parameters));
    }

    public <T> T queryForObject(String query, RowMapper<T> rowMapper,  PreparedStatementSetter pss) throws DataAccessException {
        List<T> result = query(query, rowMapper, pss);
        if (result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }

    public <T> T queryForObject(String query, RowMapper<T> rowMapper, Object... parameters) throws DataAccessException {
        return queryForObject(query, rowMapper, createPreparedStatementSetter(parameters));
    }

    public PreparedStatementSetter createPreparedStatementSetter(Object ... parameters) {
        return pstmt -> {
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i+1, parameters[i]);
            }
        };
    }
}
