/**
 * This file is part of mycollab-services.
 *
 * mycollab-services is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-services is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-services.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.common.dao;

import com.esofthead.mycollab.common.domain.MonitorItem;
import com.esofthead.mycollab.common.domain.MonitorItemExample;
import com.esofthead.mycollab.core.persistence.ICrudGenericDAO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@SuppressWarnings({ "ucd", "rawtypes" })
public interface MonitorItemMapper extends ICrudGenericDAO {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_monitor_item
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    int countByExample(MonitorItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_monitor_item
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    int deleteByExample(MonitorItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_monitor_item
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_monitor_item
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    int insert(MonitorItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_monitor_item
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    int insertSelective(MonitorItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_monitor_item
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    List<MonitorItem> selectByExample(MonitorItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_monitor_item
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    MonitorItem selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_monitor_item
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    int updateByExampleSelective(@Param("record") MonitorItem record, @Param("example") MonitorItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_monitor_item
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    int updateByExample(@Param("record") MonitorItem record, @Param("example") MonitorItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_monitor_item
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    int updateByPrimaryKeySelective(MonitorItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_monitor_item
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    int updateByPrimaryKey(MonitorItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_monitor_item
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    Integer insertAndReturnKey(MonitorItem value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_monitor_item
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    void removeKeysWithSession(List primaryKeys);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_monitor_item
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    void massUpdateWithSession(@Param("record") MonitorItem record, @Param("primaryKeys") List primaryKeys);
}