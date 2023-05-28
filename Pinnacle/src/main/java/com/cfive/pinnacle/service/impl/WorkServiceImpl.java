package com.cfive.pinnacle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cfive.pinnacle.entity.permission.User;
import com.cfive.pinnacle.entity.UserWork;
import com.cfive.pinnacle.entity.Work;
import com.cfive.pinnacle.mapper.permission.UserMapper;
import com.cfive.pinnacle.mapper.UserWorkMapper;
import com.cfive.pinnacle.mapper.WorkMapper;
import com.cfive.pinnacle.service.IWorkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        return workMapper.getAll();
    }

    @Override
    public List<Work> getTodo(Long userId) {
        return workMapper.getTodo(userId);
    }

    @Override
    public List<Work> getCard(Long userId) {
        return workMapper.getCard(userId);
    }

    @Override
    public List<Work> getComplete(Long userId) {
        return workMapper.getComplete(userId);
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
        return workMapper.getWorkByContent(content);
    }

    @Override
    public double getProgress(Long workId) {
        double workNum = userWorkMapper.selectCount(new QueryWrapper<UserWork>().eq("work_id",workId));
        double completeNum = userWorkMapper.selectCount(new QueryWrapper<UserWork>().eq("work_id",workId).eq("status",1));
        double progress;
        progress = (completeNum / workNum) * 100;
        progress = (double) Math.round(progress * 100) / 100;
        return progress;
    }

    @Override
    public String getUserName(Long userId) {
        return userMapper.selectById(userId).getUsername();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public boolean addWork(Work work) {
        boolean flag = workMapper.insert(work) > 0;
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
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public boolean deleteByWorkId(Long workId) {
        return userWorkMapper.delete(new QueryWrapper<UserWork>().eq("work_id", workId)) > 0 && workMapper.deleteById(workId) > 0;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public boolean updateStatus(UserWork userWork) {
        return userWorkMapper.update(userWork, new UpdateWrapper<UserWork>().eq("work_id", userWork.getWorkId()).eq("user_id", userWork.getUserId())) > 0;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public boolean updateWork(Work work) {
        boolean flag = userWorkMapper.delete(new QueryWrapper<UserWork>().eq("work_id", work.getId())) > 0;
        if (workMapper.update(null, new UpdateWrapper<Work>().eq("id", work.getId()).set("old", 1)) <= 0) {
            flag = false;
        }
        else{
            work.setOriginId(work.getId());
            work.setId(null); //清除id，使新插入的数据id重新生成
            work.setCreateTime(null);
            work.setModifyTime(null);
            work.setOld(0);
        }
        if (workMapper.insert(work)<=0) {
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
