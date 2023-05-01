package com.cfive.pinnacle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cfive.pinnacle.entity.User;
import com.cfive.pinnacle.entity.UserWork;
import com.cfive.pinnacle.entity.Work;
import com.cfive.pinnacle.mapper.UserMapper;
import com.cfive.pinnacle.mapper.UserWorkMapper;
import com.cfive.pinnacle.mapper.WorkMapper;
import com.cfive.pinnacle.service.IWorkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 工作事项 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Service
public class WorkServiceImpl extends ServiceImpl<WorkMapper, Work> implements IWorkService {
    @Autowired
    private WorkMapper workMapper;
    @Autowired
    private UserWorkMapper userWorkMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<Work> getAll() {
        List<Work> workList = workMapper.getAll();
        for (Work work:
             workList) {
            work.setProgress(getProgress(work.getId()));
            work.setPublisherName(getUserName(work.getPublisherId()));
        }
        return workList;
    }

    @Override
    public List<Work> getTodo(Long userId) {
        return workMapper.getTodo(userId);
    }

    @Override
    public List<Work> getComplete(Long userId) {
        return workMapper.getComplete(userId);
    }

    @Override
    public double getProgress(Long workId) {
        double workNum = userWorkMapper.selectCount(new QueryWrapper<UserWork>().eq("work_id",workId));
        double completeNum = userWorkMapper.selectCount(new QueryWrapper<UserWork>().eq("work_id",workId).eq("status",1));
        double progress = 0;
        progress = (completeNum / workNum) * 100;
        return progress;
    }

    @Override
    public String getUserName(Long userId) {
        return userMapper.selectById(userId).getUsername();
    }

    @Override
    public boolean addWork(Work work) {
        boolean flag = false;
        if (workMapper.insert(work) > 0) {
            flag = true;
        }
        long workId = work.getId();
        for (User user :
                work.getWorker()) {
            UserWork userWork = new UserWork();
            userWork.setWorkId(workId);
            userWork.setUserId(user.getId());
            if (userWorkMapper.insert(userWork) <= 0) {
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public boolean deleteByWorkId(Long workId) {
        boolean flag = false;
        if (userWorkMapper.delete(new QueryWrapper<UserWork>().eq("work_id", workId)) > 0 && workMapper.deleteById(workId) > 0) {
            flag = true;
        }
        return flag;
    }


}
