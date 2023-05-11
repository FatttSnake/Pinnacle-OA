package com.cfive.pinnacle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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

import java.text.DecimalFormat;
import java.time.LocalDateTime;
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
        List<Work> workList = workMapper.getTodo(userId);
        for (Work work:
                workList) {
            work.setPublisherName(getUserName(work.getPublisherId()));
        }
        return workList;
    }

    @Override
    public List<Work> getComplete(Long userId) {
        List<Work> workList = workMapper.getComplete(userId);
        for (Work work:
                workList) {
            work.setPublisherName(getUserName(work.getPublisherId()));
        }
        return workList;
    }

    @Override
    public Work getOne(Long workId) {
        Work work = workMapper.getWork(workId);
        work.setProgress(getProgress(workId));
        work.setPublisherName(getUserName(work.getPublisherId()));
        return work;
    }

    @Override
    public List<Work> getWorkByContent(String content) {
        List<Work> workList = workMapper.getWorkByContent(content);
        for (Work work:
                workList) {
            work.setProgress(getProgress(work.getId()));
            work.setPublisherName(getUserName(work.getPublisherId()));
        }
        return workList;
    }

    @Override
    public double getProgress(Long workId) {
        double workNum = userWorkMapper.selectCount(new QueryWrapper<UserWork>().eq("work_id",workId));
        double completeNum = userWorkMapper.selectCount(new QueryWrapper<UserWork>().eq("work_id",workId).eq("status",1));
        double progress = 0;
        progress = (completeNum / workNum) * 100;
        progress = (double) Math.round(progress * 100) / 100;
        return progress;
    }

    @Override
    public String getUserName(Long userId) {
        return userMapper.selectById(userId).getUsername();
    }

    @Override
    public boolean addWork(Work work) {
        boolean flag = true;
        if (workMapper.insert(work) <= 0) {
            flag = false;
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

    @Override
    public boolean updateStatus(UserWork userWork) {
        return userWorkMapper.update(userWork, new UpdateWrapper<UserWork>().eq("work_id", userWork.getWorkId()).eq("user_id", userWork.getUserId())) > 0;
    }

    @Override
    public boolean updateWork(Work work) {
        boolean flag = true;
        if (userWorkMapper.delete(new QueryWrapper<UserWork>().eq("work_id", work.getId())) <= 0) {
            flag = false;
        }
        if (workMapper.updateById(work)<=0) {
            flag = false;
        }
        for (User user :
                work.getWorker()) {
            UserWork userWork = new UserWork();
            userWork.setWorkId(work.getId());
            userWork.setUserId(user.getId());
            if (userWorkMapper.insert(userWork) <= 0) {
                flag = false;
            }
        }
        return flag;
    }


}
