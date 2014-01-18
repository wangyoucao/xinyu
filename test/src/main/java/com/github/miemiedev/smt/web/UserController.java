package com.github.miemiedev.smt.web;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.smt.entity.Picture;
import com.github.miemiedev.smt.service.AuthService;
import com.github.miemiedev.smt.service.PictureService;
import com.github.miemiedev.smt.web.util.PageForm;

@Controller
@RequestMapping(value = "/")
public class UserController{
    @Autowired
    private AuthService authService;
   
    @Autowired
    private PictureService pictureService;
    
    @Value("#{configProperties['ipaddress']}")
    private String ipaddress;
    
    
    
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("sdfsdgfsdf");
        return new ModelAndView("index");
    }

    @ResponseBody
    @RequestMapping(value = "list.json")
    public List list(PageForm pageForm) throws ParseException {
        pageForm.addOrderExpr("REAL_NAME", "nlssort(? ,'NLS_SORT=SCHINESE_PINYIN_M') ? nulls last");
        return authService.queryByDeptCode("", pageForm.toPageBounds());
    }
    
    
    /**
     * Accepts a POST request with multipart/form-data content
     * @param name the name of the file being uploaded
     * @param file the binary file
     * @return response message indicating success or failure
     */
    @RequestMapping(value = "postformdata", method = RequestMethod.POST, headers = "Content-Type=multipart/form-data")
    public @ResponseBody
    String handleFormUpload(@RequestParam(value="latitude",required=false) String latitude,@RequestParam(value="longtitude",required=false) String longtitude,@RequestParam("description") String description, @RequestParam("file")  byte[] bytes,HttpServletRequest request, HttpServletResponse response) {

    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        if (bytes.length>0) {
            //byte[] bytes = null;
            try {
                //bytes = file.getBytes();
               // String filePath = "E:\\apache-tomcat-6.0.37\\webapps\\xinwei-demo-web" +  
                String savePath = request.getRealPath("");
                String  filePath = savePath+ File.separator+"userpic"+File.separator;  
                System.out.println("path="+filePath);
                //声明文件读入类   
                DataInputStream in = null;   
                FileOutputStream fileOut = null;              
                String fileName = System.currentTimeMillis()+".png"; 
                String fileRealPathName = filePath + fileName;
                 //检查上载文件的目录是否存在   
                File fileDir = new File(filePath);   
                if(!fileDir.exists()){   
                fileDir.mkdirs();   
                }  
                //创建文件的写出类   
                fileOut = new FileOutputStream(fileRealPathName);   
                //      保存文件的数据   
                fileOut.write(bytes);   
                fileOut.close();  
                System.out.println("fileName"+fileRealPathName);
                
                String str = request.getContextPath();
                String imageURL =ipaddress+ str+"/userpic/"+fileName;
                
                Picture pic = new Picture();
                pic.setPicPath(imageURL);
                pic.setParentID(0);
                pic.setLocjingdu(latitude);
                pic.setLocweidu(longtitude);
                pictureService.insertPicture(pic);
            } catch (IOException e) {
                e.printStackTrace();
                
            }
            //System.out.println("fileName"+fileName);
            return "file upload received! Name:[" + description + "] Size:[" + bytes.length + "]";
        } else {
            return "file upload failed!";
        }
    }
    
    

    @RequestMapping(value = "getpiclist", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Picture> fetchpiclistJson(HttpServletRequest request, HttpServletResponse response) {
    	
    	int size = Integer.parseInt(request.getParameter("size"));
    	String type = request.getParameter("type");
    	String latitude = request.getParameter("latitude");
    	String longtitude = request.getParameter("longtitude");
        PageBounds page =	new PageBounds((size/5)+1, 5);
		
		return pictureService.getpiclist(size,type,latitude,longtitude,page);
	}
}
