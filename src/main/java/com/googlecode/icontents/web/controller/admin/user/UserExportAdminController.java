package com.googlecode.icontents.web.controller.admin.user;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.icontents.bean.User;
import com.googlecode.icontents.service.UserService;
import com.googlecode.icontents.web.controller.admin.AbstractAdminController;

@Controller
@RequestMapping(value="admin/user/userExport.do")
public class UserExportAdminController  extends AbstractAdminController{
	
	@Resource
	private UserService userService;
	
	@Override
	protected ModelAndView handleAdminRequestInternal(
			HttpServletRequest request, HttpServletResponse response,
			User adminUser, Map<String, Object> model) throws Exception {
//		request.setCharacterEncoding("utf-8");
//		response.setContentType("application/download;charset=UTF-8");
//		response.setHeader("Content-disposition","attachment;filename=\"allUser(" + new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date())+").xls\"");
//
//		HSSFWorkbook wb = new HSSFWorkbook();
//		// create a new sheet
//		HSSFSheet s = wb.createSheet();
//		// declare a row object reference
//		HSSFRow r = null;
//		// declare a cell object reference
//		// create 2 cell styles
//		HSSFCellStyle cs = wb.createCellStyle();
//		HSSFCellStyle cs2 = wb.createCellStyle();
//		HSSFDataFormat df = wb.createDataFormat();
//
//		// create 2 fonts objects
//		HSSFFont f = wb.createFont();
//		HSSFFont f2 = wb.createFont();
//
//		// Set font 1 to 12 point type, blue and bold
//		f.setFontHeightInPoints((short) 12);
//		f.setColor( HSSFColor.RED.index );
//		f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//
//		// Set font 2 to 10 point type, red and bold
//		f2.setFontHeightInPoints((short) 10);
//		f2.setColor( HSSFFont.COLOR_RED);
//		f2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//
//		// Set cell style and formatting
//		cs.setFont(f);
//		cs.setDataFormat(df.getFormat("#,##0.0"));
//
//		// Set the other cell style and formatting
//		cs2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		cs2.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
//		cs2.setFont(f2);
//
//
//		List<User> userList = userService.select(0, Integer.MAX_VALUE);
//		
//		r = s.createRow(0);
//		HSSFCell c0 = r.createCell(0);
//		HSSFCell c1 = r.createCell(1);
//		HSSFCell c2 = r.createCell(2);
//		HSSFCell c3 = r.createCell(3);
//		HSSFCell c4 = r.createCell(4);
//		
//		c0.setCellValue("用户ID");
//		c1.setCellValue("Email");
//		c2.setCellValue("用户名");
//		c3.setCellValue("用户类型");
//		c4.setCellValue("注册日期");
//		
//		// Define a few rows
//		for(int rownum = 0; rownum < userList.size(); rownum++) {
//			r = s.createRow(rownum+1);
//			User user = userList.get(rownum);
//			
//			c0 = r.createCell(0);
//			c1 = r.createCell(1);
//			c2 = r.createCell(2);
//			c3 = r.createCell(3);
//			c4 = r.createCell(4);
//
//			c0.setCellValue(user.getId());
//			c1.setCellValue(user.getEmail());
//			c2.setCellValue(user.getUserName());
//			c3.setCellValue(user.getLevel());
//			c4.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getRegistrationDate()));
//		}
//
//		// Save
//		OutputStream out = response.getOutputStream();
//		wb.write(response.getOutputStream());
//		out.close();
		
		return null;
	}
	
	@Override
	protected boolean hasAuth(User adminUser) {
		return false;
	}
}
