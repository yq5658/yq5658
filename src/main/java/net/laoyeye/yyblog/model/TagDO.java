package net.laoyeye.yyblog.model;

import java.io.Serializable;

/**
 * 
 * @author 小卖铺的老爷爷
 * @date 2018年1月20日
 * @website www.laoyeye.net
 */
public class TagDO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


}
