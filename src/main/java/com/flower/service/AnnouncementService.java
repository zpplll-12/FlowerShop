package com.flower.service;
import com.flower.dao.AnnouncementDAO;
import com.flower.pojo.Announcement;
import java.util.List;

public class AnnouncementService {
    private AnnouncementDAO dao = new AnnouncementDAO();

    public List<Announcement> findAll() {
        return dao.findAll();
    }

    public boolean add(Announcement ann) {
        if (ann.getTitle() == null || ann.getContent() == null) return false;
        return dao.add(ann);
    }

    // 新增delete方法，调用DAO的delete(int)
    public boolean delete(int annId) {
        return dao.delete(annId); // 这里必须能匹配DAO的delete(int)方法
    }
}