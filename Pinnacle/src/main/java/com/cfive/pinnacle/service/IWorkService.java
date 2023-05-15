package com.cfive.pinnacle.service;

import com.cfive.pinnacle.entity.UserWork;
import com.cfive.pinnacle.entity.Work;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 工作事项 服务类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
public interface IWorkService extends IService<Work> {
    List<Work> getAll();
    List<Work> getTodo(Long userId);

    List<Work> getCard(Long userId);
    List<Work> getComplete(Long userId);
    Work getOne(Long workId);

    List<Work> getWorkByContent(String content);
    double getProgress(Long workId);

    String getUserName(Long userId);
    boolean addWork(Work work);
    boolean deleteByWorkId(Long wid);

    boolean updateStatus(UserWork userWork);

    boolean updateWork(Work work);
}
