/**
 * 
 */
$(function(){
	//从URL里获取shopid
	var shopId = getQueryString('shopId');
	
	var isEdit = shopId ? true : false;
	var initUrl = '/o2o/shopadmin/getshopinitinfo';
	var registerShopUrl = '/o2o/shopadmin/registershop';
	//通过id指定查找到店铺，并额外订制了resultMap返回category的ID和名字，以及所有的可选区域
	var shopInfoUrl = "/o2o/shopadmin/getshopbyid?shopId=" + shopId;
	var editShopUrl = "/o2o/shopadmin/modifyshop";
	//alert(initUrl);
	
	if(!isEdit){
		getShopInitInfo();
	}else{
		getShopInfo(shopId);
	}
	
	/************************************************
	 * 从后台获取下拉栏的信息进行填充，皆为二级以下菜单
	 ************************************************/
	function getShopInfo(shopId){
		$.getJSON(shopInfoUrl, function(data) {
			if(data.success) {
				var shop = data.shop;
				$('#shop-name').val(shop.shopName);
				$('#shop-addr').val(shop.shopAddr);
				$('#shop-phone').val(shop.phone);
				$('#shop-desc').val(shop.shopDesc);
				
				//获取到的类别是不可修改的，置为disable
				var shopCategory = '<option data-id="'
						+ shop.shopCategory.shopCategoryId + '" selected>'
						+ shop.shopCategory.shopCategoryName + '</option>';
				$('#shop-category').html(shopCategory);
				$('#shop-category').attr('disabled', 'disabled');
				
				//获取到的区域是可改的，直接把整个List显示
				var tempAreaHtml = '';			
				data.areaList.map(function(item, index){
					tempAreaHtml += '<option data-id="' + item.areaId + '">' + item.areaName + '</option>';
				});
				$('#area').html(tempAreaHtml);
				$("#area option[data-id='" + shop.area.areaId + "']").attr("selected", "selected");
			}
		});
	}
	

	/************************************************
	 * 从后台获取下拉栏的信息进行填充，皆为二级以下菜单
	 ************************************************/
	function getShopInitInfo(){
		$.getJSON(initUrl, function(data) {
			if(data.success) {
				var tempHtml = '';
				var tempAreaHtml = '';
				
				data.shopCategoryList.map(function(item, index) {
					tempHtml += '<option data-id="' + item.shopCategoryId + '">' + item.shopCategoryName + '</option>';
				});
				
				data.areaList.map(function(item, index){
					tempAreaHtml += '<option data-id="' + item.areaId + '">' + item.areaName + '</option>';
				});
				
				$('#shop-category').html(tempHtml);
				$('#area').html(tempAreaHtml);
			}
		});
	}
	
	
	/************************************************
	 * 确定按钮，用来提交输入的数据到后台，除了图片其他的采用JSON格式
	 ************************************************/
		$('#submit').click(function(){
			var shop = {};
			
			if(isEdit){
				shop.shopId = shopId;
			}
			
			shop.shopName = $('#shop-name').val();
			shop.shopAddr = $('#shop-addr').val();
			shop.phone = $('#shop-phone').val();
			shop.shopDesc = $('#shop-desc').val();
			
			shop.shopCategory = {
					shopCategoryId : $('#shop-category').find('option').not(function(){
						return !this.selected;
					}).data('id')
			};
			
			shop.area = {
					areaId : $('#area').find('option').not(function(){
						return !this.selected;
					}).data('id')
			};
			
			var shopImg = $('#shop-img')[0].files[0];
			
			var formData = new FormData();
			formData.append('shopImg', shopImg);
			formData.append('shopStr', JSON.stringify(shop));
			
			var verifyCodeActual = $('#j_captcha').val();
			if(!verifyCodeActual){
				$.toast('请输入验证码 ! ');
				return;
			}
			formData.append('verifyCodeActual', verifyCodeActual);
			
			$.ajax({
				url : (isEdit ? editShopUrl : registerShopUrl),
				type : 'POST',
				data : formData,
				contentType : false,
				processData : false,
				cache : false,
				success : function(data){
					if(data.success){
						$.toast('提交成功!');
					}else{
						$.toast('提交失败!' + data.errMsg);
					}
					$('#captcha_img').click();
				}
			})
		});
		
		
		
		
})