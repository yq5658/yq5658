package net.laoyeye.yyblog.service;

import java.util.List;

import net.laoyeye.yyblog.common.YYBlogResult;
import net.laoyeye.yyblog.model.SettingDO;

public interface SettingService {
    /**
     * 获取所有配置数据
     */
    List<SettingDO> listAll();
    /**
     * 根据code获取指定的value值
     */
    String getValueByCode(String code);
    
    YYBlogResult updateValueByCode(SettingDO setting);
}
