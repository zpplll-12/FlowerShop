package com.flower.service;
import com.flower.dao.FlowerDAO;
import com.flower.pojo.Flower;
import com.flower.util.PageBean;
import java.util.List;

public class FlowerService {
    private FlowerDAO dao = new FlowerDAO();
    public List<Flower> findAll() {return dao.findAll();}
    public List<Flower> findByCategory(String category) {return dao.findByCategory(category);}
    public PageBean<Flower> findByPage(int pageNum, int pageSize) {
        PageBean<Flower> pageBean = new PageBean<>();
        pageBean.setPageNum(pageNum);pageBean.setPageSize(pageSize);
        int totalCount = dao.getTotalCount();pageBean.setTotalCount(totalCount);
        List<Flower> list = dao.findByPage((pageNum-1)*pageSize, pageSize);
        pageBean.setList(list);return pageBean;
    }
    public boolean add(Flower flower, int stock) {
        if(flower.getFlowerName()==null || flower.getPrice()<=0 || stock<0) return false;
        return dao.add(flower,stock);
    }
    public boolean update(Flower flower) {return dao.update(flower);}
    public boolean delete(int fid) {return dao.delete(fid);}
    public boolean reduceStock(int fid, int num) {return dao.reduceStock(fid,num);}
}