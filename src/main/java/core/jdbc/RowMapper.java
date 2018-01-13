package core.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by SeungUk on 2018. 1. 13..
 */
public interface RowMapper<T> {
    T mapRow(ResultSet rs) throws SQLException;
}
