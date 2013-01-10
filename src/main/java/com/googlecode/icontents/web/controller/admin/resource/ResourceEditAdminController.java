package com.googlecode.icontents.web.controller.admin.resource;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.icontents.bean.User;
import com.googlecode.icontents.bean.resource.FileComp;
import com.googlecode.icontents.bean.resource.Writer2Stream;
import com.googlecode.icontents.util.ResourceUtils;
import com.googlecode.icontents.web.ActionsConstant;
import com.googlecode.icontents.web.controller.admin.AbstractAdminController;

@Controller
@RequestMapping(value="admin/resource/resourceEdit.do")
public class ResourceEditAdminController extends AbstractAdminController{

    @Override
    protected ModelAndView handleAdminRequestInternal(
            HttpServletRequest request, HttpServletResponse response,
            User adminUser,Map<String,Object> model) throws Exception {
    	response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        
        String dir = ServletRequestUtils.getStringParameter(request, "dir", ".");
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
                        /*                  int b;
                         while ((b=fr.read())!=-1) zipout.write(b);*/
                        fr.close();
                        zipout.closeEntry();
                    }
                }
                zipout.finish();
                out.flush();
                return null;
            }
        }
        //下载文件
        else if (ActionsConstant.GET.equalsIgnoreCase(action)) {
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
                request.setAttribute("error", "您没有权限获取 " + dir);
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
