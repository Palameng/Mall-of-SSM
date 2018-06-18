package com.my.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.my.o2o.BaseTest;
import com.my.o2o.entity.ProductImg;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {
	@Autowired
	private ProductImgDao productImgDao;

	@Test
	@Ignore
	public void testA_BatchInsertProductImg() throws Exception {
		// productId为1的商品里添加两个详情图片记录
		ProductImg productImg1 = new ProductImg();
		productImg1.setImgAddr("图片1");
		productImg1.setImgDesc("测试图片1");
		productImg1.setPriority(1);
		productImg1.setCreateTime(new Date());
		productImg1.setProductId(2L);
		
		ProductImg productImg2 = new ProductImg();
		productImg2.setImgAddr("图片2");
		productImg2.setImgDesc("测试图片2");
		productImg2.setPriority(1);
		productImg2.setCreateTime(new Date());
		productImg2.setProductId(2L);
		
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		productImgList.add(productImg1);
		productImgList.add(productImg2);
		
		int effectedNum = productImgDao.batchInsertProductImg(productImgList);
		assertEquals(2, effectedNum);
	}

	@Test
	@Ignore
	public void testB_QueryProductImgList() {
		// 检查productId为1的商品是否有且仅有两张商品详情图片
		List<ProductImg> productImgList = productImgDao.queryProductImgList(1L);
		assertEquals(2, productImgList.size());
	}

	@Test
	public void testC_DeleteProductImgByProductId() throws Exception {
		// 删除新增的商品详情图片记录
		long productId = 2;
		int effectedNum = productImgDao.deleteProductImgByProductId(productId);
		assertEquals(4, effectedNum);
	}
}
