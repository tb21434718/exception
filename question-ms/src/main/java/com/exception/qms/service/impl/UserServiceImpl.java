package com.exception.qms.service.impl;

import com.exception.qms.domain.entity.User;
import com.exception.qms.domain.entity.UserAnswerContribution;
import com.exception.qms.domain.entity.UserQuestionContribution;
import com.exception.qms.domain.mapper.UserAnswerContributionMapper;
import com.exception.qms.domain.mapper.UserMapper;
import com.exception.qms.domain.mapper.UserQuestionContributionMapper;
import com.exception.qms.enums.UserAnswerContributionTypeEnum;
import com.exception.qms.enums.UserQuestionContributionTypeEnum;
import com.exception.qms.service.UserService;
import com.exception.qms.utils.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author jiangbing(江冰)
 * @date 2017/12/22
 * @time 下午2:04
 * @discription
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserQuestionContributionMapper userQuestionContributionMapper;
    @Autowired
    private UserAnswerContributionMapper userAnswerContributionMapper;

    @Override
    public List<User> queryUsersByUserIds(List<Long> userIds) {
        return userMapper.queryUsersByUserIds(userIds);
    }

    @Override
    public User queryByUserName(String userName) {
        return userMapper.queryByUserName(userName);
    }

    @Override
    public int queryUserPageListCount() {
        return userMapper.queryUserPageListCount();
    }

    @Override
    public List<User> queryUserPageList(Integer pageIndex, Integer pageSize) {
        return userMapper.queryUserPageList(PageUtil.calculateLimitSelectSqlStart(pageIndex, pageSize), pageSize);
    }

    /**
     * 更新用户最后一次登录时间为当前时间
     *
     * @param userId
     * @return
     */
    @Override
    public int updateLastLoginTime(Long userId) {
        Assert.notNull(userId, "updateLastLoginTime, the user id is null");
        return userMapper.updateLastLoginTime(userId);
    }

    @Override
    public List<UserQuestionContribution> queryUserQuestionContribution(long userId, LocalDate today, LocalDate lastYearToday) {
        return userQuestionContributionMapper.queryUserQuestionContribution(userId, today, lastYearToday);
    }

    @Override
    public List<UserAnswerContribution> queryUserAnswerContribution(Long userId, LocalDate today, LocalDate lastYearToday) {
        return userAnswerContributionMapper.queryUserAnswerContribution(userId, today, lastYearToday);
    }

    /**
     * 添加问题的贡献记录
     *
     * @param questionId
     * @return
     */
    @Override
    public int addQuestionContribution(Long questionId, Long userId, int type) {
        UserQuestionContribution userQuestionContribution = new UserQuestionContribution();
        userQuestionContribution.setCreateTime(LocalDateTime.now());
        userQuestionContribution.setQuestionId(questionId);
        userQuestionContribution.setUserId(userId);
        userQuestionContribution.setType(type);
        return userQuestionContributionMapper.insert(userQuestionContribution);
    }

    /**
     * 添加解决方案的贡献记录
     *
     * @param answerId
     * @return
     */
    @Override
    public int addAnswerContribution(Long answerId, Long userId, int type) {
        UserAnswerContribution userAnswerContribution = new UserAnswerContribution();
        userAnswerContribution.setAnswerId(answerId);
        userAnswerContribution.setCreateTime(LocalDateTime.now());
        userAnswerContribution.setUserId(userId);
        userAnswerContribution.setType(type);
        return userAnswerContributionMapper.insert(userAnswerContribution);
    }
}
