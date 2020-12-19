package com.mmall.service.impl;

import com.google.common.base.Preconditions;
import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysUserMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysUser;
import com.mmall.param.UserParam;
import com.mmall.service.SysLogService;
import com.mmall.service.SysUserService;
import com.mmall.utils.BeanValidator;
import com.mmall.utils.IpUtil;
import com.mmall.utils.MD5Utils;
import com.mmall.utils.PasswordUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.awt.geom.FlatteningPathIterator;
import java.util.Date;
import java.util.List;

/**
 * @ClassName SysUserServiceImpl
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/11/12 21:51
 * @Version 1.0
 **/
@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Resource
    private SysLogService sysLogService;

    @Override
    public int save(UserParam param) {
        BeanValidator.check(param);
        boolean emailExits = this.checkEmailExits(param.getMail(), param.getId());
        boolean telephoneExits = this.checkTelephoneExits(param.getTelephone(), param.getId());
        if (emailExits) {//存在
            throw new ParamException("邮箱已被占用");
        }
        if (telephoneExits) {//存在
            throw new ParamException("手机号已被占用");
        }
        String password = PasswordUtils.randomPassWord();
        password = "123456";

        SysUser user = new SysUser();
        user.setDeptId(param.getDeptId());
        user.setMail(param.getMail());
        user.setUsername(param.getUsername());
        user.setTelephone(param.getTelephone());
        user.setRemark(param.getRemark());
        user.setStatus(param.getStatus());
        user.setOperator(RequestHolder.getCurrentUser().getUsername());
        user.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        user.setOperateTime(new Date());
        user.setPassword(MD5Utils.encrypt(password));
        sysLogService.saveUserLog(null, user);
        return userMapper.insertSelective(user);
    }

    /**
     * 更新用户
     * @param param
     * @return
     */
    @Override
    public int update(UserParam param) {
        BeanValidator.check(param);
        //查询用户信息
        SysUser before = userMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的用户不存在");
        boolean telephoneExits = this.checkTelephoneExits(param.getTelephone(), param.getId());
        boolean emailExits = this.checkEmailExits(param.getMail(), param.getId());
        if (emailExits) {//存在
            throw new ParamException("邮箱已被占用");
        }
        if (telephoneExits) {//存在
            throw new ParamException("手机号已被占用");
        }
        SysUser after = new SysUser();
        after.setId(before.getId());
        after.setDeptId(param.getDeptId());
        after.setMail(param.getMail());
        after.setUsername(param.getUsername());
        after.setTelephone(param.getTelephone());
        after.setRemark(param.getRemark());
        after.setStatus(param.getStatus());
        after.setId(param.getId());
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        sysLogService.saveUserLog(before, after);
        return userMapper.updateByPrimaryKeySelective(after);
    }


    @Override
    public SysUser findByKeyword(String keyword) {
        return userMapper.findByKeyword(keyword);
    }


    /**
     * 校验email是否已被占用
     * @param mail
     * @param userId
     * @return
     */
    private boolean  checkEmailExits(String mail, Integer userId){
        return userMapper.countByMail(mail, userId) > 0;
    }

    /**
     * 校验手机号是否已被占用
     * @return
     */
    private boolean checkTelephoneExits(String telePhone, Integer userId){
        return userMapper.countByTelephone(telePhone, userId) > 0;
    }


    /**
     * 获取部门下的人员
     * @param deptId
     * @param pageQuery
     * @return
     */
    @Override
    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery pageQuery) {
        BeanValidator.check(pageQuery);
        int count = 0;
        //查询该部门下是否有人员
        count = userMapper.countbyDeptId(deptId);

        if (count > 0) {//获取列表
            //查询人员信息
            List<SysUser> list = userMapper.getPageByDeptId(deptId, pageQuery);
            PageResult<SysUser> resultList = new PageResult<>();
            resultList.setData(list);
            resultList.setTotal(count);
            return resultList;
        }

        return new PageResult<>();
    }

    /**
     * 查询所有用户
     * @return
     */
    @Override
    public List<SysUser> getAll() {
        List<SysUser> userList = userMapper.getAll();
        return userList;
    }
}
