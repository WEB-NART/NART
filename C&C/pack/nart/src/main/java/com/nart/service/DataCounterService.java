package com.nart.service;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: NART
 * @className: DataCounterService
 *  The methods contained in this class can update the statistics of the number of records in each table
 *
 * @version: v1.17.0
 * @Author YUNZHOU LIU
 * @Date 2023-1-14
 *
 */

public interface DataCounterService {
    /**
     *  This method can increase or decrease the number of users in the data counter table according to the incoming Boolean parameters
       @param increase
      * @return int
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    int updateUserAmount(boolean increase);

    /**
     *  This method can increase or decrease the number of user online records in the data counter table according to the incoming Boolean parameters
       @param increase
      * @return int
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    int updateOnlineUserAmount(boolean increase);

    /**
     *  This method can increase or decrease the number of status records in the data counter table according to the passed Boolean parameters
       @param increase
      * @return int
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    int updateStatusAmount(boolean increase);

    /**
     *  This method can increase or decrease the number of comments in the data counter table according to the incoming Boolean parameters
       @param increase
      * @return int
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    int updateCommentAmount(boolean increase);

    /**
     *  This method can increase or decrease the number of records of information sent in the data counter table according to the incoming Boolean parameters
       @param increase
      * @return int
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    int updateMessageAmount(boolean increase);

}
