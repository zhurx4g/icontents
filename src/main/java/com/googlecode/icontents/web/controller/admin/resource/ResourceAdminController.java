package com.googlecode.icontents.web.controller.admin.resource;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.icontents.bean.User;
import com.googlecode.icontents.bean.resource.FileComp;
import com.googlecode.icontents.bean.resource.FileInfo;
import com.googlecode.icontents.bean.resource.HttpMultiPartParser;
import com.googlecode.icontents.bean.resource.UplInfo;
import com.googlecode.icontents.bean.resource.UploadMonitor;
import com.googlecode.icontents.bean.resource.Writer2Stream;
import com.googlecode.icontents.util.ResourceUtils;
import com.googlecode.icontents.web.ActionsConstant;
import com.googlecode.icontents.web.controller.admin.AbstractAdminController;

@Controller
@RequestMapping(value="admin/resource/resource.do")
public class ResourceAdminController extends AbstractAdminController{

    @Override
    protected ModelAndView handleAdminRequestInternal(
            HttpServletRequest request, HttpServletResponse response,
            User adminUser,Map<String,Object> model) throws Exception {
    	response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        
        String dir = ServletRequestUtils.getStringParameter(request, "dir", "");
        if(StringUtils.isBlank(dir))dir = request.getRealPath("/");//System.getProperty("user.dir");
        model.put("dir",dir);
        
        String action = ServletRequestUtils.getStringParameter(request, ActionsConstant.ACTION, ActionsConstant.LIST);
        int sortMode = ServletRequestUtils.getIntParameter(request, ActionsConstant.SORT, 1);
        
        File currentFile = new File(dir);
        
        //查看文件
        if(ActionsConstant.PREVIEW.equalsIgnoreCase(action)){
            String file = ServletRequestUtils.getStringParameter(request, "file", "");
            if (file!= null) {
                File f = new File(file);
                if (!ResourceUtils.isAllowed(f, false)) {
                    model.put("dir", f.getParent());
                    model.put("error", "您没有权限访问: "+f.getAbsolutePath());
                }
                else if (f.exists() && f.canRead()) {
                    if (ResourceUtils.isPacked(f.getName(), false)) {
                        //If zipFile, do nothing here
                    }
                    else{
                        String mimeType = ResourceUtils.getMimeType(f.getName());
                        response.setContentType(mimeType);
                        if (mimeType.equals("text/plain")) response.setHeader(
                                "Content-Disposition", "inline;filename=\"temp.txt\"");
                        else response.setHeader("Content-Disposition", "inline;filename=\""
                                + f.getName() + "\"");
                        BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(f));
                        byte buffer[] = new byte[8 * 1024];
                        OutputStream out_s = new Writer2Stream(out);
                        ResourceUtils.copyStreamsWithoutClose(fileInput, out_s, buffer);
                        fileInput.close();
                        out_s.flush();
                        return null;
                    }
                }
                else {
                    model.put("dir", f.getParent());
                    model.put("error", "文件[" + f.getAbsolutePath() + "]在服务器上不存在");
                }
                
                return new ModelAndView(getViewName(),model);
            }
        }
        //用zip格式获取多个文件
        else if(ActionsConstant.ZIPS.equalsIgnoreCase(action)){
            Vector<File> v = ResourceUtils.expandFileList(request.getParameterValues("selfile"), false);
            //Check if all files in vector are allowed
            String notAllowedFile = null;
            for (int i = 0;i < v.size(); i++){
                File f = (File) v.get(i);
                if (!ResourceUtils.isAllowed(f, false)){
                    notAllowedFile = f.getAbsolutePath();
                    break;
                }
            }
            if (notAllowedFile != null){
                model.put("error", "您没有权限获取" + notAllowedFile);
            }
            else if (v.size() == 0) {
                model.put("error", "没有选择文件");
            }
            else {
                File dir_file = new File("" + request.getAttribute("dir"));
                int dir_l = dir_file.getAbsolutePath().length();
                response.setContentType("application/zip");
                response.setHeader("Content-Disposition", "attachment;filename=\"rename_me.zip\"");
                ZipOutputStream zipout = new ZipOutputStream(new Writer2Stream(out));
                zipout.setComment("Created by jsp File Browser v. " + ResourceUtils.VERSION_NR);
                zipout.setLevel(ResourceUtils.COMPRESSION_LEVEL);
                for (int i = 0; i < v.size(); i++) {
                    File f = (File) v.get(i);
                    if (f.canRead()) {
                        zipout.putNextEntry(new ZipEntry(f.getAbsolutePath().substring(dir_l + 1)));
                        BufferedInputStream fr = new BufferedInputStream(new FileInputStream(f));
                        byte buffer[] = new byte[0xffff];
                        ResourceUtils.copyStreamsWithoutClose(fr, zipout, buffer);
                        int b;
                        while ((b=fr.read())!=-1) zipout.write(b);
                        fr.close();
                        zipout.closeEntry();
                    }
                }
                zipout.finish();
                out.flush();
                return null;
            }
        }
        else if (ActionsConstant.CREATE.equalsIgnoreCase(action)) {
        	String newDirName = ServletRequestUtils.getStringParameter(request, "newDirName","");
        	
        	if(StringUtils.isNotBlank(newDirName)){
        		try{
	        		File dirFile = new File(dir+"/"+newDirName);
	        		dirFile.mkdir();
        		}catch(Exception e){
        			
        		}
        	}
        }else if (ActionsConstant.UPLOAD.equalsIgnoreCase(action)) {
        	System.out.println("upload...........................");
        	//上传
        	if ((request.getContentType() != null)
    				&& (request.getContentType().toLowerCase().startsWith("multipart"))) {
    	    	response.setContentType("text/html");
    			HttpMultiPartParser parser = new HttpMultiPartParser();
    			boolean error = false;
    			try {
    				int bstart = request.getContentType().lastIndexOf("oundary=");
    				String bound = request.getContentType().substring(bstart + 8);
    				int clength = request.getContentLength();
    				Hashtable<String,Object> ht = parser
    						.processData(request.getInputStream(), bound, dir, clength);
    	            if (!ResourceUtils.isAllowed(new File((String)ht.get("dir")), false)){
    	                //This is a hack, cos we are writing to this directory
    	            	model.put("error", "不允许上传到目录: " + ht.get("dir"));
    	                error = true;
    	            }
    				else if (ht.get("myFile") != null) {
    					FileInfo fi = (FileInfo) ht.get("myFile");
    					File f = fi.file;
    					UplInfo info = UploadMonitor.getInfo(fi.clientFileName);
    					if (info != null && info.aborted) {
    						f.delete();
    						model.put("error", "Upload aborted");
    					}
    					else {
    						// Move file from temp to the right dir
    						String path = (String) ht.get("dir");
    						if (!path.endsWith(File.separator)) path = path + File.separator;
    						if (!f.renameTo(new File(path + f.getName()))) {
    							model.put("error", "Cannot upload file.");
    							error = true;
    							f.delete();
    						}
    					}
    				}
    				else {
    					model.put("error", "No file selected for upload");
    					error = true;
    				}
    			}
    			catch (Exception e) {
    				e.printStackTrace();
    				model.put("error", "Error " + e + ". Upload aborted");
    				error = true;
    			}
    			if (!error) model.put("error", "文件成功上传.");
        	}
        }else if (ActionsConstant.UNZIP.equalsIgnoreCase(action)) {
        	File f = new File(request.getParameter("file"));
			String root = f.getParent();
			model.put("dir", root);
            if (!ResourceUtils.isAllowed(new File(root), true)){
                model.put("error", "You are not allowed to access " + root);
            }
			//Check if file exists
			else if (!f.exists()) {
				model.put("error", "Cannot unpack " + f.getName()
						+ ", file does not exist");
			}
			//Check if directory is readonly
			else if (!f.getParentFile().canWrite()) {
				model.put("error", "Cannot unpack " + f.getName()
						+ ", directory is write protected.");
			}
			//GZip
			else if (f.getName().toLowerCase().endsWith(".gz")) {
				//New name is old Name without .gz
				String newName = f.getAbsolutePath().substring(0, f.getAbsolutePath().length() - 3);
				try {
					byte buffer[] = new byte[0xffff];
					ResourceUtils.copyStreams(new GZIPInputStream(new FileInputStream(f)), new FileOutputStream(
							newName), buffer);
				}
				catch (IOException ex) {
					model.put("error", "Unpacking of " + f.getName()
							+ " aborted. Error: " + ex);
				}
			}
			//Else try Zip
			else {
				try {
					ZipFile zf = new ZipFile(f);
					Enumeration entries = zf.entries();
					//First check whether a file already exist
					boolean error = false;
					while (entries.hasMoreElements()) {
						ZipEntry entry = (ZipEntry) entries.nextElement();
						if (!entry.isDirectory()
								&& new File(root + File.separator + entry.getName()).exists()) {
							model.put("error", "Cannot unpack " + f.getName()
									+ ", File " + entry.getName() + " already exists.");
							error = true;
							break;
						}
					}
					if (!error) {
						//Unpack File
						entries = zf.entries();
						byte buffer[] = new byte[0xffff];
						while (entries.hasMoreElements()) {
							ZipEntry entry = (ZipEntry) entries.nextElement();
							File n = new File(root + File.separator + entry.getName());
							if (entry.isDirectory()) n.mkdirs();
							else {
								n.getParentFile().mkdirs();
								n.createNewFile();
								ResourceUtils.copyStreams(zf.getInputStream(entry), new FileOutputStream(n),
										buffer);
							}
						}
						zf.close();
						model.put("message", "Unpack of " + f.getName()
								+ " was successful.");
					}
				}
				catch (ZipException ex) {
					model.put("error", "Cannot unpack " + f.getName()
							+ ", no valid zip file");
				}
				catch (IOException ex) {
					model.put("error", "Unpacking of " + f.getName()
							+ " aborted. Error: " + ex);
				}
			}
        }else if (ActionsConstant.DELETE.equalsIgnoreCase(action)) {
			Vector<File> v = ResourceUtils.expandFileList(request.getParameterValues("selfile"), true);
			boolean error = false;
			//delete backwards
			for (int i = v.size() - 1; i >= 0; i--) {
				File f = (File) v.get(i);
                if (!ResourceUtils.isAllowed(f, true)){
                   model.put("error", "没有权限删除 " + f.getAbsolutePath());
                    error = true;
                    break;
                }
				if (!f.delete()) {
					request.setAttribute("error", "不能删除 " + f.getAbsolutePath()
							+ ". 删除终止");
					error = true;
					break;
				}
			}
			if ((!error) && (v.size() > 1))model.put("message", "所有文件被删除");
			else if ((!error) && (v.size() > 0))model.put("message", "文件已经删除");
			else if (!error)model.put("error", "没有选中文件");
        }else if (ActionsConstant.GET.equalsIgnoreCase(action)) {
        	//下载文件
            String filePath = ServletRequestUtils.getStringParameter(request, "file", "");
            File f = new File(filePath);
            if (!ResourceUtils.isAllowed(f, false)){
                model.put("dir", f.getParent());
                model.put("error", "您没有权限获取" + f.getAbsoluteFile());
            }
            else if (f.exists() && f.canRead()) {
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=\"" + f.getName()
                        + "\"");
                response.setContentLength((int) f.length());
                BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(f));
                byte buffer[] = new byte[8 * 1024];
                OutputStream out_s = new Writer2Stream(out);
                ResourceUtils.copyStreamsWithoutClose(fileInput, out_s, buffer);
                fileInput.close();
                out_s.flush();
                return null;
            }
            else {
                model.put("dir", f.getParent());
                model.put("error", "文件 " + f.getAbsolutePath()
                        + " 在服务器上不存在");
            }
        }else{
            if (!ResourceUtils.isAllowed(new File(dir), false)){
                model.put("error", "您没有权限获取 " + dir);
            }
        }
        if (currentFile.getParent() != null) {
            model.put("parentFile",currentFile.getParentFile());
        }
        
        File[] entry = currentFile.listFiles();
        if (entry == null) entry = new File[] {};
        if (entry != null && entry.length > 0) {
            Arrays.sort(entry, new FileComp(sortMode));
            model.put("fileList", entry);
        }
        
        return new ModelAndView(getViewName(),model);
    }

    public static String encode(String path){
        try {
            return URLEncoder.encode(path,"utf-8");
        } catch (UnsupportedEncodingException e) {
            return path;
        }
    }
    @Override
    protected boolean hasAuth(User adminUser) {
        return false;
    }

}
