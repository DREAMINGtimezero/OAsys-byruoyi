package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author YJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BarChart{
    private String[] legend;
    private String[] xAxis;
    private Map<String,Integer[]> series;
}
