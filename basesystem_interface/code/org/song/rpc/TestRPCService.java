package org.song.rpc;

import org.song.pojo.RespondDTO;

/**
 * Created by songwangwen on 2016/3/7.
 */
public interface TestRPCService {
    /**
     * @api {get} /wallet/balance/show.htm 获取钱包余额和累计收入
     * @apiName GetWalletBalanceShow
     * @apiGroup Balance
     * @apiVersion 0.0.11
     *
     * @apiParam {string} providerId 服务商ID.
     *
     * @apiSuccess {number} balance 钱包余额,即可提现最大值,单位为:分.
     * @apiSuccess {number} totalIncome 累计收入,单位为:分.
     * @apiSuccess {boolean} hasBinding 是否已绑定提现账号,决定跳转到绑定账号页还是提现页面.
     * @apiSuccess {number} bindingType 绑定账号的类型:1-银行卡;2-支付宝;
     * @apiSuccess {string} bindingId 提现账号ID
     * @apiSuccess {string} bankCode 银行编号(银行卡)
     * @apiSuccess {string} bankName 银行名称(银行卡)
     * @apiSuccess {string} openBankName 开户行(银行卡)
     * @apiSuccess {number} openBankCityId 所在地编号(银行卡)
     * @apiSuccess {string} userName 用户名
     * @apiSuccess {string} bindingNumber 银行卡号或支付宝账号,只显示前四位和后四位,中间是"*",长度短于9位只显示后四位.
     *
     * @apiSuccessExample 返回值
     * {
     *   "balance": 10200,
     *   "totalIncome": 20211,
     *   "hasBinding": false,
     *   "bindingType": 1,
     *   "bindingId": "93d8b177-e8c7-483a-b427-8d3b5b57cf01",
     *   "bankCode": "CB",
     *   "bankName": "招商银行",
     *   "openBankName": "西湖支行",
     *   "openBankCityId": 92,
     *   "userName": "张无忌",
     *   "bindingNumber": "6225***2222"
     * }
     */
    public RespondDTO getRespondByCode(String code);
}
