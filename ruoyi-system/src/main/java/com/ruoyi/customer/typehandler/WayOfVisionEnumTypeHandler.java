package com.ruoyi.customer.typehandler;

import com.ruoyi.customer.domain.WayOfVisionEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * 联系方式枚举转换，实现java枚举值与据库存储值的转换
 * @author YJ
 */

public class WayOfVisionEnumTypeHandler extends BaseTypeHandler<WayOfVisionEnum>{
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, WayOfVisionEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public WayOfVisionEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return convert(value);
    }

    @Override
    public WayOfVisionEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return convert(value);
    }

    @Override
    public WayOfVisionEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return convert(value);
    }
    private WayOfVisionEnum convert(String value){
        if(Objects.isNull(value))
        {
            return null;
        }
        try
        {
            return WayOfVisionEnum.valueOf(value);
        }
        catch (IllegalArgumentException e)
        {
            for(WayOfVisionEnum enumVal: WayOfVisionEnum.values())
            {
                if(value.equals(enumVal.getDescription()))
                {
                    return enumVal;
                }
            }
            return WayOfVisionEnum.OTHER;
        }
    }
}
