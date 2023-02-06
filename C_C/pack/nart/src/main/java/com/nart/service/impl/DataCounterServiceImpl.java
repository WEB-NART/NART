package com.nart.service.impl;


import com.nart.dao.DataCounterDao;
import com.nart.service.DataCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DataCounterServiceImpl implements DataCounterService {

    @Autowired
    private DataCounterDao dataCounterDao;

    @Override
    public int updateUserAmount(boolean increase) {
        if (increase) {
            dataCounterDao.updateUserAmount(1);
            return 1;
        } else {
            dataCounterDao.updateUserAmountm(1);
            return 2;
        }
    }

    @Override
    public int updateOnlineUserAmount(boolean increase) {
        if (increase) {
            dataCounterDao.updateOnlineUserAmount(1);

            return 1;
        } else {
            dataCounterDao.updateOnlineUserAmountm(1);
            return 2;
        }
    }

    @Override
    public int updateStatusAmount(boolean increase) {
        if (increase) {
            dataCounterDao.updateStatusAmount(1);

            return 1;
        } else {
            dataCounterDao.updateStatusAmountm(1);
            return 2;
        }
    }

    @Override
    public int updateCommentAmount(boolean increase) {
        if (increase) {
            dataCounterDao.updateCommentAmount(1);

            return 1;
        } else {
            dataCounterDao.updateCommentAmountm(1);
            return 2;
        }
    }

    @Override
    public int updateMessageAmount(boolean increase) {
        if (increase) {
            dataCounterDao.updateMessageAmount(1);

            return 1;
        } else {
            dataCounterDao.updateMessageAmountm(1);
            return 2;
        }
    }


}