package com.cfive.pinnacle.mapper;

import com.cfive.pinnacle.entity.Affair;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cfive.pinnacle.entity.permission.User;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 事务 Mapper 接口
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Mapper
public interface AffairMapper extends BaseMapper<Affair> {

    @Insert("insert into t_affair(title,type_id,content,applicant_id,inspector_id,create_time) values(#{title},#{typeId},#{content},#{applicantId},#{inspectorId},#{createTime})")
    int insertAffair(Affair affair);
    //添加事务
    //不添加事务的状态(affairsStatus)，当事务进行添加时，添加的状态默认为'未审批'

    @Delete("delete from t_affair where id=#{id}")
    int deleteAffairs(Affair affair);
    //根据id，撤回新建的事务，在新建事务时，会再进行一次确定
    // (是否撤回,当用户撤回新建的事务时，根据新建的事务的id，删除该条事务在数据库中的信息)

    @Update("update  t_affair set status=1,inspect_time=#{inspectTime} where id=#{id}")
    int updateAffairsYes(Affair affair);
    //管理员权限--->修改事务的状态(AffairsStatus)--->达到审批的效果
    //同意

    @Update("update t_affair set Status=2 where id=#{id}")
    int updateAffairsNO(Affair affair);
    //不同意

    List<User> getSameDepartmentUser(@Param("id") long id);


    List<Affair> getFuzzyQueriesByAffairTitle(String title, Long typeId, Integer status,Long applicantId);

    List<Affair> getNotApprovedByFuzzyQueries(String title, Long typeId, Integer status,Long inspectorId ,LocalDateTime startTime,LocalDateTime endTime);

    List<Affair> getApprovedByFuzzyQueries(String title, Long typeId, Integer status,Long inspectorId ,LocalDateTime startTime,LocalDateTime endTime);

//
//    @Select("SELECT t_affair.applicant_id,t_affair.inspector_id,t_user.id,t_user.username from t_affair,t_user ")
//    @ResultType(Affair.class)
//    List<Affair> selectAffairs_NotApproved();

}
